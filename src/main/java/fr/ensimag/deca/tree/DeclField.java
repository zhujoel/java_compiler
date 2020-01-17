package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.tools.IndentPrintStream;

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
    final private AbstractIdentifier varName;
    final private AbstractInitialization initialization;
    
    public DeclField(Visibility visibility, AbstractIdentifier type,
    		AbstractIdentifier varName, AbstractInitialization initialization) {
    	Validate.notNull(visibility);
        Validate.notNull(type);
        Validate.notNull(varName);
        Validate.notNull(initialization);
        this.visibility = visibility;
        this.type = type;
        this.varName = varName;
        this.initialization = initialization;
    }
    
    
	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
		s.println(prefix + "visibility="+visibility);
        type.prettyPrint(s, prefix, false);
        varName.prettyPrint(s, prefix, false);
        initialization.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}

}
