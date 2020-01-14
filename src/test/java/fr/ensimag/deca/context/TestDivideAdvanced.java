package fr.ensimag.deca.context;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.ConvFloat;
import fr.ensimag.deca.tree.Divide;

/**
 * Ce jeu de test verifie que l'operation Divide genere bien une expression entiere ou flottante
 * @author flichya
 *
 */

public class TestDivideAdvanced {
	final Type INT = new IntType(null);
	final Type FLOAT = new FloatType(null);

	
	DecacCompiler compiler;
	
	@Mock
	AbstractExpr intExpr1;
	@Mock
	AbstractExpr intExpr2;
	@Mock
	AbstractExpr floatExpr1;
	@Mock
	AbstractExpr floatExpr2;

	@Before
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(intExpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(intExpr2.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(floatExpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(floatExpr2.verifyExpr(compiler, null, null)).thenReturn(FLOAT);

	}
	
	@Test
    public void testIntInt() throws ContextualError {
        Divide t = new Divide(intExpr1, intExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
        // check that the mocks have been called properly.
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
    }

    @Test
    public void testIntFloat() throws ContextualError {
        Divide t = new Divide(intExpr1, floatExpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isFloat());
        // ConvFloat should have been inserted on the right side
        assertTrue(t.getLeftOperand() instanceof ConvFloat);
        assertFalse(t.getRightOperand() instanceof ConvFloat);
        // check that the mocks have been called properly.
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr1).verifyExpr(compiler, null, null);
    }

    @Test
    public void testFloatInt() throws ContextualError {
        Divide t = new Divide(floatExpr1, intExpr1);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isFloat());
        // ConvFloat should have been inserted on the right side
        assertTrue(t.getRightOperand() instanceof ConvFloat);
        assertFalse(t.getLeftOperand() instanceof ConvFloat);
        // check that the mocks have been called properly.
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr1).verifyExpr(compiler, null, null);
    }
    
    @Test
    public void testFloatFloat() throws ContextualError {
    	Divide t = new Divide(floatExpr1, floatExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isFloat());
        // check that the mocks have been called properly.
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
    }
}
