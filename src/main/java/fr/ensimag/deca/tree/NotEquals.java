package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class NotEquals extends AbstractOpExactCmp {

    public NotEquals(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "!=";
    }

	@Override
	protected void codeGenOpCmp(DecacCompiler compiler, Label label, boolean b) {
		if(b) {
            compiler.addInstruction(new BNE(label));
        	
        }
        else {
            compiler.addInstruction(new BEQ(label));
        }
	}
}
