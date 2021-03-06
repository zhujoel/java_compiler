package fr.ensimag.deca.context;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Signature of a method (i.e. list of arguments)
 *
 * @author gl48
 * @date 01/01/2020
 */
public class Signature {
    List<Type> args = new ArrayList<Type>();

    public void add(Type t) {
        args.add(t);
    }
    
    public Type paramNumber(int n) {
        return args.get(n);
    }
    
    public int size() {
        return args.size();
    }
    
    /**
     * Deux signatures sont egales si les arguments des listes de ces signatures sont 2 a 2 identiques
     */
    public boolean equals(Object o) {
    	if (o != null && o instanceof Signature) {
    		return args.equals(((Signature) o).args);
    	}
    	return false;
    }
    
    public List<Type> getList() {
    	return this.args;
    }

}
