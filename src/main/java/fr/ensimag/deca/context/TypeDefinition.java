package fr.ensimag.deca.context;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.DAddr;

/**
 * Definition of a Deca type (builtin or class).
 *
 * @author gl48
 * @date 01/01/2020
 */
public class TypeDefinition extends Definition {

	public void setOperand(DAddr operand) {
		this.operand = operand;
	}

	public DAddr getOperand() {
		return operand;
	}
	private DAddr operand;

	public TypeDefinition(Type type, Location location) {
		super(type, location);
	}

	@Override
	public String getNature() {
		return "type";
	}

	@Override
	public boolean isExpression() {
		return false;
	}

}
