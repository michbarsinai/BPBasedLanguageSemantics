package bp.bflow.samples.highlevel.beeriprintcasestudy.passbyhistory;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.BStepServer;
import bp.bflow.samples.highlevel.utils.DebugStepServer;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;
import static bp.eventSets.EventSetConstants.all;

public class BPrintCaseStudyRunner {
	
	@Test
	public void runTest() throws InterruptedException {
		
		BProgram bProg = new BProgram();
		EventHistory.start(bProg);
		
		final CountDownLatch bflowEnd = new CountDownLatch(1);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				
				bpExec( parallel (
							// Setup step servers 
							new DebugStepServer( new BStep("splitByDistroChannel") ),
							new DebugStepServer( new BStep("EM-1") ),
							new DebugStepServer( new BStep("EM-2") ),
							new DebugStepServer( new BStep("bulk") ),
							new DebugStepServer( new BStep("P1") ),
							new DebugStepServer( new BStep("P2") ),
							new DebugStepServer( new BStep("print") ),
							new DebugStepServer( new BStep("invoice") ),
							
							// Go Bactivity (+cleanup)
							sequence( new BeeriprintPbhCsBActivity(),
									  broadcast(BStepServer.TERMINATE_SERVERS),
									  broadcast(EventHistory.getShutdownEvent()))
				));
				bflowEnd.countDown();
				
			}}.startBThread( bProg );
		
		bflowEnd.await();
		
		List<Event> events = EventHistoryBFlowTestHelper.getStepEventHistory(bProg);
		
		for ( Event e : events ) {
			System.out.println(":- " + e );
		}
		
		// email flow
		assertTrue( "Email flow failed",
					new EventPattern()
					.append( new BStep("splitByDistroChannel").getStartEvent() )
					.append( new BStep("splitByDistroChannel").getSuccessEvent() ).appendStar( all )
					.append( new BStep("EM-1").getStartEvent() ).appendStar( all )
					.append( new BStep("EM-1").getSuccessEvent() ).appendStar( all )
					.append( new BStep("EM-2").getStartEvent() ).appendStar( all )
					.append( new BStep("EM-2").getSuccessEvent() ).appendStar( all )
					.append( new BStep("invoice").getStartEvent() )
					.append( new BStep("invoice").getSuccessEvent() )
					.matches( events ) );
		
		// bulk flow
		assertTrue( "Bulk flow failed",
					new EventPattern()
					.append( new BStep("splitByDistroChannel").getStartEvent() )
					.append( new BStep("splitByDistroChannel").getSuccessEvent() ).appendStar( all )
					.append( new BStep("bulk").getStartEvent() ).appendStar( all )
					.append( new BStep("bulk").getSuccessEvent() ).appendStar( all )
					.append( new BStep("print").getStartEvent() ).appendStar( all )
					.append( new BStep("print").getSuccessEvent() ).appendStar( all )
					.append( new BStep("invoice").getStartEvent() )
					.append( new BStep("invoice").getSuccessEvent() )
					.matches( events ) );
		
		// Postal flow
		assertTrue( "Postal flow failed",
					new EventPattern()
					.append( new BStep("splitByDistroChannel").getStartEvent() )
					.append( new BStep("splitByDistroChannel").getSuccessEvent() ).appendStar( all )
					.append( new BStep("P1").getStartEvent() ).appendStar( all )
					.append( new BStep("P1").getSuccessEvent() ).appendStar( all )
					.append( new BStep("P2").getStartEvent() ).appendStar( all )
					.append( new BStep("P2").getSuccessEvent() ).appendStar( all )
					.append( new BStep("print").getStartEvent() ).appendStar( all )
					.append( new BStep("print").getSuccessEvent() ).appendStar( all )
					.append( new BStep("invoice").getStartEvent() )
					.append( new BStep("invoice").getSuccessEvent() )
					.matches( events ) );
		
		// No invoice until printing is done
		assertFalse( "Invoicing prior to printing",
				new EventPattern()
				.appendStar( all )
				.append( new BStep("invoice").getStartEvent() )
				.appendStar( all )
				.append( new BStep("print").getStartEvent() )
				.appendStar( all )
				.matches( events ) );
	}
}
