package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.ima.pseudocode.instructions.WNL;

/**
 * @author gl48
 * @date 01/01/2020
 */
public class Println extends AbstractPrint {

    /**
     * @param arguments arguments passed to the print(...) statement.
     * @param printHex if true, then float should be displayed as hexadecimal (printlnx)
     */
    public Println(boolean printHex, ListExpr arguments) {
        super(printHex, arguments);
    }

    @Override
    protected void codeGenInst(DecacCompiler compiler) {
        super.codeGenInst(compiler);
        compiler.addInstruction(new WNL());
    }

    @Override
    String getSuffix() {
        return "ln";
    }
    
    @Override
    public void decompile(IndentPrintStream s) {
    	if(this.getPrintHex()) {
    		s.print("printlnx(");
    	}
    	else {
    		s.print("println(");
    	}
    	this.getArguments().decompile(s);
    	s.print(");");
    }
}
