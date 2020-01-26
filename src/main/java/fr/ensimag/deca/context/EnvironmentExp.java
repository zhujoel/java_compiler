package fr.ensimag.deca.context;

import java.util.HashMap;

import fr.ensimag.deca.tools.SymbolTable.Symbol;

/**
 * Dictionary associating identifier's ExpDefinition to their names.
 * 
 * This is actually a linked list of dictionaries: each EnvironmentExp has a
 * pointer to a parentEnvironment, corresponding to superblock (eg superclass).
 * 
 * The dictionary at the head of this list thus corresponds to the "current" 
 * block (eg class).
 * 
 * Searching a definition (through method get) is done in the "current" 
 * dictionary and in the parentEnvironment if it fails. 
 * 
 * Insertion (through method declare) is always done in the "current" dictionary.
 * 
 * @author gl48
 * @date 01/01/2020
 */
public class EnvironmentExp {
	
    EnvironmentExp parentEnvironment;
    
    private HashMap<Symbol, ExpDefinition> env;//Ici je met ExpDefinition et pas Definition car le dictionnaire n'est
    //censé contenir que des expressions
    
    public EnvironmentExp(EnvironmentExp parentEnvironment) {
        this.parentEnvironment = parentEnvironment;
        env = new HashMap<Symbol, ExpDefinition>();
    }

    public static class DoubleDefException extends Exception {
        private static final long serialVersionUID = -2733379901827316441L;
    }

    /**
     * Return the definition of the symbol in the environment, or null if the
     * symbol is undefined.
     */
    public ExpDefinition get(Symbol key) {
    	if(this.parentEnvironment == null) {
    		return env.get(key);
    	} else if (env.get(key) == null) {
    		return this.parentEnvironment.get(key);
    	} 
    	return env.get(key);
    }

    /**
     * Add the definition def associated to the symbol name in the environment.
     * 
     * Adding a symbol which is already defined in the environment,
     * - throws DoubleDefException if the symbol is in the "current" dictionary 
     * - or, hides the previous declaration otherwise.
     * 
     * @param name
     *            Name of the symbol to define
     * @param def
     *            Definition of the symbol
     * @throws DoubleDefException
     *             if the symbol is already defined at the "current" dictionary
     *
     */
    public void declare(Symbol name, ExpDefinition def) throws DoubleDefException {
        if (env.containsKey(name)) {
    		throw new DoubleDefException();
    	}
    	else {
    		env.put(name,  def);
    	}
    }
    
    /**
     * Fonction pour verifier si une variable a bien été déclarée dans l'environement
     * @param name
     * @return
     */
    public boolean isIn(Symbol name) {
    	//peut etre a modifier plus tard (voir dans les env parents ?)
    	return env.containsKey(name);
    }
    
    
    /**
     * Ajoute la définition la définition associé à un symbol dans l'environnement.
     * Si le symbole existe déjà, change sa définition si les deux définitions sont différentes,
     * sinon, lance une exception
     * @param name Nom du symbole
     * @param def Definition du symbole
     * @throws DoubleDefException Si le symbole est déjà défini et est identique
     */
    public void declareOrSet(Symbol name, ExpDefinition def) throws DoubleDefException{
    	if(env.containsKey(name)) {
    		if(env.get(name).equals(def)) {
    			throw new DoubleDefException();
    		}
    	}
    	env.put(name, def);
    }
    
    public HashMap<Symbol, ExpDefinition> getEnv(){
    	return this.env;
    }

}
