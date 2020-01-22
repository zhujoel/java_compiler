package fr.ensimag.deca.tree;

import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Liste des classes d'un programme.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class ListDeclClass extends TreeList<AbstractDeclClass> {
    private static final Logger LOG = Logger.getLogger(ListDeclClass.class);
    
    @Override
    public void decompile(IndentPrintStream s) {
        for (AbstractDeclClass c : getList()) {
            c.decompile(s);
            s.println();
        }
    }

    /**
     * Pass 1 of [SyntaxeContextuelle]
     */
    void verifyListClass(DecacCompiler compiler) throws ContextualError {
        LOG.debug("verify listClass: start");
        for (AbstractDeclClass c : getList()) {
        	c.verifyClass(compiler);
        }
        LOG.debug("verify listClass: end");
    }

    /**
     * Pass 2 of [SyntaxeContextuelle]
     */
    public void verifyListClassMembers(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    /**
     * Pass 3 of [SyntaxeContextuelle]
     */
    public void verifyListClassBody(DecacCompiler compiler) throws ContextualError {
        throw new UnsupportedOperationException("not yet implemented");
    }
    
    public void initListDeclClass(DecacCompiler compiler) {
    	ListDeclParam paramObject = new ListDeclParam();
    	// Nom du paramètre
    	compiler.getSymbolTable().create("Obj1");
    	
    	// Création des paramètres de la fonction equals
    	paramObject.add(new DeclParam(new Identifier(compiler.getSymbolTable().getSymbol("Object")), new Identifier(compiler.getSymbolTable().getSymbol("Obj1"))));
    	
    	// Création de la déclaration de la méthode equals
    	DeclMethod declEquals = new DeclMethod(compiler.getType("bool"), compiler.getSymbolTable().getSymbol("equals"), paramObject, new MethodBody(new ListDeclVar(), new ListInst()));
    	ListDeclMethod listMethodObject = new ListDeclMethod();
    	listMethodObject.add(declEquals);
    	
    	DeclClass ObjectDeclClass = new DeclClass(new Identifier(compiler.getSymbolTable().getSymbol("Object")), null, new ListDeclField(), listMethodObject);
    	this.add(ObjectDeclClass);
    
    }
    
    public void codeGenListClass(DecacCompiler compiler) {
    	// On prend met le pointeur au bon endroit
    	initListDeclClass(compiler);
        //compiler.addComment("On stocke la valeur \"null\" à la première case de la pile.");
        compiler.addInstruction(new LOAD(new NullOperand(), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
        compiler.getStackManager().addStackCpt();
        
        Label objEq0 = new Label("code.Object.equals");
        compiler.addInstruction(new LOAD(new LabelOperand(objEq0), Register.getR(0)));
        compiler.addInstruction(new STORE(Register.getR(0), new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
        compiler.getStackManager().addStackCpt();
        
        compiler.getEnvironmentClass().put(compiler.getType("Object").getName(), new RegisterOffset(1, Register.GB));

        
        for (AbstractDeclClass i : getList()) {
            i.codeGenDeclClass(compiler);
        }
        
    }
}
