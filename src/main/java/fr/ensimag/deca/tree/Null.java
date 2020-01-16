package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

public class Null extends AbstractExpr{

	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		// TODO Auto-generated method stub
			throw new UnsupportedOperationException("not yet implemented");
	}

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("null");
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		// leaf node
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// leaf node
		
	}
	
	@Override
	String prettyPrintNode() {
		return "NullLiteral (null)";
	}

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

}
