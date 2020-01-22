package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
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
     * Génère le code pour l'opération booléenne correspondante. Lorsque b vaut vrai,
     * on jump si le résultat est faux.
     */
	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
		compiler.addComment(this.getOperatorName());
        //compiler.addInstruction(new CMP(new ImmediateInteger(1), regDroite));
		
		Label labFin = new Label("Fin_and_"+ compiler.getRegManager().getNAnd());
		compiler.getRegManager().addNAnd();
		
        if(b) {
        	this.getLeftOperand().codeGenBool(compiler, labFin, !b);
        	this.getRightOperand().codeGenBool(compiler, label, b);
        	compiler.addLabel(labFin);
        }
        else {
        	this.getLeftOperand().codeGenBool(compiler, label, b);
        	this.getRightOperand().codeGenBool(compiler, label, b);
        }
	}

	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		// TODO Auto-generated method stub
		compiler.addInstruction(new MUL(op1, op2));
	}
}
