package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.TypeDefinition;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.BEQ;
import fr.ensimag.ima.pseudocode.instructions.BNE;
import fr.ensimag.ima.pseudocode.instructions.CMP;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WFLOAT;
import fr.ensimag.ima.pseudocode.instructions.WINT;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

/**
 * Deca Identifier
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Identifier extends AbstractIdentifier {

	
    @Override
    protected void checkDecoration() {
        if (getDefinition() == null) {
            throw new DecacInternalError("Identifier " + this.getName() + " has no attached Definition");
        }
    }

    @Override
    public Definition getDefinition() {
        return definition;
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * ClassDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a class definition.
     */
    @Override
    public ClassDefinition getClassDefinition() {
        try {
            return (ClassDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a class identifier, you can't call getClassDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * MethodDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a method definition.
     */
    @Override
    public MethodDefinition getMethodDefinition() {
        try {
            return (MethodDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a method identifier, you can't call getMethodDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * FieldDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public FieldDefinition getFieldDefinition() {
        try {
            return (FieldDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a field identifier, you can't call getFieldDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a
     * VariableDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public VariableDefinition getVariableDefinition() {
        try {
            return (VariableDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a variable identifier, you can't call getVariableDefinition on it");
        }
    }

    /**
     * Like {@link #getDefinition()}, but works only if the definition is a ExpDefinition.
     * 
     * This method essentially performs a cast, but throws an explicit exception
     * when the cast fails.
     * 
     * @throws DecacInternalError
     *             if the definition is not a field definition.
     */
    @Override
    public ExpDefinition getExpDefinition() {
        try {
            return (ExpDefinition) definition;
        } catch (ClassCastException e) {
            throw new DecacInternalError(
                    "Identifier "
                            + getName()
                            + " is not a Exp identifier, you can't call getExpDefinition on it");
        }
    }

    @Override
    public void setDefinition(Definition definition) {
        this.definition = definition;
    }

    @Override
    public Symbol getName() {
        return name;
    }

    private Symbol name;

    public Identifier(Symbol name) {
        Validate.notNull(name);
        this.name = name;
    }
    
    @Override
    public Type verifyExpr(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
    	
    	
    	//POUR UNE VARIABLE
    	//TODO a refacto !!! c est pas top la
    	Symbol s = this.getName();
    	if(!localEnv.isIn(s)) {
    		throw new ContextualError("Utilisation d'une variable non déclarée", this.getLocation());
    	}
    	Definition d = localEnv.get(s);
    	
    	//TODO tester si le type existe !
    	this.setType(d.getType());
    	this.setDefinition(d);
    	return this.getType();
    	
    	//faire pour un field et une class aussi :) :D :X
        
    }

    /**
     * Implements non-terminal "type" of [SyntaxeContextuelle] in the 3 passes
     * @param compiler contains "env_types" attribute
     */
    @Override
    public Type verifyType(DecacCompiler compiler) throws ContextualError {
        setType(compiler.getType(name.getName()));
        this.setDefinition(new TypeDefinition(this.getType(), Location.BUILTIN));
        return this.getType();
    }
    
    
    private Definition definition;


    @Override
    protected void iterChildren(TreeFunction f) {
        // leaf node => nothing to do
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        // leaf node => nothing to do
    }

    @Override
    public void decompile(IndentPrintStream s) {
        s.print(name.toString());
    }

    @Override
    String prettyPrintNode() {
        return "Identifier (" + getName() + ")";
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Definition d = getDefinition();
        if (d != null) {
            s.print(prefix);
            s.print("definition: ");
            s.print(d);
            s.println();
        }
    }

	@Override
	protected GPRegister codeGenReg(DecacCompiler compiler) {
    	GPRegister reg = compiler.getRegManager().getRegistreLibre();
    	// on récupère la définition du symbol correspondant à l'identifier dans la stack
    	ExpDefinition expDef = compiler.getEnvironmentExp().get(this.getName());
    	compiler.addInstruction(new LOAD(expDef.getOperand(), reg));
    	return reg;	
	}
	
	@Override
	protected void codeGenInst(DecacCompiler compiler) {
    	GPRegister reg = compiler.getRegManager().getRegistreLibre();
    	// on récupère la définition du symbol correspondant à l'identifier dans la stack
    	ExpDefinition expDef = compiler.getEnvironmentExp().get(this.getName());
    	compiler.addInstruction(new LOAD(expDef.getOperand(), reg));
	}
	
	
	protected void codeGenBool(DecacCompiler compiler,Label label, boolean b) {
		GPRegister reg = this.codeGenReg(compiler);
        compiler.addInstruction(new CMP(new ImmediateInteger(1), reg));
        if(b) {
            compiler.addInstruction(new BEQ(label));
        }
        else {
            compiler.addInstruction(new BNE(label));
        }
	}
	
	@Override
    protected void codeGenPrint(DecacCompiler compiler) {
		// On doit sauvegarder la valeur dans le registre R1 pour afficher
		GPRegister reg = Register.R1;
		ExpDefinition expDef = compiler.getEnvironmentExp().get(this.getName());
		compiler.addInstruction(new LOAD(expDef.getOperand(), reg));
		
		Type type = expDef.getType();
		
		if(type.isFloat()) {
			compiler.addInstruction(new WFLOAT());
		}
		else if(type.isBoolean() || type.isInt()) {
			compiler.addInstruction(new WINT());
		}
		else if(type.isNull()) {
			compiler.addInstruction(new WSTR(new ImmediateString("null")));
		}
    }
}
