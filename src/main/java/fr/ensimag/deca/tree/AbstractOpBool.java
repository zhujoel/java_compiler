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
public abstract class AbstractOpBool extends AbstractBinaryExpr {

    public AbstractOpBool(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	//On recupere les types des deux operandes
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        //Controle du type
        if (!type1.isBoolean()) {
        	throw new ContextualError("[SyntaxeContextuelle]Type " 
        			+ type1.toString() + " used on a boolean operation", 
        			this.getLeftOperand().getLocation());
        } else if(!type2.isBoolean()) {
        	throw new ContextualError("[SyntaxeContextuelle]Type " 
        			+ type2.toString() + " used on a boolean operation", 
        			this.getLeftOperand().getLocation());
        }
        
        this.setType(compiler.getType("boolean"));
        return this.getType();        
        
        
    }
  
}
