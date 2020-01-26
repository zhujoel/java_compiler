package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * Déclaration d'une selection (i.e, on prend l'attribut d'un objet).
 * Ex: obj.attr;
 * @author zhujo
 *
 */
public class Selection extends AbstractLValue {

	// l'expression à gauche de la selection (dans l'exemple 'obj')
	private AbstractExpr expr;
	// l'attribut à droite de la selection (dans l'exemple 'attr')
	private AbstractIdentifier ident;
	
	public Selection(AbstractExpr expr, AbstractIdentifier ident) {
		Validate.notNull(expr);
		Validate.notNull(ident);
		this.expr = expr;
		this.ident = ident;
	}
	
	@Override
	public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		
		Type t = expr.verifyExpr(compiler, localEnv, currentClass);
		
		//On controle que l'on fait la selection sur une classe
		ClassDefinition cDef = t.asClassType(t.getName() + " n'est pas une classe", this.expr.getLocation()).getDefinition();
		Type t2 = ident.verifyExpr(compiler, cDef.getMembers(), currentClass);
		FieldDefinition fDef = ident.getDefinition().asFieldDefinition(ident.getName() + " n'est pas un champ", ident.getLocation());
		if (fDef.getVisibility() == Visibility.PROTECTED && !cDef.hasForParent(currentClass)) {
			throw new ContextualError(ident.getName() + " est PROTECTED", expr.getLocation());
		}
		this.setType(t2);
		return this.getType();
	}

	@Override
	public void decompile(IndentPrintStream s) {
		this.expr.decompile(s);
		this.ident.decompile(s);
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.expr.prettyPrint(s, prefix, false);
		this.ident.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
		// TODO Auto-generated method stub
		return null;
	}

}
