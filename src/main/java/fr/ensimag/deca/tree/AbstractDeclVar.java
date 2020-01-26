package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.ima.pseudocode.IMAProgram;

/**
 * Variable declaration
 *
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractDeclVar extends Tree {
    
    /**
     * Implements non-terminal "decl_var" of [SyntaxeContextuelle] in pass 3
     * @param compiler contains "env_types" attribute
     * @param localEnv 
     *   its "parentEnvironment" corresponds to the "env_exp_sup" attribute
     *   in precondition, its "current" dictionary corresponds to 
     *      the "env_exp" attribute
     *   in postcondition, its "current" dictionary corresponds to 
     *      the synthetized attribute
     * @param currentClass 
     *          corresponds to the "class" attribute (null in the main bloc).
     */    
    protected abstract void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;
    /**
     * Génère le code assembleur lors de la déclaration de variable.
     * @param compiler
     */
    protected abstract void codeGenDeclVar(DecacCompiler compiler);
    
    /**
     * Génère le code assembleur lors de la déclaration d'une variable locale
     * @param compiler
     * @param cptLB Compteur de la base locale
     */
    protected abstract void codeGenDeclVarLocale(DecacCompiler compiler, int cptLB, EnvironmentExp localEnv);
}
