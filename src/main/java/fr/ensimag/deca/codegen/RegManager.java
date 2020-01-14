package fr.ensimag.deca.codegen;

import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;

public class RegManager {
	private int nbRegMax;
	private boolean registresOccupes[];
	
	public RegManager(int nbReg) {
		this.nbRegMax = nbReg;
		this.registresOccupes = new boolean[nbRegMax];
	}
	
	public GPRegister getRegistreLibre() {
		for(int i = 0; i < nbRegMax; ++i) {
			if(!registresOccupes[i]) {
				registresOccupes[i] = true;
				return Register.getR(i);
			}
		}
		// Penser au cas où l'on utilise la pile
		return null;
	}
	
	public boolean freeRegistre(int i) {
		if(registresOccupes[i]) {
			registresOccupes[i] = false;
			return true;
		}
		return false;
	}
}
