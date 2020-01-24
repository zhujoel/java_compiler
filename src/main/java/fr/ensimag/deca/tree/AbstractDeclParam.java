package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.IMAProgram;

/**
 * Déclaration d'un paramètre d'une méthode.
 * @author zhujo
 *
 */
public abstract class AbstractDeclParam extends Tree{
	@Override
	public abstract void decompile(IndentPrintStream s);

	@Override
	protected abstract void prettyPrintChildren(PrintStream s, String prefix);

	@Override
	protected abstract void iterChildren(TreeFunction f);
	
	public abstract Type verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition CurrentClass) throws ContextualError;

	
	public abstract Type getType();
	
	public abstract Symbol getName();
	
	public abstract ExpDefinition getExpDefinition();
	
	/**
	 * Génère un push pour chaque paramètre
	 * @param compiler
	 * @param offset numéro du registre à push
	 */
	public abstract void codeGenDeclParamIn(IMAProgram ima, int offset);
	public abstract void codeGenDeclParamOut(IMAProgram ima, int offset);

}
