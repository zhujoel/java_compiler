package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;



/**
 * Déclaration d'une méthode.
 * @author zhujo
 *
 */
public abstract class AbstractDeclMethod extends Tree {

	@Override
	public abstract void decompile(IndentPrintStream s);

	@Override
	protected abstract void prettyPrintChildren(PrintStream s, String prefix);

	@Override
	protected abstract void iterChildren(TreeFunction f);
	
	public abstract void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;

	public abstract void verifyMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;
	
	/**
	 * Génère le code du label pour le corps de la méthode
	 * @param compiler
	 * @param className
	 */
	protected abstract void codeGenDeclMethod(DecacCompiler compiler, AbstractIdentifier className);
	


}
