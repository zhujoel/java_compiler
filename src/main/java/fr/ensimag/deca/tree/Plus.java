package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	//VariableDefinition varDef = new VariableDefinition(this.getLeftOperand().getType(), this.getLeftOperand().getLocation());
    	//varDef.setO
    	//System.out.println("operand:" + varDef.getOperand());
    	//compiler.addInstruction();
        compiler.addInstruction(new ADD(regGauche, regDroite));

    }


	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	//VariableDefinition varDef = new VariableDefinition(this.getLeftOperand().getType(), this.getLeftOperand().getLocation());
    	//varDef.setO
    	//System.out.println("operand:" + varDef.getOperand());
    	//compiler.addInstruction();
        compiler.addInstruction(new ADD(regGauche, regDroite));
        return regGauche;
	}
}
