package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "/";
    }


	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		Type typeLeft = this.getLeftOperand().getType();
    	Type typeRight = this.getRightOperand().getType();
    	
    	// Cas des entiers
    	if(typeLeft.isInt() && typeRight.isInt()) {
    		compiler.addInstruction(new QUO(op1, op2));
    	}
    	
    	// Cas des flottants
    	else if(typeLeft.isFloat() && typeRight.isFloat()) {
    		compiler.addInstruction(new DIV(op1, op2));
    	}
	}

}
