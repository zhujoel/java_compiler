package fr.ensimag.deca.context;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import fr.ensimag.deca.tree.Location;


public class TestClass {
	
	public final int NB_FIELD_A = 5;
	public ClassType t = new ClassType(null);
	ClassDefinition classDefC = new ClassDefinition(null, null, null);
	TypeDefinition typeDef = new TypeDefinition(null, null);
	StringType s1 = new StringType(null);
	StringType s2 = new StringType(null);
	VoidType v = new VoidType(null);
	

	@Mock
	ClassDefinition classDefA;
	
	ClassDefinition classDefB;
	
	ClassType classTypeA;
	
	@Mock
	ClassType classTypeB;
	
	ClassType classTypeC;
	
	@Mock
	EnvironmentExp envExp;
	
	

	
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		classDefB = new ClassDefinition(null, null, classDefA);
		classTypeA = new ClassType(null);
		classTypeC = new ClassType(null, null, classDefB);
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
	public void testClassTypeIsClass() {
		assertTrue(classTypeA.isClass());
	}
	
	@Test
	public void testClassTypeIsClassOrNull() {
		assertTrue(classTypeA.isClassOrNull());
	}
	
	
	@Test
	public void testClassTypeDefintion() {
		assertTrue(classTypeC.getDefinition().getSuperClass().equals(classDefB));
	}
	
	@Test
	public void testAssertType() {
		assertEquals((ClassType) classTypeA, classTypeA.asClassType(null, null));
	}
	
	@Test
	public void testDefLocation() {
		classDefB.setLocation(Location.BUILTIN);
		assertEquals(classDefB.getLocation(), Location.BUILTIN);
	}
	
	@Test
	public void testDefIsField() {
		assertFalse(classDefB.isField());
	}
	
	@Test
	public void testDefIsMethod() {
		assertFalse(classDefB.isMethod());
	}
	
	@Test
	public void testDefIsClass() {
		assertTrue(classDefB.isClass());
	}
	
	
	@Test
	public void testDefIsParam() {
		assertFalse(classDefB.isParam());
	}
	
	@Test(expected = ContextualError.class)
	public void testAsMethodDefintion() throws ContextualError {
		classDefB.asMethodDefinition(null, null);
	}
	
	@Test(expected = ContextualError.class)
	public void testAdFieldDefintion() throws ContextualError{
		classDefB.asFieldDefinition(null, null);
	}

	@Test
	public void testTypeDefgetNature() {
		assertEquals(typeDef.getNature(), "type");
	}
	
	@Test
	public void testTypeDefIsExpression() {
		assertFalse(typeDef.isExpression());
	}
	
	@Test
	public void testStringSame() {
		assertTrue(s1.sameType(s2));
	}
	
	@Test
	public void testStringisString() {
		assertTrue(s1.isString());
	}
	
	@Test
	public void testVoidIsSameType() {
		assertTrue(v.sameType(new VoidType(null)));
	}
	
	@Test
	public void testVoidIsVoid() {
		assertTrue(v.isVoid());
	}
	
	@Test(expected = ContextualError.class)
	public void testAsClassDefinitionWrong() throws ContextualError {
		v.asClassType(null, null);
	}
	
	@Test
	public void testAsClassDefiniton() throws ContextualError {
		t.asClassType(null, null);
	}
	
}
