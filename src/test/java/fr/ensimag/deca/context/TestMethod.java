package fr.ensimag.deca.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.ensimag.ima.pseudocode.Label;

public class TestMethod {
	
	
	Signature s = new Signature();
	Label lbl = new Label("meth1");
	MethodDefinition meth1 = new MethodDefinition(null, null, s, 0);
	
	@Test
	public void testLabel() {
		meth1.setLabel(lbl);
		assertTrue(meth1.getLabel().equals(lbl));
	}
	
	@Test
	public void testIndex() {
		assertEquals(meth1.getIndex(), 0);
	}
	
	@Test
	public void testSignature() {
		assertTrue(meth1.getSignature().equals(s));
	}
	
	@Test
	public void testIsExpression() {
		assertFalse(meth1.isExpression());
	}
	
	@Test
	public void testNature() {
		assertEquals(meth1.getNature(), "method");
		assertTrue(meth1.isMethod());
	}
}
