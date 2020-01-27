package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.DecacInternalError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WINT;

/**
 * Expression, i.e. anything that has a value.
 *
 * @author gl48
 * @date 01/01/2020
 */
public abstract class AbstractExpr extends AbstractInst {
    /**
     * @return true if the expression does not correspond to any concrete token
     * in the source code (and should be decompiled to the empty string).
     */
    boolean isImplicit() {
        return false;
    }

    /**
     * Get the type decoration associated to this expression (i.e. the type computed by contextual verification).
     */
    public Type getType() {
        return type;
    }

    protected void setType(Type type) {
        Validate.notNull(type);
        this.type = type;
    }
    private Type type;

    @Override
    protected void checkDecoration() {
        if (getType() == null) {
            throw new DecacInternalError("Expression " + decompile() + " has no Type decoration");
        }
    }

    /**
     * Verify the expression for contextual error.
     * 
     * implements non-terminals "expr" and "lvalue" 
     *    of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  (contains the "env_types" attribute)
     * @param localEnv
     *            Environment in which the expression should be checked
     *            (corresponds to the "env_exp" attribute)
     * @param currentClass
     *            Definition of the class containing the expression
     *            (corresponds to the "class" attribute)
     *             is null in the main bloc.
     * @return the Type of the expression
     *            (corresponds to the "type" attribute)
     */
    public abstract Type verifyExpr(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError;

    /**
     * Verify the expression in right hand-side of (implicit) assignments 
     * 
     * implements non-terminal "rvalue" of [SyntaxeContextuelle] in pass 3
     *
     * @param compiler  contains the "env_types" attribute
     * @param localEnv corresponds to the "env_exp" attribute
     * @param currentClass corresponds to the "class" attribute
     * @param expectedType corresponds to the "type1" attribute            
     * @return this with an additional ConvFloat if needed...
     */
    public AbstractExpr verifyRValue(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass, 
            Type expectedType)
            throws ContextualError {
    	type = this.verifyExpr(compiler, localEnv, currentClass);
    	
    	if(type.isInt() && expectedType.isFloat()) {//si on stocke un int dans un nombre flottant
    		ConvFloat c = new ConvFloat(this);
    		//c.verifyExpr(compiler, localEnv, currentClass);
    		c.setType(type);
    		return c;
    	} else if (type.sameType(expectedType)) {//sinon si les types sont bien identiques
    		this.setType(type);
    		return this;
    	} else if(type.isClass() && expectedType.isClass()) {//si les types sont des classes
    		if(type.asClassType("[SyntaxeContextuelle]" + type + " isn't a class", this.getLocation())
    				.isSubClassOf(expectedType.asClassType("[SyntaxeContextuelle]" + expectedType + 
    						" isn't a class", this.getLocation()))) {//si la rvalue est un type qui herite de <type>
    			this.setType(type);
    			return this;
    		}
    	}else if (type.isNull() && expectedType.isClass()) {
    		//si on initialise notre objet a null (on peut peut etre compacter avec type.isClassOrNull()...)
    		this.setType(type);
    		return this;
    	}
    	throw new ContextualError("[SyntaxeContextuelle] Affectation error, "
    			+ "type: " + type.toString() + ", expected type: " + expectedType.toString(), getLocation());
    	
    	
    }
    
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
        verifyExpr(compiler, localEnv, currentClass);
    }

    /**
     * Verify the expression as a condition, i.e. check that the type is
     * boolean.
     *
     * @param localEnv
     *            Environment in which the condition should be checked.
     * @param currentClass
     *            Definition of the class containing the expression, or null in
     *            the main program.
     */
    void verifyCondition(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass) throws ContextualError {
        Type t = this.verifyExpr(compiler, localEnv, currentClass);
        if (!t.isBoolean()) {
        	throw new ContextualError("[SyntaxeContextuelle] The condition must be a boolean", this.getLocation());
        }
    }

    /**
     * Generate code to print the expression
     *
     * @param compiler
     */

    protected void codeGenPrint(DecacCompiler compiler) {
		GPRegister reg = codeGenReg(compiler);
		compiler.addInstruction(new LOAD(reg, GPRegister.R1));
		compiler.addInstruction(new WINT());
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        //throw new UnsupportedOperationException("not yet implemented");
    }
    

    @Override
    protected void decompileInst(IndentPrintStream s) {
        decompile(s);
        s.print(";");
    }

    @Override
    protected void prettyPrintType(PrintStream s, String prefix) {
        Type t = getType();
        if (t != null) {
            s.print(prefix);
            s.print("type: ");
            s.print(t);
            s.println();
        }
    }
    
    /**
     * Génère le code assembleur quand on utilise l'abstractexpr dans le cas d'une expression
     * et renvoie le registre associé
     * @param compiler
     * @return
     */
    protected abstract GPRegister codeGenReg(DecacCompiler compiler);

   	protected void codeGenBool(DecacCompiler compiler, Label label, boolean b) {
   		//return null;
   	}
}
