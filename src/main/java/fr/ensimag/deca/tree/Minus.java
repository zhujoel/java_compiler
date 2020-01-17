package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.SUB;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Minus extends AbstractOpArith {
    public Minus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "-";
    }

    /**
     * Génère le code dans le cas ou l'on a pas besoin de stocker l'opération
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
    	// On génère le code correspondant à l'opération, et on fait un test en cas de debordement
        compiler.addInstruction(new SUB(regGauche, regDroite));
    	compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
    	
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
    	
    	// On génère le code correspondant à l'opération, et on fait un test en cas de debordement
        compiler.addInstruction(new SUB(regGauche, regDroite));
    	compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
    	
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
        return regDroite;
	}
    
}
