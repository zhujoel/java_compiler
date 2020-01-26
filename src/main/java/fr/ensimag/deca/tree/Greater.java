package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Greater extends AbstractOpIneq {

    public Greater(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return ">";
    }
   
	
	@Override
	protected void codeGenOpCmp(DecacCompiler compiler, Label label, boolean b) {
		if(b) {
            compiler.addInstruction(new BGT(label));
        	
        }
        else {
            compiler.addInstruction(new BLE(label));
        }
	}
}
