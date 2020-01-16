package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.tools.IndentPrintStream;

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
		// TODO Auto-generated method stub
		
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

}
