package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

   	@Override
	protected void codeGenOpCmp(DecacCompiler compiler, Label label, boolean b) {
		if(b) {
            compiler.addInstruction(new BLT(label));
        	
        }
        else {
            compiler.addInstruction(new BGE(label));
        }
	}
}
