package fr.ensimag.deca.tree;

import java.io.PrintStream;

import org.apache.commons.lang.Validate;
import org.apache.log4j.Logger;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.context.ClassDefinition;
import fr.ensimag.deca.context.ContextualError;
import fr.ensimag.deca.context.Definition;
import fr.ensimag.deca.context.EnvironmentExp;
import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Corps d'une méthode. Composé de déclaration de variables locales et d'instructions.
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
		s.println("{");
		s.indent();
		this.decls.decompile(s);
		this.insts.decompile(s);
        s.unindent();
		s.print("}");
		
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
	
	@Override
	public void verifyMethodBody(DecacCompiler compiler, EnvironmentExp envParam,
			ClassDefinition currentClass, Type returnType) throws ContextualError {
		decls.verifyListDeclVariable(compiler, envParam, currentClass);
		insts.verifyListInst(compiler, envParam, currentClass, returnType);
	}


}
