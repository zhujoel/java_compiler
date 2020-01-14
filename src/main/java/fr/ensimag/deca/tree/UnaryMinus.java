package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class UnaryMinus extends AbstractUnaryExpr {

    public UnaryMinus(AbstractExpr operand) {
        super(operand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type t = this.getOperand().verifyExpr(compiler, localEnv, currentClass);
    	if(!(t.isInt() || t.isFloat())) { //type := type _ unary _ op(op, type 1 )
    		throw new ContextualError("Type non supporté par l'opération \"-\"", 
    			this.getOperand().getLocation());
    	}
    	this.setType(t);
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

}
