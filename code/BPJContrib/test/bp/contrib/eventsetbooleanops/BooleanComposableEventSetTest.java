package bp.contrib.eventsetbooleanops;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import bp.eventSets.EventSetInterface;
import static bp.eventSets.BooleanComposableEventSet.*;

/**
 * 
 * 
 * @author michaelbar-sinai
 */
public class BooleanComposableEventSetTest {
	
	EventSetInterface esiA = new ContainBySubString("A");
	EventSetInterface esiB = new ContainBySubString("B");
	EventSetInterface esiC = new ContainBySubString("C");
	EventSetInterface esiD = new ContainBySubString("D");
	EventSetInterface esiAB = new ContainBySubString("AB");
	EventSetInterface esiABC = new ContainBySubString("ABC");
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testIs() {
		assertTrue( theEventSet(esiA).contains(esiA) );
		assertFalse( theEventSet(esiA).contains(esiB) );
	}

	@Test
	public void testNot() {
		assertTrue( not(esiA).contains(esiB) );
		assertFalse( not(esiA).contains(esiA) );
	}
	
	@Test
	public void testAnyOf() {
		EventSetInterface actual = anyOf(esiA, esiB);
		assertTrue( actual.contains(esiA) );
		assertTrue( actual.contains(esiB) );
		assertFalse( actual.contains(esiC) );
		assertFalse( actual.contains(esiD) );
	}

	@Test
	public void testAllOf() {
		EventSetInterface actual = allOf(esiABC, esiAB);
		assertTrue( actual.contains(esiA) );
		assertTrue( actual.contains(esiB) );
		assertFalse( actual.contains(esiC) );
		assertFalse( actual.contains(esiD) );
	}
	
	@Test
	public void testAnd() {
		EventSetInterface actual = theEventSet(esiABC).and(esiAB);
		assertTrue( actual.contains(esiA) );
		assertTrue( actual.contains(esiB) );
		assertFalse( actual.contains(esiC) );
		assertFalse( actual.contains(esiD) );
	}

	@Test
	public void testOr() {
		EventSetInterface actual = theEventSet(esiA).or(esiB);
		assertTrue( actual.contains(esiA) );
		assertTrue( actual.contains(esiB) );
		assertFalse( actual.contains(esiC) );
	}

	@Test
	public void testXor() {
		EventSetInterface actual = theEventSet(esiAB).xor(esiABC);
		assertFalse( actual.contains(esiA) );
		assertFalse( actual.contains(esiB) );
		assertTrue( actual.contains(esiC) );
	}

	@Test
	public void testNor() {
		EventSetInterface actual = theEventSet(esiA).nor(esiB);
		assertFalse( actual.contains(esiA) );
		assertFalse( actual.contains(esiB) );
		assertTrue( actual.contains(esiC) );
	}

	@Test
	public void testNand() {
		EventSetInterface actual = theEventSet(esiABC).nand(esiAB);
		assertFalse( actual.contains(esiA) );
		assertFalse( actual.contains(esiB) );
		assertTrue( actual.contains(esiC) );
		assertTrue( actual.contains(esiD) );
	}
}

class ContainBySubString implements EventSetInterface {
	String name;
	
	public ContainBySubString(String name) {
		super();
		this.name = name;
	}

	@Override
	public boolean contains(Object o) {
		if ( o instanceof ContainBySubString ) {
			return name.indexOf(((ContainBySubString)o).name) != -1;
		}
		return false;
	}
	
	
}
