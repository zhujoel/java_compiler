package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.LabelOperand;
import fr.ensimag.ima.pseudocode.NullOperand;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.LOAD;
import fr.ensimag.ima.pseudocode.instructions.STORE;

/**
 * Liste des paramètres d'une méthode d'une classe.
 * @author zhujo
 *
 */
public class ListDeclParam extends TreeList<AbstractDeclParam> {

	@Override
	public void decompile(IndentPrintStream s) {
		s.print("(");
		for(int i = 0; i < getList().size()-1; ++i) {
			getList().get(i).decompile(s);
			s.print(", ");
		}
		if(getList().size() != 0) {
			getList().get(getList().size()-1).decompile(s);
		}
		s.print(")");
	}

	public void codeGenListClass(DecacCompiler compiler) {
        for (AbstractDeclParam i : getList()) {
            i.codeGenDeclParam(compiler);
        }
        
    }
}
