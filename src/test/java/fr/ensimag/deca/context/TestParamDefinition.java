package fr.ensimag.deca.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestParamDefinition {
	
	ParamDefinition p = new ParamDefinition(null, null);
	
	@Test
	public void testGetNature() {
		assertEquals(p.getNature(), "parameter");
	}
	
	@Test
	public void testIsExpression() {
		assertTrue(p.isExpression());
	}
	
	@Test
	public void testIsParam() {
		assertTrue(p.isParam());
	}
	
}
