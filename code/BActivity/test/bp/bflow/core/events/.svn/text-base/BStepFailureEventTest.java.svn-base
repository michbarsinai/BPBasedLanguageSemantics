package bp.bflow.core.events;


import static org.junit.Assert.*;

import org.junit.Test;

import bp.Event;
import bp.bflow.core.BStep;

public class BStepFailureEventTest {

	@Test
	public void inclusionSameStep() throws Exception {
		BStep myStep = new BStep("a");
		
		Event a = myStep.getFailureEvent();
		Event b = myStep.getFailureEvent();
		
		assertTrue( a.contains(b) );
		assertTrue( b.contains(a) );
	}
	
	@Test
	public void inclusionEqualStep() throws Exception {
		BStep myStepA = new BStep("A Step");
		BStep myStepB = new BStep("A Step");
		
		assertTrue( myStepA.equals(myStepB) );
		assertTrue( myStepB.equals(myStepA) );
		
		Event a = myStepA.getFailureEvent();
		Event b = myStepB.getFailureEvent();
		
		assertTrue( a.contains(b) );
		assertTrue( b.contains(a) );
	}
}
	