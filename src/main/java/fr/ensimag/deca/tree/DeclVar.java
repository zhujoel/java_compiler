package fr.ensimag.deca.tree;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.RegManager;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.ExpDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

import java.io.PrintStream;
import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class DeclVar extends AbstractDeclVar {
	private static final Logger LOG = Logger.getLogger(DeclVar.class);

    
    final private AbstractIdentifier type;
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;

    public DeclVar(AbstractIdentifier type, AbstractIdentifier varName, AbstractInitialization initialization) {
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }

    @Override
    protected void verifyDeclVar(DecacCompiler compiler,
            EnvironmentExp localEnv, ClassDefinition currentClass)
            throws ContextualError {
    	LOG.debug("verify Type : start");
    	Type t = type.verifyType(compiler);
    	LOG.debug("verify Type : end");
    	//condition type != void
    	if (t.isVoid()) {
    		throw new ContextualError("Variable de type void : ", getLocation());
    	}
    	LOG.debug("verify Initialisation : start");
    	initialization.verifyInitialization(compiler, t, localEnv, currentClass);
    	LOG.debug("verify Initialisation : end");
    	
    	//generation de la definition pour la decoration de l'arbre
    	ExpDefinition d = new VariableDefinition(t, varName.getLocation());
    	try {
    		//declaration de la variable dans l'environement des expressions
			localEnv.declare(this.varName.getName(), d);
			//decoration de l'arbre :
			varName.setDefinition(d);
			varName.setType(t);
			
		} catch (DoubleDefException e) {
			throw new ContextualError("Declaration d'une variable deja déclarée précédement", getLocation());
		}
    	
    }

    
    @Override
    public void decompile(IndentPrintStream s) {
    	this.type.decompile(s);
    	s.print(" ");
    	this.varName.decompile(s);
    	this.initialization.decompile(s);
    	s.print(";");
    }

    @Override
    protected
    void iterChildren(TreeFunction f) {
        type.iter(f);
        varName.iter(f);
        initialization.iter(f);
    }
    
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
    }

	@Override
	protected void codeGenDeclVar(DecacCompiler compiler) {
		int sLocation = initialization.codeGenInit(compiler);
        System.out.println("Slocation dans declVar : "+sLocation);
		varName.setStackLocation(sLocation);
	}
}
