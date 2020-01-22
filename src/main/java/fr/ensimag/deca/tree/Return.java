package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Instruction de retour d'une méthode.
 * @author zhujo
 *
 */
public class Return extends AbstractInst {
	
	// valeur de retour
	private AbstractExpr expr;

	public Return(AbstractExpr expr) {
		Validate.notNull(expr);
		this.expr = expr;
	}
	
	@Override
	protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass,
			Type returnType) throws ContextualError {
		this.expr = this.expr.verifyRValue(compiler, localEnv, currentClass, returnType);
		if(this.expr.getType().isVoid()) {
			throw new ContextualError("Retour void", this.expr.getLocation());
		}
		
	}

	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("return ");
		this.expr.decompile(s);
		s.print(";");
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.expr.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
