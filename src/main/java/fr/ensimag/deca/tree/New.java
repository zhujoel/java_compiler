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
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

/**
 * Déclaration d'une classe.
 * attribut ident est le nom de la classe
 * @author zhujo
 *
 */
public class New extends AbstractExpr{
	
	private AbstractIdentifier ident;

	public New(AbstractIdentifier ident) {
		Validate.notNull(ident);
		this.ident = ident;
	}
	
	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type t = this.ident.verifyType(compiler);
		if(!t.isClass()) {
			throw new ContextualError("Trying to instanciate with a type which is not a class", this.ident.getLocation());
		}
		this.setType(t);
		return t;
	}

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		GPRegister reg = compiler.getRegManager().getRegistreLibre(compiler);
		Label init = new Label("init." + this.ident.getName().getName());
		compiler.addInstruction(new BRA(init));
		return reg;
	}

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("new ");
		this.ident.decompile(s);
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.ident.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
