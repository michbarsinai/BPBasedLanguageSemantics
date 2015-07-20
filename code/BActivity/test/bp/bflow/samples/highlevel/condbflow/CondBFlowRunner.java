package bp.bflow.samples.highlevel.condbflow;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.BThread;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BFlowUnit;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.UniversalBStepServer;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;

import static bp.eventSets.EventSetConstants.all;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
public class CondBFlowRunner {
	
	Set<String> stepSet = new HashSet<String>( Arrays.asList("A","B","OTHER") );
	
	public void testForStep( String selectedStepName ) throws InterruptedException {
		final BFlowUnit sut = CondBFlowFlows.makeFlow( selectedStepName );
		
		final CountDownLatch flowDone = new CountDownLatch(1);
		final BProgram bprog = new BProgram();
		
		BThread uss = new UniversalBStepServer();
		bprog.add(uss, 700d);
		uss.startBThread();
		EventHistory.start(bprog);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				bpExec( sut,
						broadcast(UniversalBStepServer.TERMINATE),
						broadcast(EventHistory.getShutdownEvent()));
				flowDone.countDown();
			}
		}.startBThread(bprog);
		
		flowDone.await();
		
		EventHistoryBFlowTestHelper.dumpEventHistory(bprog);
		
		EventPattern ep = new EventPattern();
		ep.appendStar( all );
		ep.append( new BStep( selectedStepName ).getStartEvent() );
		ep.appendStar( all );
		
		assertTrue( "Step " + selectedStepName+ " was not executed",
				ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bprog)) );
		
		
		Set<String> remaining = new HashSet<String>(stepSet);
		remaining.remove(selectedStepName);
		for ( String stepName : remaining ) {
			ep = new EventPattern();
			ep.appendStar( all );
			ep.append( new BStep(stepName).getStartEvent() );
			ep.appendStar( all );
			assertFalse( "Step " + remaining + " was wrongly executed",
					ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bprog)) );
		}
	}
	
	@Test
	public void testFirstOption() throws InterruptedException {
		testForStep("A");
	}
	@Test
	public void testSecondOption() throws InterruptedException {
		testForStep("B");
	}
	@Test
	public void testElseOption() throws InterruptedException {
		testForStep("OTHER");
	}
}
