package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
/**
 * Dictionnaire qui associe les TypeDefinition à leurs types
 * @author flichya
 *
 */
//TODO à mieux commenter

public class EnvironmentType {
	//L'environement parent
	EnvironmentType parentEnvironment;
	
	//Le dictionnaire realisant l'association : type -> typedef
	private HashMap<Symbol, TypeDefinition> env;
	
	public EnvironmentType(EnvironmentType parent) {
		parentEnvironment = parent;
		env = new HashMap<Symbol, TypeDefinition>();
	}
	
	public TypeDefinition get(Symbol key) {
		return env.get(key);
	}
	
	public void declare(Symbol name, TypeDefinition def) throws DoubleDefException{
		if (env.containsKey(name)) {
    		throw new DoubleDefException();
    	}
    	else {
    		env.put(name,  def);
    	}
	}
}
