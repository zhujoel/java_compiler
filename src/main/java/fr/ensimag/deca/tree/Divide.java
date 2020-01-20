package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.context.Type;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.DIV;
import fr.ensimag.ima.pseudocode.instructions.QUO;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Divide extends AbstractOpArith {
    public Divide(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }


    @Override
    protected String getOperatorName() {
        return "/";
    }

    /**
     * Génère le code dans le cas ou l'on a pas besoin de stocker l'opération
     */
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	compiler.addComment(this.getOperatorName());
    	GPRegister regGauche = this.getLeftOperand().codeGenReg(compiler);
    	GPRegister regDroite = this.getRightOperand().codeGenReg(compiler);
    	Type typeLeft = this.getLeftOperand().getType();
    	Type typeRight = this.getRightOperand().getType();
    	
    	// Cas des entiers
    	if(typeLeft.isInt() && typeRight.isInt()) {
    		compiler.addInstruction(new QUO(regGauche, regDroite));
    	}
    	
    	// Cas des flottants
    	if(typeLeft.isFloat() && typeRight.isFloat()) {
    		compiler.addInstruction(new DIV(regGauche, regDroite));	
    	}
    	compiler.getRegManager().freeRegistre(regGauche.getNumber());
    	//Si la option -n (noCheck) n'est pas activée
    	if (!compiler.getCompilerOptions().getNoCheck()) {
            //on fait un test en cas de debordement
            compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
        }
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
    	Type typeLeft = this.getLeftOperand().getType();
    	Type typeRight = this.getRightOperand().getType();
    	
    	// Cas des entiers
    	if(typeLeft.isInt() && typeRight.isInt()) {
    		compiler.addInstruction(new QUO(regGauche, regDroite));
    	}
    	
    	// Cas des flottants
    	if(typeLeft.isFloat() && typeRight.isFloat()) {
    		compiler.addInstruction(new DIV(regGauche, regDroite));	
    	}
    	compiler.getRegManager().freeRegistre(regGauche.getNumber());
        //Si la option -n (noCheck) n'est pas activée
    	if (!compiler.getCompilerOptions().getNoCheck()) {
            //on fait un test en cas de debordement
            compiler.addInstruction(new BOV(ErrorManager.tabLabel[3]));
        }
    	return regDroite;
    	
	}

}
