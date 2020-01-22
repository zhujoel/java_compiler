package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

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

}
