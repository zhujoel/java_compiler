package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		compiler.addInstruction(new SUB(op1, op2));
		compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
	}
    
}
