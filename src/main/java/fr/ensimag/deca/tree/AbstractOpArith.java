package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 * Arithmetic binary operations (+, -, /, ...)
 * 
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractOpArith extends AbstractBinaryExpr {

    public AbstractOpArith(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        if (type1.sameType(type2)) {
        	this.setType(type1);
        	return type1;
        } 
        //Dans le cas d'un convfloat
        else if (type1.isInt() && type2.isFloat()) {
        	ConvFloat c = new ConvFloat(this.getLeftOperand());
        	this.setLeftOperand(c);
        	type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        	return type2;
        }
        else if(type1.isFloat() && type2.isInt()){
        	ConvFloat c = new ConvFloat(this.getRightOperand());
        	this.setRightOperand(c);
        	type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        	return type1;
        }
        throw new ContextualError("Operation entre deux elements de type differents", getLocation());
    	
    }
}
