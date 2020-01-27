package fr.ensimag.deca.tree;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

/**
 * Liste des classes d'un programme.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        for (AbstractDeclClass c : getList()) {
        	c.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
    	for(AbstractDeclClass c : this.getList()) {
        	c.verifyClassMembers(compiler);
        }
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
    	for(AbstractDeclClass c : this.getList()) {
        	c.verifyClassBody(compiler);
        }
    }
    
    public void codeGenListClass(DecacCompiler compiler) {
        
    	// on génère le code pour la classe Object à la main car celle-ci n'est pas déclaré
        compiler.addComment("Code de la table des méthodes de Object");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
        compiler.getStackManager().addStackCpt();
        
        Label objEq0 = new Label("code.Object.equals");
        compiler.addInstruction(new LOAD(new LabelOperand(objEq0), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
        compiler.getStackManager().addStackCpt();
		
        compiler.activateStoring();
        compiler.addIMABloc();
        compiler.addLabel(objEq0);
        compiler.deactivateStoring();
        
        compiler.getEnvironmentClass().put(compiler.getType("Object").getName(), new RegisterOffset(1, Register.GB));

        for (AbstractDeclClass i : getList()) {
            i.codeGenDeclClass(compiler);
        }
    }
    
    public int getNbDeclField() {
    	int i = 0;
    	for(AbstractDeclClass c : this.getList()) {
    		i += c.getFieldNb();
    	}
    	return i;
    }
}
