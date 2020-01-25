package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.context.FieldDefinition;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.context.VariableDefinition;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Déclaration d'un attribut d'une classe.
 * Ex: public int attrName [ = 3];
 * @author zhujo
 *
 */
public class DeclField extends AbstractDeclField {
	private static final Logger LOG = Logger.getLogger(DeclVar.class);

	// la visibilité du field
    final private Visibility visibility;
    final private AbstractIdentifier type;
    final private AbstractIdentifier fieldName;
    final private AbstractInitialization initialization;
    
    public DeclField(Visibility visibility, AbstractIdentifier type,
    		AbstractIdentifier fieldName, AbstractInitialization initialization) {
    	Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(fieldName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.fieldName = fieldName;
        this.initialization = initialization;
    }
    
    public Visibility getVisibility() {
    	return visibility;
    }
    
    public AbstractIdentifier getFieldName() {
    	return fieldName;
    }
    
    
    
	@Override
	public void decompile(IndentPrintStream s) {
		s.print(this.visibility.getAffichage() + " ");
		this.type.decompile(s);
		s.print(" ");
		this.fieldName.decompile(s);
		this.initialization.decompile(s);
		s.print(";");
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		s.println(prefix + "visibility="+visibility);
        type.prettyPrint(s, prefix, false);
        fieldName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void verifyDeclField(DecacCompiler compiler, EnvironmentExp localEnv, ClassDefinition currentClass) throws ContextualError {
		Type t = this.type.verifyType(compiler);
		if(t.isVoid()) {
			throw new ContextualError("Variable de type void", type.getLocation());
		}
		
		//On incremente le nombre de champs
		currentClass.incNumberOfFields();
		//On cree sa definition
		FieldDefinition fDef = new FieldDefinition(t, fieldName.getLocation(), visibility, currentClass, currentClass.getNumberOfFields());
		
		//On controle si le champ est defini dans une superclasse
		ClassDefinition cSuperDef = currentClass.getFirstSuperClassWithDef(this.fieldName.getName());
		//si oui cSuperDef est non null
		if(cSuperDef != null) {
			//dans ce cas si la superDefinition n est pas un champ c est une erreur
			if(!cSuperDef.getMembers().get(fieldName.getName()).isField()) {
				throw new ContextualError(this.fieldName.getName().toString() + 
						" n'est pas un champ dans l'une des classes parente", this.fieldName.getLocation());
			}
		}
		
		try {
			//On declare le champ
			localEnv.declare(this.fieldName.getName(), fDef);
			fieldName.setDefinition(fDef);
			fieldName.setType(t);
		}catch(DoubleDefException e) {
			throw new ContextualError("Champ deja defini", this.fieldName.getLocation());
		}
	}
	
	public void verifyField(DecacCompiler compiler, EnvironmentExp localEnv
			, ClassDefinition currentClass) throws ContextualError{
		Type t = type.getType();
		initialization.verifyInitialization(compiler, t, localEnv, currentClass);
	}

	@Override
	public void codeGenDeclField(DecacCompiler compiler, RegisterOffset reg) {
		// on vérifie que le type existe dans notre environnement
		
		this.type.setType(compiler.getEnvironmentType().get(this.type.getName()));
		this.fieldName.setType(compiler.getEnvironmentType().get(this.type.getName()));
		// on ajoute une variable dans notre environnement et on indique son emplacement dans le stack
		VariableDefinition varDef = new VariableDefinition(this.type.getType(), fieldName.getLocation());
		varDef.setOperand(reg);
		try {
			compiler.getEnvironmentExp().declareOrSet(fieldName.getName(), varDef);
		}
		catch(DoubleDefException e) {
			e.printStackTrace();
		}
		// un registre de plus est occupé
		compiler.getStackManager().addStackCpt();
				
		// on génère le code assembleur de l'initialisation
		GPRegister reg1 = initialization.codeGenInit(compiler, this.type.getType());
		compiler.addInstruction(new STORE(reg1, varDef.getOperand()));
		// indique que le registre est libre
		compiler.getRegManager().freeRegistre(reg1.getNumber(), compiler);
	}

}
