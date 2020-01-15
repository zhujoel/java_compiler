package fr.ensimag.deca.codegen;

import java.util.HashMap;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;

/**
 * Classe qui permet de lier un type avec sa valeur par défaut
 * @author zhujo
 *
 */
public class EnvironmentDefault {

	private HashMap<Type, DVal> defaultValues;
	
	public EnvironmentDefault(SymbolTable symbolTable) {
		this.defaultValues = new HashMap<>();
		
	}
	
	public HashMap<Type, DVal> getDefaultValues() {
		return defaultValues;
	}
}
