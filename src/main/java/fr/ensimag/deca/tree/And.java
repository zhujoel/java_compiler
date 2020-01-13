package fr.ensimag.deca.tree;

import fr.ensimag.deca.tools.IndentPrintStream;

/**
 *
 * @author gl48
 * @date 01/01/2020
 */
public class And extends AbstractOpBool {

    public And(AbstractExpr leftOperand, AbstractExpr rightOperand) {
        super(leftOperand, rightOperand);
    }

    @Override
    protected String getOperatorName() {
        return "&&";
    }

}
