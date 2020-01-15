package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;
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

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	// Le OR équivaut à un ADD décalé vers la droite (dans le cas 0x0001, le décalage à droite n'est pas fait)
        compiler.addInstruction(new ADD(regGauche, regDroite));
        compiler.addInstruction(new SHR(regGauche));
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        return regGauche;
	}

}
