package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BGT;
import fr.ensimag.ima.pseudocode.instructions.BLE;
import fr.ensimag.ima.pseudocode.instructions.CMP;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class LowerOrEqual extends AbstractOpIneq {
    public LowerOrEqual(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "<=";
    }


    @Override
   	protected GPRegister codeGenReg(DecacCompiler compiler) {
   		compiler.addComment(this.getOperatorName());
       	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
       	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
       	
           compiler.addInstruction(new CMP(regGauche, regDroite));
           compiler.getRegManager().freeRegistre(regDroite.getNumber());
           return regGauche;
   	}
   	
   	@Override
   	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
   		compiler.addComment(this.getOperatorName());
       	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
       	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
       	
        compiler.addInstruction(new CMP(regGauche, regDroite));
        if(b) {
            compiler.addInstruction(new BGT(label));
        }
        else {
            compiler.addInstruction(new BLE(label));
        }
        compiler.getRegManager().freeRegistre(regDroite.getNumber());
        //return regGauche;
   	}

}
