package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Not extends AbstractUnaryExpr {

    public Not(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
        if (!t.isBoolean()) { //type := type _ unary _ op(op, type 1 )
        	throw new ContextualError("Type non supporté par l'opération \"!\"", this.getOperand().getLocation());
        }
        this.setType(t);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister reg = this.getOperand().codeGenReg(compiler);
    	GPRegister regTemp = compiler.getRegManager().getRegistreLibre();
        compiler.addInstruction(new LOAD(new ImmediateInteger(1), regTemp));
        compiler.addInstruction(new SUB(regTemp, reg));
    }
    
	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister reg = this.getOperand().codeGenReg(compiler);
    	GPRegister regTemp = compiler.getRegManager().getRegistreLibre();
        compiler.addInstruction(new LOAD(new ImmediateInteger(1), regTemp));
        compiler.addInstruction(new SUB(regTemp, reg));
        return regTemp;
	}
	
	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
		this.getOperand().codeGenBool(compiler, label, !b);
	}
}
