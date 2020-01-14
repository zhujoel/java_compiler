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
import fr.ensimag.deca.tree.Equals;
import fr.ensimag.deca.tree.NotEquals;
import fr.ensimag.deca.tree.Or;
import fr.ensimag.deca.tree.And;

public class TestBinaryOpAdvanced {
	final Type BOOLEAN = new BooleanType(null);
	

	
	DecacCompiler compiler;
	
	@Mock
	AbstractExpr booleanExpr1;
	@Mock
	AbstractExpr booleanExpr2;


	@Before
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(booleanExpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        when(booleanExpr2.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);


	}
	
	@Test
    public void testAnd() throws ContextualError {
        And t = new And(booleanExpr1, booleanExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanExpr1).verifyExpr(compiler, null, null);
        verify(booleanExpr2).verifyExpr(compiler, null, null);
    }
	
	@Test
    public void testOr() throws ContextualError {
        Or t = new Or(booleanExpr1, booleanExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanExpr1).verifyExpr(compiler, null, null);
        verify(booleanExpr2).verifyExpr(compiler, null, null);
    }

	@Test
    public void testEq() throws ContextualError {
        Equals t = new Equals(booleanExpr1, booleanExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanExpr1).verifyExpr(compiler, null, null);
        verify(booleanExpr2).verifyExpr(compiler, null, null);
    }
	
	@Test
    public void testNeq() throws ContextualError {
        NotEquals t = new NotEquals(booleanExpr1, booleanExpr2);
        // check the result
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        // check that the mocks have been called properly.
        verify(booleanExpr1).verifyExpr(compiler, null, null);
        verify(booleanExpr2).verifyExpr(compiler, null, null);
    }
   
}
