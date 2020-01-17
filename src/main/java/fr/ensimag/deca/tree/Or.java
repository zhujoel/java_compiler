package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.MUL;
import fr.ensimag.ima.pseudocode.instructions.SHR;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Or extends AbstractOpBool {

    public Or(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    
    @Override
    protected String getOperatorName() {
        return "||";
    }

    
    /**
     * Génère le code dans le cas ou l'on a pas besoin de stocker l'opération
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
    	// Le OR équivaut à un ADD décalé vers la droite (dans le cas 0x0001, le décalage à droite n'est pas fait)
        compiler.addInstruction(new ADD(regGauche, regDroite));
        compiler.addInstruction(new SHR(regDroite));
        
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
    	
    	// Le OR équivaut à un ADD décalé vers la droite (dans le cas 0x0001, le décalage à droite n'est pas fait)
        compiler.addInstruction(new ADD(regGauche, regDroite));
        compiler.addInstruction(new SHR(regDroite));
        
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
        return regDroite;
	}

}
