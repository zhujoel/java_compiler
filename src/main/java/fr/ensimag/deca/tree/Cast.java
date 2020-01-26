package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Définition d'un cast de variable.
 * @author zhujo
 *
 */
public class Cast extends AbstractExpr {
	
	// type en quoi on cast 
	private AbstractIdentifier type;
	// la variable qu'on veut caster
	private AbstractExpr var;
	
	public Cast(AbstractIdentifier type, AbstractExpr var) {
		Validate.notNull(type);
		Validate.notNull(var);
		this.type = type;
		this.var = var;
	}

	//TODO : ne fonctionne pas
	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type type1 = type.verifyType(compiler);
		Type type2 = var.verifyExpr(compiler, localEnv, currentClass);
		//que se passe-t-il si on cast un null / en null ??
		if(type1.isClass() && type2.isClass()) {
			if(!type1.asClassType(type1.toString() + " n'est pas une classe", 
					this.getLocation()).isSubClassOf(type2.asClassType(type2.toString() 
							+ " n'est pas une classe", this.getLocation()))) {
				throw new ContextualError("La classe demandée pour le cast n'est pas une classe parente de l'identifier", this.getLocation());
			}
		}
		this.setType(type1);
		return type1;
	}

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("(");
		type.decompile(s);
		s.print(") ");
		var.decompile(s);
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.type.prettyPrint(s, prefix, false);
		this.var.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
