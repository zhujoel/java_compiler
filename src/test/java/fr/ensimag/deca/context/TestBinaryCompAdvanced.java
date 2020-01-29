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
import fr.ensimag.deca.tree.Greater;
import fr.ensimag.deca.tree.GreaterOrEqual;
import fr.ensimag.deca.tree.Lower;
import fr.ensimag.deca.tree.LowerOrEqual;
import fr.ensimag.deca.tree.NotEquals;

public class TestBinaryCompAdvanced {
	final Type INT = new IntType(null);
	final Type FLOAT = new FloatType(null);
	final Type BOOLEAN = new BooleanType(null);

	
	DecacCompiler compiler;
	
	@Mock
	AbstractExpr intExpr1;
	@Mock
	AbstractExpr intExpr2;
	@Mock
	AbstractExpr floatExpr1;
	@Mock
	AbstractExpr floatExpr2;
	@Mock
	AbstractExpr boolExpr1;
	@Mock
	AbstractExpr boolExpr2;

	@Before
	public void setup() throws ContextualError {
		MockitoAnnotations.initMocks(this);
		compiler = new DecacCompiler(null, null);
		when(intExpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(intExpr2.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(floatExpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(floatExpr2.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(boolExpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
        when(boolExpr2.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);

	}
	
	@Test
	public void testEqIntInt() throws ContextualError{
		Equals t = new Equals(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testEqFloatFloat() throws ContextualError{
		Equals t = new Equals(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testNEqIntInt() throws ContextualError{
		NotEquals t = new NotEquals(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testNEqFloatFloat() throws ContextualError{
		NotEquals t = new NotEquals(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testLTIntInt() throws ContextualError{
		Lower t = new Lower(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testLTFloatFloat() throws ContextualError{
		Lower t = new Lower(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testGTIntInt() throws ContextualError{
		Greater t = new Greater(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testGTFloatFloat() throws ContextualError{
		Greater t = new Greater(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testLEqIntInt() throws ContextualError{
		LowerOrEqual t = new LowerOrEqual(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testLEqFloatFloat() throws ContextualError{
		LowerOrEqual t = new LowerOrEqual(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testGEqIntInt() throws ContextualError{
		GreaterOrEqual t = new GreaterOrEqual(intExpr1, intExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(intExpr1).verifyExpr(compiler, null, null);
        verify(intExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testGEqFloatFloat() throws ContextualError{
		GreaterOrEqual t = new GreaterOrEqual(floatExpr1, floatExpr2);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(floatExpr1).verifyExpr(compiler, null, null);
        verify(floatExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testEqBoolBool() throws ContextualError{
		Equals t = new Equals(boolExpr1, boolExpr2);
		assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
		verify(boolExpr1).verifyExpr(compiler, null, null);
		verify(boolExpr2).verifyExpr(compiler, null, null);
	}
	
	@Test
	public void testNEqBoolBool() throws ContextualError{
		NotEquals t = new NotEquals(boolExpr1, boolExpr2);
		assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
		verify(boolExpr1).verifyExpr(compiler, null, null);
		verify(boolExpr2).verifyExpr(compiler, null, null);
	}
}
