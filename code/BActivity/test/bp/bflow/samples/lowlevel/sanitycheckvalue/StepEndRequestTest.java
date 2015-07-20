package bp.bflow.samples.lowlevel.sanitycheckvalue;

import static org.junit.Assert.*;

import org.junit.Test;

import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;

public class StepEndRequestTest {

	@Test
	public void testContains() {
		StepEndEvent ser1 = new StepEndEvent(1d, "A");
		StepEndEvent ser2 = new StepEndEvent(44d, "A");
		assertTrue( ser1.contains(ser2) );
		assertTrue( ser2.contains(ser1) );
		assertTrue( ser2.contains(ser2) );
		assertTrue( ser1.contains(ser1) );
	}
	
	@Test
	public void testDoesNotContain() {
		StepEndEvent ser1 = new StepEndEvent(1d, "A");
		StepEndEvent ser2 = new StepEndEvent(1d, "B");
		assertFalse( ser1.contains(ser2) );
		assertFalse( ser2.contains(ser1) );
	}

}
