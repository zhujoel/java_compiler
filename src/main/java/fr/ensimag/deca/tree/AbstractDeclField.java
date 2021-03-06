package fr.ensimag.deca.tree;

import java.io.PrintStream;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.RegisterOffset;

/**
 * Déclaration d'un attribut d'une classe.
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
	
	/**
	 * Initialisation des champs dans la passe 2
	 * @param compiler l'environnement des types
	 * @param localEnv l'environement local
	 * @param currentClass la classe dans laquelle on se trouve
	 * @throws ContextualError
	 */
	public abstract void verifyDeclField(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError;

	/**
	 * Instanciation des champs dans la passe 3
	 * @param compiler l'environement des types
	 * @param lovalEnv l'environement local
	 * @param currentClass la classe dans laquelle on se trouve
	 * @throws ContextualError
	 */
	public abstract void verifyField(DecacCompiler compiler, EnvironmentExp lovalEnv, ClassDefinition currentClass) throws ContextualError;

	public abstract void codeGenDeclField(DecacCompiler compiler, RegisterOffset registerOffset);
}
