package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.IMAProgram;

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
	
	public void codeGenListMethod(DecacCompiler compiler, AbstractIdentifier className) {
		for (AbstractDeclMethod m : getList()) {
			m.codeGenDeclMethod(compiler, className);
		}
	}
	

}
