package fr.ensimag.deca.context;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestNullType {

	NullType n = new NullType(null);
	
	@Test
	public void testIsNull() {
		assertTrue(n.isNull());
	}
	
	@Test
	public void testIsClassOrNull() {
		assertTrue(n.isClassOrNull());
	}
	
}
