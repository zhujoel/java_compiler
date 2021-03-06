package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.REM;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Modulo extends AbstractOpArith {

    public Modulo(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	//On recupere les types des deux operandes
        Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        //type _ binary _ op(mod, int, int) = int
        if (!type1.isInt()) {
        	throw new ContextualError("[SyntaxeContextuelle]Unsupported type for % (must be an integer)", 
        			this.getLeftOperand().getLocation());
        } else if (!type2.isInt()) {
        	throw new ContextualError("[SyntaxeContextuelle]Unsupported type for % (must be an integer)", 
        			this.getRightOperand().getLocation());
        }
        this.setType(compiler.getType("int"));
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		compiler.addInstruction(new REM(op1, op2));
	}
}
