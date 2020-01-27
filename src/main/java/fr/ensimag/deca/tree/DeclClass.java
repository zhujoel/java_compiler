package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Map;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassType;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LEA;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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
    
    

	// Constructeur pour la classe Object
	public DeclClass(AbstractIdentifier className, 
			ListDeclField fields, ListDeclMethod methods) {
		Validate.notNull(className);
		Validate.notNull(fields);
		Validate.notNull(methods);
		this.extension = null;
		this.className = className;
		this.fields = fields;
		this.methods = methods;
	}
	
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
    		throw new ContextualError("[SyntaxeContextuelle] Class undefined", extension.getLocation());
    	}
    	
    	//On recupere le type de la superclasse
    	ClassType superC = compiler.getEnvironmentType().get(extension.getName())
    			.asClassType("[SyntaxeContextuelle] The class must inherit from an existing class"
    					, extension.getLocation());
        this.extension.setType(superC.getDefinition().getType());
        this.extension.setDefinition(superC.getDefinition());
    	
    	
    	//declaration du type de la classe
        ClassType c = new ClassType(compiler.getSymbolTable().create(this.className.getName().toString()),
        		this.className.getLocation(), superC.getDefinition());
    	this.className.setDefinition(c.getDefinition());
        this.className.setType(c);
        
        try {
        	compiler.getEnvironmentType().declare(compiler.getSymbolTable()
        			.create(this.className.getName().toString()),c);
        } catch (DoubleDefException e) { //pas de double definition possible
        	throw new ContextualError("[SyntaxeContextuelle] Trying to declare a class that already exist", className.getLocation());
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

    

	/**
	 * Génère le code assembleur des prototypes des méthodes.
	 * @param className Nom de la classe
	 * @param compiler Compilo
	 * @param underMethod Méthodes de la classes fille (pour comparer avec celles de la superclass et voir s'il y a des redéfinitions)
	 */

	public void codeGenPrototypeMethod(Symbol className, DecacCompiler compiler, EnvironmentExp underMethod) {

		// On récupère le champ classe type dans l'environnement car celui-ci possède
		// toutes les informations concernant la classe
		ClassType clType = (ClassType)compiler.getEnvironmentType().get(className);

		// on récupère les champs de la classe
		EnvironmentExp clEnv = clType.getDefinition().getMembers();

		// on parcourt récursivement pour récupérer toutes les fonctions des superclasses
		if(clType.getDefinition().hasSuperclass()) {
			Symbol superclass = clType.getDefinition().getSuperClass().getType().getName();
			codeGenPrototypeMethod(superclass, compiler, clEnv);
		}


		// on compare les méthodes avec les méthodes filles pour détecter des redéfinitions
		for(Map.Entry<Symbol, ExpDefinition> entry : clEnv.getEnv().entrySet()) {
			// on génère l'assembleur pour les méthodes seulement et s'il n'y a pas de redéfinition
			if(entry.getValue() instanceof MethodDefinition && !underMethod.isIn(entry.getKey())) {
				// génération du load et store
				Label label = new Label("code."+className.getName()+"."+entry.getKey().getName());
				compiler.addInstruction(new LOAD(new LabelOperand(label), Register.R0));
				compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
				compiler.getStackManager().addStackCpt();
			}
		}
	}


	@Override
	protected void codeGenDeclClass(DecacCompiler compiler) {

		// Passe 1 : on génère le prototype des méthodes
		compiler.addComment("Code de la table des méthodes de " + this.className.getName().getName());
		compiler.addInstruction(new LEA(compiler.getEnvironmentClass().get(this.extension.getName()), Register.R0));
		compiler.getEnvironmentClass().put(this.className.getName(), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB));
		compiler.getStackManager().addStackCpt();
		compiler.addInstruction(new STORE(Register.R0, compiler.getEnvironmentClass().get(this.className.getName())));

		// génération du code des prototypes des méthodes
		codeGenPrototypeMethod(this.className.getName(), compiler, new EnvironmentExp(null));

		// génération des attributs
		// on active le storing car on veut générer le code à la fin du programme
		compiler.activateStoring();
		this.fields.codeGenListField(compiler, this.className.getName());
		
		// Passe 2 : on génère le corps des méthodes
		// on donne le className pour générer le bon label
		this.methods.codeGenListMethod(compiler, this.className);
		compiler.deactivateStoring();
	}

	@Override
	public int getFieldNb() {
		return this.fields.getList().size();
	}
}
