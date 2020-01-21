package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Liste des méthodes d'une classe.
 * @author zhujo
 *
 */
public class ListDeclMethod extends TreeList<AbstractDeclMethod>{

	@Override
	public void decompile(IndentPrintStream s) {
		for(AbstractDeclMethod m : getList()) {
			m.decompile(s);
			s.println();
		}
	}
	
	public void codeGenListMethod(DecacCompiler compiler) {
		for (AbstractDeclMethod m : getList()) {
			m.codeGenDeclMethod(compiler);
		}
	}
	

}
