package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

/**
 * Déclaration d'une méthode d'une classe.
 * Ex: int methName(int param1, int param2){ corps }
 * @author zhujo
 *
 */
public class DeclMethod extends AbstractDeclMethod {
	private static final Logger LOG = Logger.getLogger(DeclVar.class);
	
	// type du retour de la méthode
    final private AbstractIdentifier returnType;
    // nom de la méthode
    final private AbstractIdentifier methName;
    // paramètres de la méthode
    final private ListDeclParam params;
    // corps de la méthode
    final private AbstractMethodBody corps;
    
    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methName,
    		ListDeclParam params, ListDeclVar decls, ListInst insts) {
    	Validate.notNull(returnType);
        Validate.notNull(methName);
        Validate.notNull(params);
        Validate.notNull(decls);
        Validate.notNull(insts);
        this.returnType = returnType;
        this.methName = methName;
        this.params = params;
        this.corps = new MethodBody(decls, insts);
    }
    
    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methName,
    		ListDeclParam params, AbstractMethodBody body) {
    	Validate.notNull(returnType);
        Validate.notNull(methName);
        Validate.notNull(params);
        Validate.notNull(body);
        this.returnType = returnType;
        this.methName = methName;
        this.params = params;
        this.corps = body;
    }
    
	@Override
	public void decompile(IndentPrintStream s) {
		this.returnType.decompile(s);
		s.print(" ");
		this.methName.decompile(s);
		this.params.decompile(s);
		this.corps.decompile(s);
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.returnType.prettyPrint(s, prefix, false);
		this.methName.prettyPrint(s, prefix, false);
		this.params.prettyPrint(s, prefix, false);
		this.corps.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}
	
	//TODO surement a finir
	@Override
	public void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
		Type t = this.returnType.verifyType(compiler);
		Signature s = new Signature();
		
		//incrementation du nombre de methode dans la classe courrante
		currentClass.incNumberOfMethods();
		
		//creation de la signature
		for (AbstractDeclParam p : params.getList()) {
			s.add(p.verifyDeclParam(compiler, localEnv, currentClass));
		}
		
		
			
		//recuperation de la premiere classe parent qui contient la definition de la methode (en partant de currentClass)
		ClassDefinition sC = currentClass.getFirstSuperClassWithDef(methName.getName());
		
		//si la methode est bien definie dans une superClasse, sc n'est pas null
		if (sC != null) {
			//recuperation de la methode dans cette classe parent
			MethodDefinition methSuperC = sC.getMembers()
					.get(methName.getName()).asMethodDefinition("[SyntaxeContextuelle] " + methName.getName()
							+ " isn't a method", methName.getLocation());
			//On compare les deux signatures
			if(!methSuperC.getSignature().equals(s)) {
				throw new ContextualError("[SyntaxeContextuelle] Trying to redefine a function with a different signature", 
						methName.getLocation());
			}
				
				
			//on s'assure que le type de retour de la fonction parent est un parent du type de retour de la nouvelle fonction
			if(t.isClass()) {
				//on recupere la ClassDefinition du type de retour de la methode fille
				ClassDefinition cDefThis = compiler.getEnvironmentType().get(t.getName())
						.asClassType("[SyntaxeContextuelle] " + t.getName() + " isn't a class", this.returnType.getLocation()).getDefinition();
				//on recupere la ClassDefinition du type de retour de la methode mere
				ClassDefinition cDefSuper = compiler.getEnvironmentType().get(methSuperC.getType().getName())
						.asClassType("[SyntaxeContextuelle] " + methSuperC.getType().getName() + " isn't a class", methSuperC.getLocation()).getDefinition();
				//on les compares
				if(!cDefThis.hasForParent(cDefSuper)) {
					throw new ContextualError("[SyntaxeContextuelle] The type returned by the function  " + cDefThis.getType() 
							+ " must inherit from " + cDefSuper.getType().toString(), this.returnType.getLocation());
				}
			}
		}
		
		//definition de la methode
		MethodDefinition methDef = new MethodDefinition(t, methName.getLocation(), s, currentClass.getNumberOfMethods());
		methName.setDefinition(methDef);
		methName.setType(t);
		//declaration de la methode dans l'environement local
		try {
			
			localEnv.declare(this.methName.getName(), methDef);
			
		}catch(DoubleDefException e) {
			throw new ContextualError("[SyntaxeContextuelle] Method already defined", this.methName.getLocation());
		}
	}

	@Override
	public void verifyMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type t = this.returnType.getType();
		//instanciation de l'environement de la methode
		EnvironmentExp envParam = new EnvironmentExp(localEnv);
		
		//declaration des parametre et ajout dans la methode
		for (AbstractDeclParam p : this.params.getList()) {
			try {
				//TODO a changer
				envParam.declare(p.getName(), p.getExpDefinition());
			}catch(DoubleDefException e) {
				throw new ContextualError("[SyntaxeContextuelle] Parameter already declared", p.getLocation());
			}
		}
		
		this.corps.verifyMethodBody(compiler, envParam, currentClass, t);
	}
	
	@Override
	protected void codeGenDeclMethod(DecacCompiler compiler, AbstractIdentifier className) {
    	// On défini un nouveau environnement pour les variables locales
    	EnvironmentExp localEnv = new EnvironmentExp(compiler.getEnvironmentExp());
		
		//bloc de début de méthode
		compiler.addIMABloc();
		
		// label du corps de la méthode
		Label methLabel = new Label("code."+className.getName()+"."+this.methName.getName().getName());
		compiler.addLabel(methLabel);
		
		this.params.codeGenListParamIn(compiler, localEnv);
		
		this.corps.codeGenMethodBody(compiler, className, localEnv);
		
		compiler.addSecond(new ADDSP(new ImmediateInteger(this.params.size()+this.corps.getNbVarLocal())));
		compiler.addSecond(new BOV(ErrorManager.tabLabel[0]));
		compiler.addSecond(new TSTO(new ImmediateInteger(this.params.size()+this.corps.getNbVarLocal())));
		
		// bloc de fin de méthode
		compiler.addIMABloc();
		// label de la fin de la méthode
		Label methLabelFin = new Label("fin."+className.getName()+"."+this.methName.getName().getName());
		compiler.addLabel(methLabelFin);

		this.params.codeGenListParamOut(compiler, localEnv);
		
		
		this.returnType.codeGenReturn(compiler);
		
	}

}
