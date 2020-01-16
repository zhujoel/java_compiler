package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class BooleanLiteral extends AbstractExpr {

    private boolean value;

    public BooleanLiteral(boolean value) {
        this.value = value;
    }

    public boolean getValue() {
        return value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	setType(compiler.getType("bool"));
    	return this.getType();
    }


    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Boolean.toString(value));
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
    String prettyPrintNode() {
        return "BooleanLiteral (" + value + ")";
    }

    @Override
    protected GPRegister codeGenReg(DecacCompiler compiler) {
        //compiler.addInstruction(new ImmediateInteger(this.getValue()));
    	GPRegister reg = compiler.getRegManager().getRegistreLibre();
    	// Le booléen a pour valeur 1
    	if(value) {
    		compiler.addInstruction(new LOAD(new ImmediateInteger(1), reg));
    		return reg;
    	}	
    	// Le booléen a pour valeur 0
    	compiler.addInstruction(new LOAD(new ImmediateInteger(0), reg));
		return reg;
    	
    }
    
    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
    	if(value) {
            compiler.addInstruction(new WSTR(new ImmediateString("true")));
    	}
    	else {
            compiler.addInstruction(new WSTR(new ImmediateString("false")));
    	}
    }
}
