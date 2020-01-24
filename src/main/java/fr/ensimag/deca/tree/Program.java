package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.HALT;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        
        //Construction de la classe object
        ClassType o = compiler.getEnvironmentType().get(compiler.getSymbolTable()
        		.create("Object")).asClassType("Object n'est pas une classe", Location.BUILTIN);
        ClassDefinition oDef = o.getDefinition();
        
        
        //methode equals
        oDef.incNumberOfMethods();
        Signature s = new Signature();
        s.add(compiler.getType("Object"));
        MethodDefinition equalsDef = new MethodDefinition(compiler.getType("bool"), Location.BUILTIN, s, oDef.getNumberOfMethods());
        try {
        	o.getDefinition().getMembers().declare(compiler.getSymbolTable()
        			.create("equals"), equalsDef);
        }catch(DoubleDefException e) {
        	throw new ContextualError("equals a deja été declaré", Location.BUILTIN);
        }
        
        
        //PASSE 1
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClass(compiler);
        }
        //PASSE 2
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClassMembers(compiler);
        }
        //PASSE 3
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClassBody(compiler);
        }
        
        //on ne met pas liste_decl pour le hello world mais il faudra l'ajouter
        main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code

        compiler.addComment("Class Declaration");
    	classes.codeGenListClass(compiler);
        compiler.addComment("Main Function");
        main.codeGenMain(compiler);
        compiler.getRegManager().clearStack(compiler);
        compiler.addInstruction(new HALT());
        compiler.appendAllBlocs();
        //ErrorManager.addErrorLabels(compiler);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
