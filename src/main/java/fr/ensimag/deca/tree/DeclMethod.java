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
import fr.ensimag.ima.pseudocode.IMAProgram;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;

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
					.get(methName.getName()).asMethodDefinition(methName.getName().toString()
							+ " n'est pas une methode", methName.getLocation());
			//On compare les deux signatures
			if(!methSuperC.getSignature().equals(s)) {
				throw new ContextualError("Redefinition de methode avec deux signatures differentes", methName.getLocation());
			}
				
				
			//on s'assure que le type de retour de la fonction parent est un parent du type de retour de la nouvelle fonction
			if(t.isClass()) {
				//on recupere la ClassDefinition du type de retour de la methode fille
				ClassDefinition cDefThis = compiler.getEnvironmentType().get(t.getName())
						.asClassType(t.getName().toString() + " n'est pas une classe", this.returnType.getLocation()).getDefinition();
				//on recupere la ClassDefinition du type de retour de la methode mere
				ClassDefinition cDefSuper = compiler.getEnvironmentType().get(methSuperC.getType().getName())
						.asClassType(methSuperC.getType().getName().toString() + " n'est pas une classe", methSuperC.getLocation()).getDefinition();
				//on les compares
				if(!cDefThis.hasForParent(cDefSuper)) {
					throw new ContextualError("Le type de retour de la fonction " + cDefThis.getType().toString() 
							+ " doit etre herite de " + cDefSuper.getType().toString(), this.returnType.getLocation());
				}
			}
		}
		
		//definition de la methode
		MethodDefinition methDef = new MethodDefinition(t, methName.getLocation(), s, currentClass.getNumberOfMethods());
		
		//declaration de la methode dans l'environement local
		try {
			
			localEnv.declare(this.methName.getName(), methDef);
			methName.setDefinition(methDef);
			methName.setType(t);
			
		}catch(DoubleDefException e) {
			throw new ContextualError("Methode deja definie", this.methName.getLocation());
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
				throw new ContextualError("Parametre deja déclaré", p.getLocation());
			}
		}
		
		this.corps.verifyMethodBody(compiler, envParam, currentClass, t);
	}
	
	@Override
	protected void codeGenDeclMethod(DecacCompiler compiler, AbstractIdentifier className) {
		//bloc de début de méthode
		compiler.addIMABloc();
		
		// label du corps de la méthode
		Label methLabel = new Label("code."+className.getName()+"."+this.methName.getName().getName());
		compiler.addLabel(methLabel);
		compiler.addInstruction(new BOV(ErrorManager.tabLabel[0]));
		
		this.params.codeGenListParamIn(compiler);
		
		this.corps.codeGenMethodBody(compiler, className);
		
		// bloc de fin de méthode
		compiler.addIMABloc();
		// label de la fin de la méthode
		Label methLabelFin = new Label("fin."+className.getName()+"."+this.methName.getName().getName());
		compiler.addLabel(methLabelFin);

		this.params.codeGenListParamOut(compiler);
		
		this.returnType.codeGenReturn(compiler);
		
	}

}
