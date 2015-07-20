package bp.bflow.samples.highlevel.combinations;

import java.util.Arrays;
import static bp.eventSets.EventSetConstants.all;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;

public class CombiningBActivityRunner {
	
	@Test
	public void runTest() throws InterruptedException {
		// setup
		BProgram bp = new BProgram();
		for ( String name : Arrays.asList("1A","2A","2B0","2B1") )  {
			StepServers.prepareServerFor(bp, new BStep(name));
		}
		
		final CountDownLatch endSignal = new CountDownLatch(1);
		new BFlowBatch(){
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start( getBProgram() );
				bpExec( new CombiningBActivity(), broadcast( EventHistory.getShutdownEvent()) );
				endSignal.countDown();
				
		}}.startBThread(bp);
		
		endSignal.await();
		
		List<Event> history = EventHistoryBFlowTestHelper.getStepEventHistory(bp);
		
		assertTrue( "flow 1 did not run",
				new EventPattern().appendStar(all)
								  .append( new BStep("1A").getStartEvent())
								  .appendStar(all)
								  .appendStar( new BStep("1A").getSuccessEvent())
								  .appendStar(all)
								  .matches(history));
		
		assertTrue( "flow 2 did not run",
				new EventPattern().appendStar(all)
								  .append( new BStep("2A").getStartEvent())
								  .appendStar(all)
								  .appendStar( new BStep("2A").getSuccessEvent())
								  .appendStar(all)
								  .append( new BStep("2B0").getStartEvent())
								  .appendStar(all)
								  .appendStar( new BStep("2B0").getSuccessEvent())
								  .appendStar(all)
								  .append( new BStep("2B1").getStartEvent())
								  .appendStar(all)
								  .appendStar( new BStep("2B1").getSuccessEvent())
								  .appendStar(all)
								  .matches(history)
					||
					new EventPattern().appendStar(all)
					  .append( new BStep("2A").getStartEvent())
					  .appendStar(all)
					  .appendStar( new BStep("2A").getSuccessEvent())
					  .appendStar(all)
					  .append( new BStep("2B1").getStartEvent())
					  .appendStar(all)
					  .appendStar( new BStep("2B1").getSuccessEvent())
					  .appendStar(all)
					  .append( new BStep("2B0").getStartEvent())
					  .appendStar(all)
					  .appendStar( new BStep("2B0").getSuccessEvent())
					  .appendStar(all)
					  .matches(history));
		
	}
}
