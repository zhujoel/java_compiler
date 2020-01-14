package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Assignment, i.e. lvalue = expr.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Assign extends AbstractBinaryExpr {

    @Override
    public AbstractLValue getLeftOperand() {
        // The cast succeeds by construction, as the leftOperand has been set
        // as an AbstractLValue by the constructor.
        return (AbstractLValue)super.getLeftOperand();
    }

    public Assign(AbstractLValue leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	//TODO gerer les convfloat
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        this.setType(type1);
        this.setRightOperand(this.getRightOperand().verifyRValue(compiler, localEnv, currentClass, type1));
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "=";
    }

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		GPRegister reg = getRightOperand().codeGenReg(compiler);
		
		int sLocation = ((Identifier)this.getLeftOperand()).getStackLocation();
		System.out.println("sLoc dans Assign : " + sLocation);
        compiler.addInstruction(new STORE(reg, new RegisterOffset(sLocation, Register.GB)));
	}

}
