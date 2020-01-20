package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractOpCmp extends AbstractBinaryExpr {

    public AbstractOpCmp(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	Type type1 = this.getLeftOperand().verifyExpr(compiler, localEnv, currentClass);
        Type type2 = this.getRightOperand().verifyExpr(compiler, localEnv, currentClass);
        
        if (type1.sameType(type2)) {
        	this.setType(compiler.getType("bool"));
        	return this.getType();
        } 
        throw new ContextualError("Comparaison entre deux elements de type differents (" 
        + type1.toString() + " & " + type2.toString() + ")", getLocation());
    }
    
	/**
     * Génère le code pour l'opération booléenne correspondante. Lorsque b vaut vrai,
     * on jump si le résultat est faux.
     */
	@Override
	protected void codeGenBool(DecacCompiler compiler, Label label, Label labelFin, boolean b) {
		compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	
    	codeGenAssemblyInst(compiler, regDroite, regGauche);
        codeGenOpCmp(compiler, labelFin, !b);
        compiler.getRegManager().freeRegistre(regDroite.getNumber(), compiler);
        compiler.getRegManager().freeRegistre(regGauche.getNumber(), compiler);
	}
	
	@Override
	protected void codeGenAssemblyInst(DecacCompiler compiler, DVal op1, GPRegister op2) {
		compiler.addInstruction(new CMP(op1, op2));
	}
	
	/**
	 * Génère le code assembleur en fonction de l'opérande pour les comparaisons
	 * @param compiler
	 * @param label
	 * @param b
	 */
	protected abstract void codeGenOpCmp(DecacCompiler compiler, Label label, boolean b);
    
}
