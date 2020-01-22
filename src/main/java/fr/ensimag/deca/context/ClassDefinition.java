package fr.ensimag.deca.context;

import fr.ensimag.deca.tools.SymbolTable.Symbol;
import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;
import org.apache.commons.lang.Validate;

/**
 * Definition of a class.
 *
 * @author gl48
 * @date 01/01/2020
 */
public class ClassDefinition extends TypeDefinition {


    public void setNumberOfFields(int numberOfFields) {
        this.numberOfFields = numberOfFields;
    }

    public int getNumberOfFields() {
        return numberOfFields;
    }

    public void incNumberOfFields() {
        this.numberOfFields++;
    }

    public int getNumberOfMethods() {
        return numberOfMethods;
    }

    public void setNumberOfMethods(int n) {
        Validate.isTrue(n >= 0);
        numberOfMethods = n;
    }
    
    public int incNumberOfMethods() {
        numberOfMethods++;
        return numberOfMethods;
    }

    private int numberOfFields = 0;
    private int numberOfMethods = 0;
    
    @Override
    public boolean isClass() {
        return true;
    }
    
    @Override
    public ClassType getType() {
        // Cast succeeds by construction because the type has been correctly set
        // in the constructor.
        return (ClassType) super.getType();
    };

    public ClassDefinition getSuperClass() {
        return superClass;
    }

    private final EnvironmentExp members;
    private final ClassDefinition superClass; 

    public EnvironmentExp getMembers() {
        return members;
    }

    public ClassDefinition(ClassType type, Location location, ClassDefinition superClass) {
        super(type, location);
        EnvironmentExp parent;
        if (superClass != null) {
            parent = superClass.getMembers();
        } else {
            parent = null;
        }
        members = new EnvironmentExp(parent);
        this.superClass = superClass;
    }
    
    /**
     * Chercher recursivement la definition d'un identifier
     * @param name le symbol de l'identifier
     * @return true si l'identifier est defini dans l'une des classes parente
     */
    public boolean checkFirstSuperDefinition(Symbol name) {
    	if(this.superClass == null){
    		return false;
    	}
    	return this.superClass.getMembers().isIn(name) || superClass.checkFirstSuperDefinition(name);
    }
    
    /**
     * Retourne la premier classe en partant de this qui contient l'identifier name
     * @param name le symbole de l'identifier recherché
     * @return la ClassDefinition si elle existe et null sinon
     */
    public ClassDefinition getFirstSuperClassWithDef(Symbol name) {
    	if(superClass != null && this.superClass.getMembers().isIn(name)) {
    		return superClass;
    	}else if(superClass != null){
    		return superClass.getFirstSuperClassWithDef(name);
    	}else {
    		return null;
    	}
    	
    }
    
    /**
     * On remonte recursivement la chaine de parent pour voir si l'un d'entre eux est egal à c
     * @param c le potentiel parent
     * @return true si l'une des classe parent est identique à c false sinon
     */
    public boolean hasForParent(ClassDefinition c) {
    	if (superClass != null){
    		return c.getType().sameType(this.getType()) || superClass.hasForParent(c);
    	}else{
    		return c.getType().sameType(this.getType()) ;
    	}
    }
    
    
}
