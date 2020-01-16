package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ADDSP;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

/**
 * List of declarations (e.g. int x; float y,z).
 * 
 * @author gl48
 * @date 01/01/2020
 */
public class ListDeclVar extends TreeList<AbstractDeclVar> {

    @Override
    public void decompile(IndentPrintStream s) {
    	for(AbstractDeclVar v : getList()) {
    		v.decompile(s);
    		s.println();
    	}
    }

    /**
     * Implements non-terminal "list_decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains the "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the "env_exp_r" attribute
     * @param currentClass 
     *          corresponds to "class" attribute (null in the main bloc).
     */    
    void verifyListDeclVariable(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	for (AbstractDeclVar v : getList()) {
    		v.verifyDeclVar(compiler, localEnv, currentClass);
    	}
    }

    public void codeGenListDeclVar(DecacCompiler compiler) {
    	// On prend met le pointeur au bon endroit
    	int nbDecl = this.size();
    	compiler.addComment("Test pour savoir si la pile est pleine");
    	compiler.addInstruction(new TSTO(new ImmediateInteger(nbDecl + 2)));
    	compiler.addInstruction(new BOV(new Label("pile_pleine")));
    	compiler.addInstruction(new ADDSP(new ImmediateInteger(nbDecl + 2)));
    	compiler.addComment("Declaration des variables");
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVar(compiler);
        }
        
    }

}
