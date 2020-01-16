package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Corps d'une méthode
 * @author zhujo
 *
 */
public class MethodBody extends AbstractMethodBody {
	private static final Logger LOG = Logger.getLogger(DeclVar.class);
	
	// déclarations de variables au sein de la méthode
    final private ListDeclVar decls;
    // instructions au sein de la méthode
    final private ListInst insts;

    public MethodBody(ListDeclVar decls,
            ListInst insts) {
        Validate.notNull(decls);
        Validate.notNull(insts);
        this.decls = decls;
        this.insts = insts;
    }

	@Override
	public void decompile(IndentPrintStream s) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void prettyPrintChildren(PrintStream s, String prefix) {
        decls.prettyPrint(s, prefix, false);
        insts.prettyPrint(s, prefix, true);
	}

	@Override
	protected void iterChildren(TreeFunction f) {
		// TODO Auto-generated method stub
		
	}


}
