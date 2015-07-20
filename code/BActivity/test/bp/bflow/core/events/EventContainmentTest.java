package bp.bflow.core.events;

import static org.junit.Assert.*;

import org.junit.Test;

import bp.bflow.core.BFlow;
import bp.bflow.core.BStep;

public class EventContainmentTest {
	
	@Test
	public void sameStepNoParent() {
		BStep stepA = new BStep("A");
		BStep stepA_ = new BStep("A");
		
		assertTrue( stepA.getStartEvent().contains(stepA.getStartEvent()) );
		assertTrue( stepA.getStartEvent().contains(stepA_.getStartEvent()) );
		assertTrue( stepA_.getStartEvent().contains(stepA.getStartEvent()) );
		
		assertFalse( stepA.getStartEvent().contains(stepA_.getSuccessEvent()) );
	}
	
	@Test
	public void sameStepParent() {
		BFlow parent = new BFlow(){
			@Override
			public void runBFlow() throws InterruptedException { /* dummy */ }
		};
		BStep stepA = new BStep("A");
		BStep stepA_ = new BStep("A");
		
		stepA.setParent(parent);
		stepA_.setParent(parent);
		
		assertTrue( stepA.getStartEvent().contains(stepA.getStartEvent()) );
		assertTrue( stepA.getStartEvent().contains(stepA_.getStartEvent()) );
		assertTrue( stepA_.getStartEvent().contains(stepA.getStartEvent()) );
		
		assertFalse( stepA.getStartEvent().contains(stepA_.getSuccessEvent()) );
	}
	
	@Test
	public void nullParentAsWildCard() {
		BFlow parent = new BFlow(){
			@Override
			public void runBFlow() throws InterruptedException { /* dummy */ }
		};
		BStep stepA = new BStep("A");
		BStep stepA_noParent = new BStep("A");
		
		stepA.setParent(parent);
		
		assertTrue( stepA.getStartEvent().contains(stepA.getStartEvent()) );
		assertFalse( stepA.getStartEvent().contains(stepA_noParent.getStartEvent()) );
		assertFalse( stepA.getSuccessEvent().contains(stepA_noParent.getSuccessEvent()) );
		assertFalse( stepA.getFailureEvent().contains(stepA_noParent.getFailureEvent()) );
		assertTrue( stepA_noParent.getStartEvent().contains(stepA.getStartEvent()) );
		assertTrue( stepA_noParent.getStartEvent().contains(stepA.getStartEvent()) );
		assertTrue( stepA_noParent.getSuccessEvent().contains(stepA.getSuccessEvent()) );
		assertTrue( stepA_noParent.getFailureEvent().contains(stepA.getFailureEvent()) );
		
		assertFalse( stepA.getStartEvent().contains(stepA_noParent.getSuccessEvent()) );
	}
}
