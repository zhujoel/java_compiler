package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Multiply extends AbstractOpArith {
    public Multiply(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "*";
    }


    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
        compiler.addInstruction(new MUL(regGauche, regDroite));

    }


	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
        compiler.addInstruction(new MUL(regGauche, regDroite));
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        return regGauche;
	}
}
