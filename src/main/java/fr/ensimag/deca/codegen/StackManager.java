package fr.ensimag.deca.codegen;

public class StackManager {
	
	private static int stackCpt = 1;
	
	public StackManager() {
		
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
}
