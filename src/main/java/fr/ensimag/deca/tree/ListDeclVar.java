package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;

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
    	// On met le pointeur au bon endroit
    	compiler.addComment("Declaration des variables");
        for (AbstractDeclVar i : getList()) {
            i.codeGenDeclVar(compiler);
        }
    }
    
    /**
     * Déclaration de variables dans une méthode
     */
    public void codeGenListDeclVarLocale(DecacCompiler compiler) {
    	compiler.addComment("Declaration des variables locales");
    	int cptLB = 1;
    	for(AbstractDeclVar i : getList()) {
    		i.codeGenDeclVarLocale(compiler, cptLB++);
    	}
    }

}
