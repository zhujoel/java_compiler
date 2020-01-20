package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * Liste des attributs d'une classe.
 * @author zhujo
 *
 */
public class ListDeclField extends TreeList<AbstractDeclField> {

	@Override
	public void decompile(IndentPrintStream s) {
		for(AbstractDeclField f : getList()) {
			f.decompile(s);
			s.println();
		}
		
	}

}
