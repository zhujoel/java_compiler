package fr.ensimag.deca.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.ima.pseudocode.Label;

public class TestMethod {
	
	
	Signature s = new Signature();
	Label lbl = new Label("meth1");
	MethodDefinition meth1 = new MethodDefinition(null, null, s, 0);
	Signature s1 = new Signature();
	Signature s2 = new Signature();
	Definition meth2 = new MethodDefinition(null, Location.BUILTIN, s2, 0);
	
	@Before
	public void setup() {
		Type t = new ClassType(null);
		s1.add(t);
		s2.add(t);
	}
	
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
	
	@Test
	public void testSignSize() {
		assertEquals(s.size(), 0);
		assertEquals(s1.size(), 1);
	}
	
	@Test
	public void testSignEquals() {
		assertEquals(s1, s2);
		assertFalse(s.equals(s2));
	}
	
	@Test
	public void testAsMethodDefinition() throws ContextualError {
		meth2 = meth2.asMethodDefinition("", null);
	}
	
	@Test(expected = ContextualError.class)
	public void testAsFieldDefinition() throws ContextualError {
		meth2 = meth2.asFieldDefinition("", null);
	}
	
	@Test
	public void testIsClass() {
		assertFalse(meth1.isClass());
	}
	
}
