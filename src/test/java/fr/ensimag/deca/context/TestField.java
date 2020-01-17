package fr.ensimag.deca.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import fr.ensimag.deca.tree.Location;
import fr.ensimag.deca.tree.Visibility;

public class TestField {
	
	ClassDefinition c = new ClassDefinition(null, null, null);
	FieldDefinition f = new FieldDefinition(null, Location.BUILTIN, Visibility.PUBLIC, c, 0);
	
	@Test
	public void testIndex() {
		assertEquals(f.getIndex(), 0);
	}
	
	@Test
	public void testIsField() {
		assertTrue(f.isField());
	}
	
	@Test
	public void testAsFieldDefintion() throws ContextualError{
		assertEquals((FieldDefinition) f, f.asFieldDefinition(null, null));
	}
	
	@Test
	public void testVisibility() {
		assertEquals(f.getVisibility(), Visibility.PUBLIC);
	}
	
	@Test
	public void testContainingClass() {
		assertEquals(f.getContainingClass(), c);
	}
	
	@Test
	public void testNature() {
		assertEquals(f.getNature(), "field");
	}
	
	@Test
	public void testIsExpression() {
		assertTrue(f.isExpression());
	}
}
