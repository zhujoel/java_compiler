package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
/**
 * Dictionnaire qui associe les TypeDefinition à leurs types
 * @author flichya
 *
 */
//TODO à mieux commenter

public class EnvironmentType {
	
	//Le dictionnaire realisant l'association : type -> typedef
	private HashMap<Symbol, Type> env;
	
	public EnvironmentType(SymbolTable symbolTable) {
		env = new HashMap<Symbol, Type>();
		env.put(symbolTable.create("void"), new VoidType(symbolTable.create("void")));
		env.put(symbolTable.create("bool"), new BooleanType(symbolTable.create("bool")));
		env.put(symbolTable.create("float"), new FloatType(symbolTable.create("float")));
		env.put(symbolTable.create("int"), new IntType(symbolTable.create("int")));
		env.put(symbolTable.create("string"), new StringType(symbolTable.create("string")));
	}
	
	public Type get(Symbol key) {
		return env.getOrDefault(key, null);
	}
	
	
	public void declare(Symbol name, Type type) throws DoubleDefException{
		if (env.containsKey(name)) {
    		throw new DoubleDefException();
    	}
    	else {
    		env.put(name,  type);
    	}
	}
}
