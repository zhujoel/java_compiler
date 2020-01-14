package fr.ensimag.deca.context;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.DecacCompiler;
import fr.ensimag.deca.tree.AbstractExpr;
import fr.ensimag.deca.tree.Modulo;
/**
 * Ce jeu de test verifie que le modulo genere bien une expression entiere
 * @author flichya
 *
 */
public class TestModAdvanced {
	final Type INT = new IntType(null);
	final Type FLOAT = new FloatType(null);

	
	DecacCompiler compiler;
	
	@Mock
	AbstractExpr intExpr1;
	@Mock
	AbstractExpr intExpr2;

	@Before
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(intExpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(intExpr2.verifyExpr(compiler, null, null)).thenReturn(INT);
	}
	
	@Test
    public void testIntInt() throws ContextualError {
        Modulo t = new Modulo(intExpr1, intExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
        // check that the mocks have been called properly.
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
    }
}
