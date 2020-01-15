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
import fr.ensimag.deca.tree.Not;
import fr.ensimag.deca.tree.UnaryMinus;

public class TestUnaryOpAdvanced {


    final Type INT = new IntType(null);
    final Type FLOAT = new FloatType(null);
    final Type BOOLEAN = new BooleanType(null);

    @Mock
    AbstractExpr intExpr1;
    @Mock
    AbstractExpr floatExpr1;
    @Mock
    AbstractExpr booleanExpr1;

    DecacCompiler compiler;
    
    @Before
    public void setup() throws ContextualError {
        MockitoAnnotations.initMocks(this);
        compiler = new DecacCompiler(null, null);
        when(intExpr1.verifyExpr(compiler, null, null)).thenReturn(INT);
        when(floatExpr1.verifyExpr(compiler, null, null)).thenReturn(FLOAT);
        when(booleanExpr1.verifyExpr(compiler, null, null)).thenReturn(BOOLEAN);
    }
    
    @Test
    public void testUnaryMinusInt() throws ContextualError {
        UnaryMinus t = new UnaryMinus(intExpr1);
        assertTrue(t.verifyExpr(compiler, null, null).isInt());
        verify(intExpr1).verifyExpr(compiler, null, null);
    }
    
    @Test
    public void testUnaryMinusFloat() throws ContextualError {
        UnaryMinus t = new UnaryMinus(floatExpr1);
        assertTrue(t.verifyExpr(compiler, null, null).isFloat());
        verify(floatExpr1).verifyExpr(compiler, null, null);
    }
    
    @Test
    public void testNot() throws ContextualError {
        Not t = new Not(booleanExpr1);
        assertTrue(t.verifyExpr(compiler, null, null).isBoolean());
        verify(booleanExpr1).verifyExpr(compiler, null, null);
    }
	
}
