package bp.bflow.samples.highlevel.basicfork;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;

import static bp.eventSets.EventSetConstants.all;
import static org.junit.Assert.assertTrue;

public class BasicForkRunner {
	
	@Test
	public void runTest() throws InterruptedException {
		BProgram bp = new BProgram();
		
		for ( String s : Arrays.asList("a0","b0","b1","b2","c0") ) {
			StepServers.prepareServerFor(bp, new BStep(s) );
		}
		
		final CountDownLatch finishSignal = new CountDownLatch( 1 );
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start(getBProgram());
				bpExec( new BasicForkBActivity(),
						broadcast( EventHistory.getShutdownEvent() ) );
				
				finishSignal.countDown();
				
			}
			
		}.startBThread( bp );
		
		finishSignal.await();
		
		for ( int i : Arrays.asList(0,1,2) ) {
			EventPattern ep = new EventPattern();
			ep.append( new BStep("a0").getStartEvent() );
			ep.append( new BStep("a0").getSuccessEvent() );
			ep.appendStar( all );
			ep.append( new BStep("b" + i).getStartEvent() );
			ep.appendStar( all );
			ep.append( new BStep("b" + i).getSuccessEvent() );
			ep.appendStar( all );
			ep.append( new BStep("c0").getStartEvent() );
			ep.append( new BStep("c0").getSuccessEvent() );
			
			assertTrue( "Assertion failed on path containing event b" + i,
					    ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)) );
		}
		
	}
}
