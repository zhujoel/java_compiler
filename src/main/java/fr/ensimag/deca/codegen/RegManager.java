package fr.ensimag.deca.codegen;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.ima.pseudocode.GPRegister;
import fr.ensimag.ima.pseudocode.Register;
import fr.ensimag.ima.pseudocode.instructions.POP;
import fr.ensimag.ima.pseudocode.instructions.PUSH;

public class RegManager {
	private int nbRegMax;
	private boolean registresOccupes[];
	public static int stackCpt = 2;
	
	// Compteur des labels pour avoir des noms différents dans la génération
	// de label lors de codeGen
    private int nWhile = 1;
    private int nIf = 1;
    private int nOr = 1;
    private int nAnd = 1;
    private int pushed = 0;
	
	public RegManager(int nbReg) {
		this.nbRegMax = nbReg;
		this.registresOccupes = new boolean[nbRegMax];
	}
	
	public void addStackCpt() {
		stackCpt++;
	}
	
	public void subStackCpt() {
		stackCpt--;
	}
	
	public int getStackCpt() {
		return stackCpt;
	}
	
	public int getNWhile() {
		return nWhile;
	}
	
	public int getNIf() {
		return nIf;
	}
	
	public int getNAnd() {
		return nAnd;
	}
	
	public int getNOr() {
		return nOr;
	}
	
	public void addNWhile() {
		nWhile++;
	}
	
	public void addNIf() {
		nIf++;
	}
	
	public void addNAnd() {
		nAnd++;
	}
	
	public void addNOr() {
		nOr++;
	}
	
	public void setNbRegistreMax(int nb) {
		this.nbRegMax = nb;
	}
	
	public GPRegister getRegistreLibre(DecacCompiler compiler) {
		for(int i = 2; i < nbRegMax; ++i) {
			if(!registresOccupes[i]) {
				registresOccupes[i] = true;
				return Register.getR(i);
			}
		}
		
		// Cas d'une pénurie de registre
		compiler.addInstruction(new PUSH(GPRegister.getR(this.nbRegMax-1)));
		registresOccupes[this.nbRegMax - 1] = true;
		this.pushed++;
		return GPRegister.getR(this.nbRegMax - 1);
	}
	
	public boolean allAreBusy() {
		for(int i = 2; i < nbRegMax; ++i) {
			if(!registresOccupes[i]) {
				return false;
			}
		}
		return true;
	}
	
	public boolean freeRegistre(int i,DecacCompiler compiler) {
		if(i == (this.nbRegMax - 1) && this.pushed > 0) {
			compiler.addInstruction(new POP(GPRegister.getR(this.nbRegMax - 1)));
			this.pushed--;
			registresOccupes[i] = false;
			return true;
		}
		else if(registresOccupes[i]) {
			registresOccupes[i] = false;
			return true;
		}
		return false;
	}
	
	public void clearStack(DecacCompiler compiler) {
		for(int i = 0; i < (this.pushed); i++) {
			compiler.addInstruction(new POP(GPRegister.getR(nbRegMax - 1)));
		}
	}
	
}
