package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.IMAProgram;

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

	/**
	 * Sauvegarde les registres avant d'entrer dans le code de la méthode
	 * @param compiler
	 */
	public void codeGenListParamIn(DecacCompiler compiler) {
		compiler.addComment("Sauvegarde des registres");
        for (int i = 0 ; i < this.getList().size(); ++i) {
            this.getList().get(i).codeGenDeclParamIn(compiler, 2+i);
        }
    }
	
	/**
	 * Restore les registres avant de sortir de la méthode
	 */
	public void codeGenListParamOut(DecacCompiler compiler) {
		compiler.addComment("Restauration des registres");
        for (int i = 0 ; i < this.getList().size(); ++i) {
            this.getList().get(i).codeGenDeclParamOut(compiler, 2+i);
        }
	}
}
