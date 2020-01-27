package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;

/**
 * Corps d'une méthode.
 * @author zhujo
 *
 */
public abstract class AbstractMethodBody extends Tree{
	
	/**
	 * [SyntaxeContextuelle] Verification du corps d'une methode dans la passe 3
	 * @param compiler
	 * @param envParam
	 * @param currentClass
	 * @param t
	 * @throws ContextualError
	 */
	public abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp envParam, ClassDefinition currentClass,
			Type t) throws ContextualError;
	
	/**
	 * Génère les instructions de la méthode
	 * @param compiler
	 */
	public abstract void codeGenMethodBody(DecacCompiler compiler, AbstractIdentifier className, EnvironmentExp localEnv);
	
	public abstract int getNbVarLocal();
}
