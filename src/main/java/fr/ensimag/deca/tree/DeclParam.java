package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;

import fr.ensimag.deca.tools.IndentPrintStream;

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
    
	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub
		
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

}
