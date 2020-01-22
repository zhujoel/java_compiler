package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 * List of expressions (eg list of parameters).
 *
 * @author gl48
 * @date 01/01/2020
 */
public class ListExpr extends TreeList<AbstractExpr> {


    @Override
    public void decompile(IndentPrintStream s) {
    	for(int i = 0; i < getList().size()-1; ++i) {
    		getList().get(i).decompile(s);
    		s.print(", ");
    	}
    	getList().get(getList().size()-1).decompile(s);
    }
}
