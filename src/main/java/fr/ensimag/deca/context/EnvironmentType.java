package fr.ensimag.deca.context;

import java.util.HashMap;
import java.util.Map;

import fr.ensimag.deca.context.EnvironmentExp.DoubleDefException;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.AbstractIdentifier;
import fr.ensimag.deca.tree.Location;
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
		env.put(symbolTable.create("Object"), new ClassType(symbolTable.create("Object"), Location.BUILTIN, null));
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
	
	public boolean isIn(Symbol key) {
		return env.containsKey(key);
	}
	
	/**
	 * Récupère le numéro de offset d'un attribut pour l'accès avec assembleur
	 * @return
	 */
	public int getAttributeOffset(AbstractIdentifier className, AbstractIdentifier attr) {
		ClassType clType = (ClassType)this.get(className.getName());
		EnvironmentExp env = clType.getDefinition().getMembers();
		int offset = 1;
		for(Map.Entry<Symbol, ExpDefinition> entry : env.getEnv().entrySet()) {
			if(entry.getValue() instanceof FieldDefinition) {
				if(entry.getKey().getName().equals(attr.getName().getName())) {
					return offset;
				}
				++offset;
			}
		}
		return -1;
	}
}
