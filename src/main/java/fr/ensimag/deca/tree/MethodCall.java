package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Appel d'une méthode de classe ou fonction globale.
 * Ex: a.z.meth(b, c);
 * Ex: func(a);
 * @author zhujo
 *
 */
public class MethodCall extends AbstractExpr{
	
	// expression à gauche du point (dans l'exemple 'a.z')
    private AbstractExpr expr;
    // nom de la méthode (dans l'exemple 'meth')
    private AbstractIdentifier nomMethode;
    // parametres de la méthode (dans l'exemple (b, c)
    private ListExpr listExpr;
    
    public MethodCall(AbstractExpr expr, AbstractIdentifier nomMethode, ListExpr list) {
    	Validate.notNull(expr);
    	Validate.notNull(nomMethode);
    	Validate.notNull(list);
    	this.expr = expr;
    	this.nomMethode = nomMethode;
    	this.listExpr = list;
    }
    
    public MethodCall(AbstractIdentifier nomMethode, ListExpr list) {
    	Validate.notNull(nomMethode);
    	Validate.notNull(list);
    	this.expr = null;
    	this.nomMethode = nomMethode;
    	this.listExpr = list;
    }

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		
		Type retourType;
		//si on fait une selection (appel d'une methode dans un objet)
		if(expr != null) {
			//On recupere la definition de l'objet duquel on veut appeler la methode
			ClassDefinition cDef = expr.verifyExpr(compiler, localEnv, currentClass).asClassType("[SyntaxeContextuelle]" + expr
				.getType() + " isn't a class", expr.getLocation()).getDefinition();
			
			retourType = nomMethode.verifyExpr(compiler, cDef.getMembers(), currentClass);
			
			//Si l'objet n est pas declaré : on renvoie une erreur
			if(cDef.getMembers().get(nomMethode.getName()) == null) {
				throw new ContextualError("[SyntaxeContextuelle]" + nomMethode.getName() + " isn't a method from " + expr.getType().getName(), expr.getLocation());
			}
		}else {//Si on ne fait pas de selection (appel dans l'objet)
			retourType = nomMethode.verifyExpr(compiler, localEnv, currentClass);
			if (localEnv.get(nomMethode.getName()) == null) {
				throw new ContextualError("[SyntaxeContextuelle] The method " + nomMethode.getName() + " isn't defined", nomMethode.getLocation());
			}
		}
		this.setType(retourType);
		//On controle que l'appel se fait avec le bon nombre d'arguments et que leurs types sont corrects
		Signature s = nomMethode.getMethodDefinition().getSignature();
		for (int i = 0 ; i < s.getList().size() ; i++) {
			this.listExpr.getList().get(i).verifyRValue(compiler, localEnv, currentClass, s.paramNumber(i));
		}
		
		
		
		return this.getType();
	}

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		if(this.expr != null) {
			this.expr.prettyPrint(s, prefix, false);
		}
		this.nomMethode.prettyPrint(s, prefix, false);
		this.listExpr.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
