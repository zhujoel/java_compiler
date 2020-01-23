package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * Integer literal
 *
 * @author gl48
 * @date 01/01/2020
 */
public class IntLiteral extends AbstractExpr {
    public int getValue() {
        return value;
    }

    private int value;

    public IntLiteral(int value) {
        this.value = value;
    }

    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	setType(compiler.getType("int"));
    	return this.getType();
    }


    @Override
    String prettyPrintNode() {
        return "Int (" + getValue() + ")";
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(Integer.toString(value));
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
    protected GPRegister codeGenReg(DecacCompiler compiler) {
        //compiler.addInstruction(new ImmediateInteger(this.getValue()));
    	GPRegister reg = compiler.getRegManager().getRegistreLibre(compiler);
    	compiler.addInstruction(new LOAD(new ImmediateInteger(value), reg));
    	return reg;
    }
    
    @Override
    protected void codeGenInst(DecacCompiler compiler) {
    	GPRegister reg = compiler.getRegManager().getRegistreLibre(compiler);
    	compiler.addInstruction(new LOAD(new ImmediateInteger(value), reg));
    }
    
    @Override
    protected void codeGenPrint(DecacCompiler compiler) {
        compiler.addInstruction(new WSTR(new ImmediateString(Integer.toString(value))));
    }
}
