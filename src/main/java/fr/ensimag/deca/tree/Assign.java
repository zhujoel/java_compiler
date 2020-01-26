package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

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
		// Normalement cette fonction n'est pas appelée.
		return null;
	}
	
	@Override
	protected void codeGenInst(DecacCompiler compiler) {
		GPRegister reg = getRightOperand().codeGenReg(compiler);
		if(getLeftOperand() instanceof Identifier) {
			Identifier leftOp = (Identifier)getLeftOperand();
			ExpDefinition expDef = compiler.getEnvironmentExp().get(leftOp.getName());
	        compiler.addInstruction(new STORE(reg, expDef.getOperand()));
	        compiler.getRegManager().freeRegistre(reg.getNumber(), compiler);
		}
		else if(getLeftOperand() instanceof Selection){
			Selection leftOp = (Selection)getLeftOperand();
			leftOp.codeGenReg(compiler);
		}
	}
	
	@Override
	protected void codeGenInst(DecacCompiler compiler, AbstractIdentifier className, EnvironmentExp localEnv) {
		GPRegister reg = getRightOperand().codeGenReg(compiler);
		if(getLeftOperand() instanceof Identifier) {
			Identifier leftOp = (Identifier)getLeftOperand();
			ExpDefinition expDef = compiler.getEnvironmentExp().get(leftOp.getName());
	        compiler.addInstruction(new STORE(reg, expDef.getOperand()));
	        compiler.getRegManager().freeRegistre(reg.getNumber(), compiler);
		}
		else if(getLeftOperand() instanceof Selection){
			Selection leftOp = (Selection)getLeftOperand();
			GPRegister reg2 = leftOp.codeGenReg(compiler, className);
			int offset = compiler.getEnvironmentType().getAttributeOffset(className, leftOp.getIdent());
			compiler.addInstruction(new STORE(reg, new RegisterOffset(offset, reg2)));
			compiler.getRegManager().freeRegistre(reg.getNumber(), compiler);
			compiler.getRegManager().freeRegistre(reg2.getNumber(), compiler);
		}
	}
	
	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		// Normalement cette fonction n'est pas appelée.
		
	}

}
