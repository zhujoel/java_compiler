package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.BRA;

/**
 * Full if/else if/else statement.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class IfThenElse extends AbstractInst {
    
    private final AbstractExpr condition; 
    private final ListInst thenBranch;
    private ListInst elseBranch;

    public IfThenElse(AbstractExpr condition, ListInst thenBranch, ListInst elseBranch) {
        Validate.notNull(condition);
        Validate.notNull(thenBranch);
        Validate.notNull(elseBranch);
        this.condition = condition;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }
    
    @Override
    protected void verifyInst(DecacCompiler compiler, EnvironmentExp localEnv,
            ClassDefinition currentClass, Type returnType)
            throws ContextualError {
    	this.condition.verifyCondition(compiler, localEnv, currentClass);
    	this.thenBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    	this.elseBranch.verifyListInst(compiler, localEnv, currentClass, returnType);
    }


    @Override
    public void decompile(IndentPrintStream s) {
    	s.print("if(");
    	this.condition.decompile(s);
    	s.println("){");
    	s.indent();
    	this.thenBranch.decompile(s);
    	s.unindent();
    	s.println("} else {");
    	s.indent();
    	this.elseBranch.decompile(s);
    	s.unindent();
    	s.print("}");
    	
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        condition.iter(f);
        thenBranch.iter(f);
        elseBranch.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        condition.prettyPrint(s, prefix, false);
        thenBranch.prettyPrint(s, prefix, false);
        elseBranch.prettyPrint(s, prefix, true);
    }
    

    @Override
    protected void codeGenInst(DecacCompiler compiler) {

    	compiler.addComment("Début du if_"+ compiler.getRegManager().getNIf());
    	Label labElse = new Label("else_"+ compiler.getRegManager().getNIf());
    	Label labEndif = new Label("endif_"+ compiler.getRegManager().getNIf());
    	
    	condition.codeGenBool(compiler, labElse, false);
    	compiler.addComment("Contenu du if_"+ compiler.getRegManager().getNIf());
    	thenBranch.codeGenListInst(compiler);
    	compiler.addInstruction(new BRA(labEndif));
    	compiler.addLabel(labElse);
    	elseBranch.codeGenListInst(compiler);
    	compiler.addLabel(labEndif);
    	
    	compiler.getRegManager().addNIf();
    }
}
