package fr.ensimag.deca.context;

import fr.ensimag.deca.context.Type;
import fr.ensimag.deca.tools.SymbolTable;
import fr.ensimag.ima.pseudocode.DVal;
import fr.ensimag.ima.pseudocode.ImmediateInteger;

/**
 *
 * @author Ensimag
 * @date 01/01/2020
 */
public class BooleanType extends Type {

    public BooleanType(SymbolTable.Symbol name) {
        super(name);
    }

    @Override
    public boolean isBoolean() {
        return true;
    }

    @Override
    public boolean sameType(Type otherType) {
        return this.equals(otherType);
        //throw new UnsupportedOperationException("not yet implemented");
    }


}
