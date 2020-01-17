package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Appel d'une méthode.
 * Ex: a.z.meth(b, c);
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

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		// TODO Auto-generated method stub
		return null;
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
		this.expr.prettyPrint(s, prefix, false);
		this.nomMethode.prettyPrint(s, prefix, false);
		this.listExpr.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
