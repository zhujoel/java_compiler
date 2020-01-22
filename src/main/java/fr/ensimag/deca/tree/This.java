package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Déclaration de this.
 * Ex: this.attr;
 * Ex: this.meth();
 * @author zhujo
 *
 */
public class This extends AbstractExpr {
	
	// indique si la déclaration de this est explicit ou pas (e.g. si il n'y a pas d'ambiguité
	// alors on a pas forcément besoin de this pour désigner l'attr/methode de classe)
	private boolean estExplicit;
	
	public This(boolean estExplicit) {
		Validate.notNull(estExplicit);
		this.estExplicit = estExplicit;
	}

	//TODO : a modfier (pas fini)
	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		if(currentClass == null) {
			throw new ContextualError("Appel de l'identificateur this hors d'une classe", this.getLocation());
		}
		return null;
		
	}

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		if(this.estExplicit) {
			s.println(prefix+"true");
		}
		else {
			s.println(prefix+"false");
		}
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}


}
