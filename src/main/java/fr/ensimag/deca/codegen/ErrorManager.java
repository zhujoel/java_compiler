package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.ImmediateString;
import fr.ensimag.ima.pseudocode.Label;
import fr.ensimag.ima.pseudocode.instructions.ERROR;
import fr.ensimag.ima.pseudocode.instructions.WNL;
import fr.ensimag.ima.pseudocode.instructions.WSTR;

public class ErrorManager {
	public static Label tabLabel[] = { new Label("pile_pleine"), new Label("dereferencement_null"), 
			new Label("tas_plein"), new Label("debordement"), new Label("io_error")};
	public static String tabStr[] = {"Erreur : Pile pleine.", "Erreur : dereferencement null.", "Erreur : Tas plein.",
			"Erreur : debordement durant une operation arithmétique", "Erreur : input ou output"};
	
	public static void codeGenError(DecacCompiler compiler, Label label,String string) {
		compiler.addLabel(label);
		compiler.addInstruction(new WSTR(new ImmediateString(string)));
		compiler.addInstruction(new WNL());
		compiler.addInstruction(new ERROR());
	}
	
	public static void addErrorLabels(DecacCompiler compiler) {
		for(int i = 0; i < tabLabel.length; i++) {
			codeGenError(compiler, tabLabel[i], tabStr[i]);
		}
	}
}
