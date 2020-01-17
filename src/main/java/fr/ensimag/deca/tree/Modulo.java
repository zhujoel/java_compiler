package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
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
        	throw new ContextualError("Type non supporté pour un modulo (le nombre doit etre entier)", 
        			this.getLeftOperand().getLocation());
        } else if (!type2.isInt()) {
        	throw new ContextualError("Type non supporté pour un modulo (le nombre doit etre entier)", 
        			this.getRightOperand().getLocation());
        }
        this.setType(compiler.getType("int"));
        return this.getType();
    }


    @Override
    protected String getOperatorName() {
        return "%";
    }

    /**
     * Génère le code dans le cas ou l'on a pas besoin de stocker l'opération
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
        compiler.addInstruction(new REM(regGauche, regDroite));
        
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
    }

    /**
     * Génère le code correspondant à l'opération, et renvoie le registre dans lequel
     * le résultat est stocké.
     */
	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
        compiler.addInstruction(new REM(regGauche, regDroite));
        
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
        return regDroite;
	}
}
