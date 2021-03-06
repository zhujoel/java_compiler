package fr.ensimag.ima.pseudocode;

/**
 * Immediate operand representing a string.
 * 
 * @author Ensimag
 * @date 01/01/2020
 */
public class ImmediateString extends Operand {
    private String value;

    public ImmediateString(String value) {
        super();
        this.value = value;
    }

    @Override
    public String toString() {
    	//TODO a modifier
        return "\"" + value.replace("\"", "") + "\"";
        //ancien : return "\"" + value.replace("\"", "\"\"") + "\"";
    }
}
