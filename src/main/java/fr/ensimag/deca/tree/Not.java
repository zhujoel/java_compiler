package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
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
        	throw new ContextualError("[SyntaxeContextuelle]Type not supported by the operation \"!\"", this.getOperand().getLocation());
        }
        this.setType(t);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "!";
    }

	
	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
		this.getOperand().codeGenBool(compiler, label, !b);
	}

	@Override
	protected void codeGenUnary(DecacCompiler compiler, GPRegister reg) {
		compiler.addInstruction(new SUB(new ImmediateInteger(1), reg));
	}
}
