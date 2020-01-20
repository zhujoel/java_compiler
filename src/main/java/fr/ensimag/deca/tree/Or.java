package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.SHR;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    
    @Override
    protected String getOperatorName() {
        return "||";
    }


	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, Label labelFin, boolean b) {
		compiler.addComment(this.getOperatorName());
    	
        //compiler.addInstruction(new CMP(new ImmediateInteger(1), regDroite));
        if(b) {
        	this.getLeftOperand().codeGenBool(compiler, label, labelFin, b);
        	this.getRightOperand().codeGenBool(compiler, labelFin, label, !b); 
        }
        else {
        	this.getLeftOperand().codeGenBool(compiler, labelFin, label, !b);
        	this.getRightOperand().codeGenBool(compiler, label, labelFin, b);
        }
        //return regGauche;
	}


	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		compiler.addInstruction(new ADD(op1, op2));
	    compiler.addInstruction(new SHR(op2));
		
	}
}
