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

	public abstract void verifyMethodBody(DecacCompiler compiler, EnvironmentExp envParam, ClassDefinition currentClass,
			Type t) throws ContextualError;
	
	/**
	 * Génère les instructions de la méthode
	 * @param compiler
	 */
	public abstract void codeGenMethodBody(DecacCompiler compiler, AbstractIdentifier className);
}
