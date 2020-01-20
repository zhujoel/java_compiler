package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGE;
import fr.ensimag.ima.pseudocode.instructions.BLT;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Lower extends AbstractOpIneq {

    public Lower(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<";
    }

    /**
     * Génère le code pour l'opération booléenne correspondante, lorsque celle ci est appelée
     * hors d'un if et d'un while. Il n'y a donc pas de saut.
     */
    @Override
   	protected GPRegister codeGenReg(DecacCompiler compiler) {
   		compiler.addComment(this.getOperatorName());
       	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
       	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
       	
           compiler.addInstruction(new CMP(regGauche, regDroite));
           compiler.getRegManager().freeRegistre(regDroite.getNumber());
           return regGauche;
   	}
   	
    /**
     * Génère le code pour l'opération booléenne correspondante. Lorsque b vaut vrai,
     * on jump si le résultat est faux.
     */
   	@Override
   	protected void codeGenBool(DecacCompiler compiler, Label label,Label labelFin, boolean b) {
   		compiler.addComment(this.getOperatorName());
       	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
       	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
       	
        compiler.addInstruction(new CMP(regDroite, regGauche));
        if(b) {
            compiler.addInstruction(new BLT(label));
        }
        else {
            compiler.addInstruction(new BGE(label));
        }
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        //return regGauche;
   	}

}
