package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * Declaration of a class (<code>class name extends superClass {members}<code>).
 * 
 * @author gl48
 * @date 01/01/2020
 */
public class DeclClass extends AbstractDeclClass {
	
	// outil pour logger les erreurs eventuels
	private static final Logger LOG = Logger.getLogger(DeclClass.class);

    // nom de la classe
    final private AbstractIdentifier className;
    // extension de la classe
	// elle étend Object par défaut
    final private AbstractIdentifier extension;
    // attributs de la classe
    final private ListDeclField fields;
    // méthodes de la classe
    final private ListDeclMethod methods;
    

    
    public DeclClass(AbstractIdentifier className, AbstractIdentifier extension,
    		ListDeclField fields, ListDeclMethod methods) {
    	Validate.notNull(className);
    	Validate.notNull(extension);
    	Validate.notNull(fields);
    	Validate.notNull(methods);
    	this.extension = extension;
    	this.className = className;
    	this.fields = fields;
    	this.methods = methods;
    }
    


    @Override
    public void decompile(IndentPrintStream s) {
        s.print("class ");
        this.className.decompile(s);
        s.print(" extends ");
        this.extension.decompile(s);
        s.println("{");
        s.indent();
        this.fields.decompile(s);
        this.methods.decompile(s);
        s.unindent();
        s.print("}");
    }

    @Override
    protected void verifyClass(DecacCompiler compiler) throws ContextualError {
        
    	//On verifie que la classe herite d'une classe existante
    	if (!compiler.getEnvironmentType().isIn(extension.getName())) {
    		throw new ContextualError("Classe non définie", extension.getLocation());
    	}
    	
    	//On recupere le type de la superclasse
    	ClassType superC = compiler.getEnvironmentType().get(extension.getName()).asClassType("La classe doit hériter d'une classe existante", extension.getLocation());
        this.extension.setType(superC.getDefinition().getType());
        this.extension.setDefinition(superC.getDefinition());
    	
    	
    	//declaration du type de la classe
        ClassType c = new ClassType(compiler.getSymbolTable().create(this.className.getName().toString()),
        		this.className.getLocation(), superC.getDefinition());
        
        try {
        	compiler.getEnvironmentType().declare(compiler.getSymbolTable()
        			.create(this.className.getName().toString()),c);
        	this.className.setDefinition(c.getDefinition());
            this.className.setType(c);
        } catch (DoubleDefException e) { //pas de double definition possible
        	throw new ContextualError("Declaration d'une classe deja declare precedement", className.getLocation());
        }
        
    }

    @Override
    protected void verifyClassMembers(DecacCompiler compiler)
            throws ContextualError {
        for (AbstractDeclField f : fields.getList()) {
        	f.verifyDeclField(compiler, this.className.getClassDefinition().getMembers(), this.className.getClassDefinition());
        }
        for (AbstractDeclMethod m : methods.getList()) {
        	m.verifyDeclMethod(compiler, this.className.getClassDefinition().getMembers(), this.className.getClassDefinition());
        }
    }
    
    @Override
    protected void verifyClassBody(DecacCompiler compiler) throws ContextualError {
    	for (AbstractDeclField f : fields.getList()) {
    		f.verifyField(compiler, this.className.getClassDefinition().getMembers(), this.className.getClassDefinition());
        }
    	
    	for (AbstractDeclMethod m : methods.getList()) {
    		m.verifyMethod(compiler, this.className.getClassDefinition().getMembers(), this.className.getClassDefinition());
        }
    }


    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
    	this.className.prettyPrint(s, prefix, false);
    	this.extension.prettyPrint(s, prefix, false);
    	this.fields.prettyPrint(s, prefix, false);
    	this.methods.prettyPrint(s, prefix, true);
    }

    @Override
    protected void iterChildren(TreeFunction f) {
    	this.fields.iter(f);
    	this.methods.iter(f);
    }

    
    @Override
	protected void codeGenDeclClass(DecacCompiler compiler) {
        compiler.addInstruction(new LEA(compiler.getEnvironmentClass().get(this.extension.getName()), Register.R0));
        compiler.getEnvironmentClass().put(this.className.getName(), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB));
        compiler.getStackManager().addStackCpt();
        compiler.addInstruction(new STORE(Register.R0, compiler.getEnvironmentClass().get(this.className.getName())));
        
        
        this.methods.codeGenListMethod(compiler);
        
        compiler.addInstruction(new WSTR(new ImmediateString("Je suis une classe")));
	}
}
