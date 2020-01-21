package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.MethodDefinition;
import fr.ensimag.deca.context.Signature;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Déclaration d'une méthode d'une classe.
 * Ex: int methName(int param1, int param2){ corps }
 * @author zhujo
 *
 */
public class DeclMethod extends AbstractDeclMethod {
	private static final Logger LOG = Logger.getLogger(DeclVar.class);
	
	// type du retour de la méthode
    final private AbstractIdentifier returnType;
    // nom de la méthode
    final private AbstractIdentifier methName;
    // paramètres de la méthode
    final private ListDeclParam params;
    // corps de la méthode
    final private MethodBody corps;
    
    public DeclMethod(AbstractIdentifier returnType, AbstractIdentifier methName,
    		ListDeclParam params, ListDeclVar decls, ListInst insts) {
    	Validate.notNull(returnType);
        Validate.notNull(methName);
        Validate.notNull(params);
        Validate.notNull(decls);
        Validate.notNull(insts);
        this.returnType = returnType;
        this.methName = methName;
        this.params = params;
        this.corps = new MethodBody(decls, insts);
    }
    
    
	@Override
	public void decompile(IndentPrintStream s) {
		this.returnType.decompile(s);
		s.print(" ");
		this.methName.decompile(s);
		this.params.decompile(s);
		this.corps.decompile(s);
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		this.returnType.prettyPrint(s, prefix, false);
		this.methName.prettyPrint(s, prefix, false);
		this.params.prettyPrint(s, prefix, false);
		this.corps.prettyPrint(s, prefix, true);
		
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}
	
	//TODO surement a finir
	@Override
	public void verifyDeclMethod(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
		Type t = this.returnType.verifyType(compiler);
		Signature s = new Signature();
		
		//incrementation du nombre de methode dans la classe courrante
		currentClass.incNumberOfMethods();
		
		//creation de la signature
		for (AbstractDeclParam p : params.getList()) {
			s.add(p.verifyDeclParam(compiler, localEnv, currentClass));
		}
		
		//TODO
		//si la methode est definie dans la superclasse et si la methode de la superclasse a une signature differente
		//System.out.println(currentClass.toString());
		//		if (currentClass.getSuperClass().getMembers().isIn(methName.getName()) 
//				&& !currentClass.getSuperClass().getMembers().get(methName.getName())
//				.asMethodDefinition(methName.getName().toString() + " n'est pas une methode", methName.getLocation())
//				.getSignature().equals(s)) {
//			throw new ContextualError("Redefinition de methode avec deux signatures differentes", methName.getLocation());
//			
//		}
		
		//definition de la methode
		MethodDefinition methDef = new MethodDefinition(t, methName.getLocation(), s, currentClass.getNumberOfMethods());
		
		//declaration de la methode dans l'environement local
		try {
			
			localEnv.declare(this.methName.getName(), methDef);
			methName.setDefinition(methDef);
			methName.setType(t);
			
		}catch(DoubleDefException e) {
			throw new ContextualError("Methode deja definie", this.methName.getLocation());
		}
	}
	
	@Override
	protected void codeGenDeclMethod(DecacCompiler compiler) {
		compiler.addInstruction(new LOAD(new LabelOperand(new Label("code." + this.methName.getName().getName())), Register.R0));
		compiler.addInstruction(new STORE(Register.R0, new RegisterOffset(compiler.getStackManager().getStackCpt(), Register.GB)));
		compiler.getStackManager().addStackCpt();
	}

}
