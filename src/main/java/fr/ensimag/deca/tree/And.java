package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.MUL;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }
    
    /**
     * Génère le code dans le cas ou l'on a pas besoin de stocker l'opération
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
        compiler.addInstruction(new MUL(regGauche, regDroite));
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
    	// Le AND équivaut à un MUL
        compiler.addInstruction(new MUL(regGauche, regDroite));
        
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
        return regDroite;
	}
	
	 /**
     * Génère le code pour l'opération booléenne correspondante. Lorsque b vaut vrai,
     * on jump si le résultat est faux.
     */
	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, Label labelFin, boolean b) {
		compiler.addComment(this.getOperatorName());
    	
        //compiler.addInstruction(new CMP(new ImmediateInteger(1), regDroite));
        if(b) {
        	this.getLeftOperand().codeGenBool(compiler, labelFin, label, !b);
        	this.getRightOperand().codeGenBool(compiler, labelFin, label, !b);
        }
        else {
        	this.getLeftOperand().codeGenBool(compiler, label, labelFin, b);
        	this.getRightOperand().codeGenBool(compiler, label, labelFin, b);
        }
        //return regGauche;
	}
}
