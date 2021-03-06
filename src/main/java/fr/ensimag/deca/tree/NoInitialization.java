package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateFloat;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.LOAD;

/**
 * Absence of initialization (e.g. "int x;" as opposed to "int x =
 * 42;").
 *
 * @author gl48
 * @date 01/01/2020
 */
public class NoInitialization extends AbstractInitialization {

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
        //rien a faire
    }


    /**
     * Node contains no real information, nothing to check.
     */
    @Override
    protected void checkLocation() {
        // nothing
    }

    @Override
    public void decompile(IndentPrintStream s) {
    	// nothing
    }

    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    protected GPRegister codeGenInit(DecacCompiler compiler, Type type) {
    	// Pour l'instant on met n'importe quoi dans la case lors de la non-initialisation.
    	GPRegister reg = Register.getR(0);
    	if(type == compiler.getType("int") || type == compiler.getType("boolean")) {
        	compiler.addInstruction(new LOAD(new ImmediateInteger(0), Register.R0));
    	}
    	else if(type == compiler.getType("float")){
        	compiler.addInstruction(new LOAD(new ImmediateFloat(0), Register.R0));
    	}
    	else {
    		compiler.addInstruction(new LOAD(new NullOperand(), Register.R0));
    	}
    	return reg;
    }
}
