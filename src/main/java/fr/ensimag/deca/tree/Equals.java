package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Equals extends AbstractOpExactCmp {

    public Equals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "==";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
        compiler.addInstruction(new CMP(regGauche, regDroite));
        compiler.addInstruction(new BEQ(new Label("Equal")));
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
    }

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
        compiler.addInstruction(new CMP(regGauche, regDroite));
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        return regGauche;
	}
	
	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
        compiler.addInstruction(new CMP(regGauche, regDroite));
        if(b) {
            compiler.addInstruction(new BNE(label));
        	
        }
        else {
            compiler.addInstruction(new BEQ(label));
        }
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        //return regGauche;
	}
    
}
