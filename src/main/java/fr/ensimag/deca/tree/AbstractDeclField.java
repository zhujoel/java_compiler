package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * TODO: COMPLETE THIS MOTHERFUCKERRRRRRRRRRRRRRR
 * @author zhujo
 *
 */
public abstract class AbstractDeclField extends Tree{

	@Override
	public abstract void decompile(IndentPrintStream s);

	@Override
	protected abstract void prettyPrintChildren(PrintStream s, String prefix);

	@Override
	protected abstract void iterChildren(TreeFunction f);

}
