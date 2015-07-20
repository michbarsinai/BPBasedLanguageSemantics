package bp.bflow.core.events;


import static org.junit.Assert.assertTrue;

import org.junit.Test;

import bp.Event;
import bp.bflow.core.BStep;

public class BStepSuccessEventTest {

	@Test
	public void inclusionSameStep() throws Exception {
		BStep myStep = new BStep("a");
		
		Event a = myStep.getSuccessEvent();
		Event b = myStep.getSuccessEvent();
		
		assertTrue( a.contains(b) );
		assertTrue( b.contains(a) );
	}
	
	@Test
	public void inclusionEqualStep() throws Exception {
		BStep myStepA = new BStep("A Step");
		BStep myStepB = new BStep("A Step");
		
		assertTrue( myStepA.equals(myStepB) );
		assertTrue( myStepB.equals(myStepA) );
		
		Event a = myStepA.getSuccessEvent();
		Event b = myStepB.getSuccessEvent();
		
		assertTrue( a.contains(b) );
		assertTrue( b.contains(a) );
	}
	
	@Test
	public void inclusionValue() throws Exception {
		BStep myStep = new BStep("a");
		
		Event a = myStep.getSuccessEvent();
		Event b = myStep.getSuccessEvent("la la la");
		
		assertTrue( a.contains(b) );
		assertTrue( b.contains(a) );
	}
}
