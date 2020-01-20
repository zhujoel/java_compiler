package fr.ensimag.deca.tree;

import java.io.PrintStream;
import java.util.Iterator;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.HALT;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Deca complete program (class definition plus main block)
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Program extends AbstractProgram {
    private static final Logger LOG = Logger.getLogger(Program.class);
    
    public Program(ListDeclClass classes, AbstractMain main) {
        Validate.notNull(classes);
        Validate.notNull(main);
        this.classes = classes;
        this.main = main;
    }
    public ListDeclClass getClasses() {
        return classes;
    }
    public AbstractMain getMain() {
        return main;
    }
    private ListDeclClass classes;
    private AbstractMain main;

    @Override
    public void verifyProgram(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify program: start");
        
        //PASSE 1
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClass(compiler);
        }
        //PASSE 2
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClassMembers(compiler);
        }
        /*//PASSE 3
        for(AbstractDeclClass c : classes.getList()) {
        	c.verifyClassBody(compiler);
        }*/
        
        //on ne met pas liste_decl pour le hello world mais il faudra l'ajouter
        main.verifyMain(compiler);
        LOG.debug("verify program: end");
    }

    @Override
    public void codeGenProgram(DecacCompiler compiler) {
        // A FAIRE: compléter ce squelette très rudimentaire de code
        compiler.addComment("Main program");
        compiler.addComment("On stocke la valeur \"null\" à la première case de la pile.");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(1, Register.GB)));
        main.codeGenMain(compiler);
        compiler.addInstruction(new HALT());
        ErrorManager.addErrorLabels(compiler);
    }

    @Override
    public void decompile(IndentPrintStream s) {
        getClasses().decompile(s);
        getMain().decompile(s);
    }
    
    @Override
    protected void iterChildren(TreeFunction f) {
        classes.iter(f);
        main.iter(f);
    }
    @Override
    protected void prettyPrintChildren(PrintStream s, String prefix) {
        classes.prettyPrint(s, prefix, false);
        main.prettyPrint(s, prefix, true);
    }
}
