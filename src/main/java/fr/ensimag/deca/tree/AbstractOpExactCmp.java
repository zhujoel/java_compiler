package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractOpExactCmp extends AbstractOpCmp {

    public AbstractOpExactCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        if (type1.sameType(type2)) {
        	this.setType(compiler.getType("boolean"));
        	return this.getType();
        } else if(type1.isClass() && type2.isClass()) {
        	this.setType(compiler.getType("boolean"));
        	return this.getType();
        }
        throw new ContextualError("[SyntaxeContextuelle]Comparison between two different types (" 
        + type1.toString() + " & " + type2.toString() + ")", getLocation());
    }


}
