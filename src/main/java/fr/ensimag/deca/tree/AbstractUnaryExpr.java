package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Unary expression.
 *
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractUnaryExpr extends AbstractExpr {

    public AbstractExpr getOperand() {
        return operand;
    }
    private AbstractExpr operand;
    public AbstractUnaryExpr(AbstractExpr operand) {
        Validate.notNull(operand);
        this.operand = operand;
    }


    protected abstract String getOperatorName();
  
    @Override
    public void decompile(IndentPrintStream s) {
    	s.print("(" + this.getOperatorName());
    	this.getOperand().decompile(s);
    	s.print(")");
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        operand.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        operand.prettyPrint(s, prefix, true);
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister reg = this.getOperand().codeGenReg(compiler);
        codeGenUnary(compiler, reg);
        compiler.getRegManager().freeRegistre(reg.getNumber(), compiler);
    }
    
	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister reg = this.getOperand().codeGenReg(compiler);
        codeGenUnary(compiler, reg);
        return reg;
	}

	/**
	 * Génère le code assembleur pour une expression unaire en fonction de l'opérande
	 * @param compiler
	 * @param op1
	 * @param op2
	 * @return 
	 */
	protected abstract void codeGenUnary(DecacCompiler compiler, GPRegister reg);
}
