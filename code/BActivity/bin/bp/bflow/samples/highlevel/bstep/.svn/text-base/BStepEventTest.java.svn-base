package bp.bflow.samples.highlevel.bstep;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.BeforeClass;
import org.junit.Test;

import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.events.BStepEvent;

public class BStepEventTest {
	
	static BFlowBatch bact;
	
	@BeforeClass
	public static void setup() {
		bact = new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {}
		};
	}
	
	@Test
	public void testContains() {
		
		BStep a = new BStep(bact, "A");
		BStep a_ = new BStep(bact, "A");
		
		// see that containment is reflective and symmetric.
		assertTrue( a.getStartEvent().contains(a_.getStartEvent()) );
		assertTrue( a.getSuccessEvent().contains(a_.getSuccessEvent()) );
		assertTrue( a_.getStartEvent().contains(a_.getStartEvent()) );
		assertTrue( a_.getSuccessEvent().contains(a_.getSuccessEvent()) );
		assertTrue( a_.getStartEvent().contains(a.getStartEvent()) );
		assertTrue( a_.getSuccessEvent().contains(a.getSuccessEvent()) );
		
		assertFalse( a.getStartEvent().contains(a.getSuccessEvent()) );
		assertFalse( a.getSuccessEvent().contains(a.getStartEvent()) );
		assertFalse( a.getStartEvent().contains(a_.getSuccessEvent()) );
		assertFalse( a.getSuccessEvent().contains(a_.getStartEvent()) );
	}
	
	@Test
	public void testContainsWithValues() {
		BStep a = new BStep(bact, "A");
		BStep a_ = new BStep(bact, "A");
		
		BStepEvent aStartReq = a.getStartEvent("X");
		BStepEvent aEndRequest = a.getSuccessEvent("X");
		BStepEvent a_EndRequest = a_.getSuccessEvent();
		BStepEvent a_StartRequest = a_.getStartEvent();

		// see that containment is reflective and symmetric,
		// and does not care about the value.
		assertTrue( aStartReq.contains(a_StartRequest) );
		assertTrue( aEndRequest.contains(a_EndRequest) );
		assertTrue( a_StartRequest.contains(a_StartRequest) );
		assertTrue( a_EndRequest.contains(a_EndRequest) );
		assertTrue( a_StartRequest.contains(aStartReq) );
		assertTrue( a_EndRequest.contains(aEndRequest) );
		
		assertFalse( aStartReq.contains(aEndRequest) );
		assertFalse( aEndRequest.contains(aStartReq) );
		assertFalse( aStartReq.contains(a_EndRequest) );
		assertFalse( aEndRequest.contains(a_StartRequest) );
	}
	
	@Test
	public void testNotContains() {
		BStep a = new BStep(bact, "A");
		BStep b = new BStep(bact, "B");
		
		assertFalse( a.getStartEvent().contains(b.getStartEvent()) );
		assertFalse( a.getSuccessEvent().contains(b.getSuccessEvent()) );
		assertFalse( b.getStartEvent().contains(a.getStartEvent()) );
		assertFalse( b.getSuccessEvent().contains(a.getSuccessEvent()) );
		
		assertFalse( a.getStartEvent().contains(a.getSuccessEvent()) );
		assertFalse( a.getSuccessEvent().contains(a.getStartEvent()) );
		assertFalse( a.getStartEvent().contains(b.getSuccessEvent()) );
		assertFalse( a.getSuccessEvent().contains(b.getStartEvent()) );
	}

}
