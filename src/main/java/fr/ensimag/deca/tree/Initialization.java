package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Initialization extends AbstractInitialization {
	private static final Logger LOG = Logger.getLogger(Initialization.class);

    public AbstractExpr getExpression() {
        return expression;
    }

    private AbstractExpr expression;

    public void setExpression(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    public Initialization(AbstractExpr expression) {
        Validate.notNull(expression);
        this.expression = expression;
    }

    @Override
    protected void verifyInitialization(DecacCompiler compiler, Type t,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	LOG.debug("verify rvalue : start");
    	//on modifie le contenu de expression au cas où on veut un convfloat au lieu d'un float
    	expression = expression.verifyRValue(compiler, localEnv, currentClass, t);
    	LOG.debug("verify rvalue : end");
    }


    @Override
    public void decompile(IndentPrintStream s) {
    	s.print(" = ");
    	this.getExpression().decompile(s);
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        expression.iter(f);
    }

    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        expression.prettyPrint(s, prefix, true);
    }
    
    @Override
    protected GPRegister codeGenInit(DecacCompiler compiler, Type type) {
    	GPRegister reg = expression.codeGenReg(compiler);
    	return reg;
    }

}
