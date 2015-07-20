package bp.bflow.samples.lowlevel.sanitycheckvalue;


import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;

public class StepStartRequestTest {

	@Test
	public void testContains() {
		StepStartEvent ser1 = new StepStartEvent(1d, "A");
		StepStartEvent ser2 = new StepStartEvent(44d, "A");
		assertTrue( ser1.contains(ser2) );
		assertTrue( ser2.contains(ser1) );
		assertTrue( ser2.contains(ser2) );
		assertTrue( ser1.contains(ser1) );
	}
	
	@Test
	public void testDoesNotContain() {
		StepStartEvent ser1 = new StepStartEvent(1d, "A");
		StepStartEvent ser2 = new StepStartEvent(1d, "B");
		assertFalse( ser1.contains(ser2) );
		assertFalse( ser2.contains(ser1) );
	}

}
