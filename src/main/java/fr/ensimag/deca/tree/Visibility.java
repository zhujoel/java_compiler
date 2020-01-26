package fr.ensimag.deca.tree;

/**
 * Visibility of a field.
 *
 * @author gl48
 * @date 01/01/2020
 */

public enum Visibility {
	// on leur assigne un string pour un affichage plus sympatique :)
    PUBLIC("public"),
    PROTECTED("protected");
    
    private String affichage;
    
    Visibility(String affichage){
    	this.affichage = affichage;
    }
    
    public String getAffichage() {
    	return this.affichage;
    }
}
