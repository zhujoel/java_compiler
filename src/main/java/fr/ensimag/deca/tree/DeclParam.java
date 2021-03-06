package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.ParamDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

/**
 * Déclaration d'un paramètre de méthode.
 * @author zhujo
 *
 */
public class DeclParam extends AbstractDeclParam{

	// type du parametre
	final private AbstractIdentifier type;
	// nom du parametre
	final private AbstractIdentifier parametre;

	public DeclParam(AbstractIdentifier type, AbstractIdentifier parametre) {
		Validate.notNull(type);
		Validate.notNull(parametre);
		this.type = type;
		this.parametre = parametre;
	}

	public Type getType() {
		return this.type.getType();
	}

	public Symbol getName() {
		return this.parametre.getName();
	}

	public ExpDefinition getExpDefinition() {
		return (ExpDefinition) this.parametre.getDefinition();
	}

	@Override
	public void decompile(IndentPrintStream s) {
		this.type.decompile(s);
		s.print(" ");
		this.parametre.decompile(s);

	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.type.prettyPrint(s, prefix, false);
		this.parametre.prettyPrint(s, prefix, true);

	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub

	}

	//TODO surement a finir !!
	@Override
	public Type verifyDeclParam(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass)
			throws ContextualError {
		Type t = this.type.verifyType(compiler);
		if(t.isVoid()) {
			throw new ContextualError("[SyntaxeContextuelle] Void Parameter", this.parametre.getLocation());
		}
		this.parametre.setType(t);
		ExpDefinition pDef = new VariableDefinition(this.parametre.getType(), this.parametre.getLocation());
		this.parametre.setDefinition(pDef);
		return t;
	}

	@Override
	public void codeGenDeclParamIn(DecacCompiler compiler, int offset, EnvironmentExp localEnv) {
		// on ajoute une variable dans notre environnement et on indique son emplacement dans le local stack
		ParamDefinition paramDef = new ParamDefinition(this.type.getType(), parametre.getLocation());
		paramDef.setOperand(new RegisterOffset(offset, Register.LB));
		try {
			localEnv.declareOrSet(parametre.getName(), paramDef);
		}
		catch(DoubleDefException e) {
			e.printStackTrace();
		}
		compiler.addInstruction(new PUSH(GPRegister.getR(offset)));

	}

	@Override
	public void codeGenDeclParamOut(DecacCompiler compiler, int offset) {
		compiler.addInstruction(new POP(GPRegister.getR(offset)));
	}

}
