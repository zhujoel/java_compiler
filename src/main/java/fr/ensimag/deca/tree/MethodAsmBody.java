package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Corps d'une méthode ASM (assembleur).
 * @author zhujo
 *
 */
public class MethodAsmBody extends AbstractMethodBody{
	
	// morceau d'assembleur
	private StringLiteral body;

	public MethodAsmBody(StringLiteral body) {
		Validate.notNull(body);
		this.body = body;
	}
	
	@Override
	public void decompile(IndentPrintStream s) {
		s.print(" asm (");
		body.decompile(s);
		s.print(");");
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		body.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp envParam, ClassDefinition currentClass, Type t)
			throws ContextualError {
		// TODO Auto-generated method stub
		
	}

}
