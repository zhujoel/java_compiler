package fr.ensimag.deca.tree;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.codegen.ErrorManager;
import fr.ensimag.deca.tools.IndentPrintStream;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.ImmediateInteger;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.RegisterOffset;
import fr.ensimag.ima.pseudocode.instructions.BOV;
import fr.ensimag.ima.pseudocode.instructions.NEW;
import fr.ensimag.ima.pseudocode.instructions.RTS;
import fr.ensimag.ima.pseudocode.instructions.TSTO;

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

	
	public void codeGenListField(DecacCompiler compiler, Symbol symbol) {
		compiler.addIMABloc();
		// On vérifie qu'il y a de la place pour ranger les attributs dans le tas
		Label init = new Label("init." + symbol.getName());
		GPRegister reg = compiler.getRegManager().getRegistreLibre(compiler);
		compiler.addLabel(init);
		
		compiler.addInstruction(new TSTO(new ImmediateInteger(getList().size())));
		compiler.addInstruction(new BOV(ErrorManager.tabLabel[2]));
		compiler.addInstruction(new NEW(new ImmediateInteger(getList().size()), reg));
		
		for (AbstractDeclField i : getList()) {
			i.codeGenDeclField(compiler,new RegisterOffset(getList().indexOf(i) + 1, reg));
    	}  
		
		compiler.addInstruction(new RTS());
    }
}
