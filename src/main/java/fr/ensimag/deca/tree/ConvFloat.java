package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.FLOAT;

/**
 * Conversion of an int into a float. Used for implicit conversions.
 * 
 * @author gl48
 * @date 01/01/2020
 */
public class ConvFloat extends AbstractUnaryExpr {
    public ConvFloat(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) {
        this.setType(compiler.getType("float"));
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "/* conv float */";
    }

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
    	GPRegister reg = this.getOperand().codeGenReg(compiler);
		compiler.addInstruction(new FLOAT(reg, reg));
    	return reg;
	}
	

	@Override
	protected void codeGenUnary(DecacCompiler compiler, GPRegister reg) {
		//compiler.addInstruction(new FLOAT(reg, reg));
	}

}
