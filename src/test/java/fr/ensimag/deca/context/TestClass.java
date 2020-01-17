package fr.ensimag.deca.context;

import static org.mockito.Mockito.when;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class TestClass {
	
	public final int NB_FIELD_A = 5;
	public ClassType t = new ClassType(null);
	ClassDefinition classDefC = new ClassDefinition(null, null, null);
	

	@Mock
	ClassDefinition classDefA;
	
	ClassDefinition classDefB;
	
	ClassType classTypeA;
	
	@Mock
	ClassType classTypeB;
	
	@Mock
	EnvironmentExp envExp;
	

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		classDefB = new ClassDefinition(null, null, classDefA);
		classTypeA = new ClassType(null);
		when(classDefA.getType()).thenReturn(t);
		when(classTypeB.isClass()).thenReturn(true);
		

	}
	
	@Test
	public void testClassParent() {
		assertTrue(classDefB.getSuperClass().equals(classDefA));
	}
	
	@Test
	public void testFieldNumber() {
		classDefC.setNumberOfFields(3);
		classDefC.incNumberOfFields();
		assertEquals(classDefC.getNumberOfFields(),4);

		
	}
	
	@Test
	public void testMethodNumber() {
		classDefC.setNumberOfMethods(3);
		classDefC.incNumberOfMethods();
		assertEquals(classDefC.getNumberOfMethods(),4);
	}
	
	
	//faire les tests classtype
	
	@Test
	public void testIsClass() {
		assertTrue(classTypeA.isClass());
	}
	
	@Test
	public void testIsClassOrNull() {
		assertTrue(classTypeA.isClassOrNull());
	}
	
	@Test
	public void testSameType() {
		assertTrue(classTypeA.sameType(classTypeB));
	}
	

	

}
