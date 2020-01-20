package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

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
    	//On recupere les types des deux operandes
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        //type := type _ binary _ op(op, type 1 , type 2 )
        if (!(type1.isInt() || type1.isFloat())) {
        	throw new ContextualError("Type " + type1.toString() + " non supporté par l'operation " + this.getOperatorName(), this.getLeftOperand().getLocation());
        } else if(!(type2.isInt() || type2.isFloat())){
        	throw new ContextualError("Type " + type2.toString() + " non supporté par l'operation " + this.getOperatorName(), this.getRightOperand().getLocation());
        }
        
        
        
        if (type1.sameType(type2)){
        	this.setType(type1);
        	return this.getType();
        } 
        //Dans le cas d'un convfloat
        else if (type1.isInt() && type2.isFloat()) //Convfloat si l'un est int et l'autre float
        {
        	//Creation d'un nouveau noeud Convfloat
        	ConvFloat c = new ConvFloat(this.getLeftOperand());
        	//On l'insere
        	this.setLeftOperand(c);
        	type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        	this.setType(type2);
        	return this.getType();
        }
        else if(type1.isFloat() && type2.isInt())//Convfloat si l'un est int et l'autre float
        {
        	//Creation d'un nouveau noeud Convfloat
        	ConvFloat c = new ConvFloat(this.getRightOperand());
        	//On l'insere
        	this.setRightOperand(c);
        	type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        	this.setType(type1);
        	return this.getType();
        }
        
        
        throw new ContextualError("Operation entre un element de type " + type1.toString()
        + " et un element de type " + type2.toString(), getLocation());
    	
    }
    
   
}
