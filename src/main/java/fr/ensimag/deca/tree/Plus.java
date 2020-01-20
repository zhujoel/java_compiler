package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.ADD;
import fr.ensimag.ima.pseudocode.instructions.BOV;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Plus extends AbstractOpArith {
    public Plus(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }
 

    @Override
    protected String getOperatorName() {
        return "+";	 
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
        compiler.addInstruction(new ADD(regGauche, regDroite));
    	//Si la option -n (noCheck) n'est pas activée
    	if (!compiler.getCompilerOptions().getNoCheck()) {
            //on fait un test en cas de debordement
            compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
        }
    	
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
    	compiler.addInstruction(new ADD(regGauche, regDroite));
    	//Si la option -n (noCheck) n'est pas activée
    	if (!compiler.getCompilerOptions().getNoCheck()) {
            //on fait un test en cas de debordement
            compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
        }
    	
        compiler.getRegManager().freeRegistre(regGauche.getNumber());
        return regDroite;
	}
	
	@Override
	protected void codeGenFact(DecacCompiler compiler, DVal regGauche, GPRegister regDroite) {
		compiler.addInstruction(new ADD(regGauche, regDroite));
	}
}
