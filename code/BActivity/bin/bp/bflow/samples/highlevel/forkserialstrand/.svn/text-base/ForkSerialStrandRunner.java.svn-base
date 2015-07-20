package bp.bflow.samples.highlevel.forkserialstrand;

import static bp.eventSets.EventSetConstants.all;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.eventhistory.EventHistory;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.validation.eventpattern.EventPattern;

public class ForkSerialStrandRunner {
	
	@Test
	public void runTest() throws InterruptedException {
		BProgram bp = new BProgram();
		
		final CountDownLatch cld = new CountDownLatch(1);
		
		for (String s : Arrays.asList("a0", "b0", "b1", "c1","b2", "c2", "d2", "z0")) {
			StepServers.prepareServerFor(bp, new BStep(s));
		}
		
		new BFlowBatch(){

			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start( getBProgram() );
				bpExec( new ForkSerialStrandBActivity(),
						broadcast(EventHistory.getShutdownEvent()));
				
				cld.countDown();
				
			}}.startBThread(bp);
		

		cld.await();
		
		List<Event> history = EventHistory.historian(bp).select(all).direction(Direction.OLD_FIRST).getEvents();
		
		// we do a separate glob per path on the run map of the bflow.
		EventPattern gp = new EventPattern();
		gp.append( new BStep("a0").getStartEvent() )
		  .append( new BStep("a0").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("b0").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("b0").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("z0").getStartEvent() )
		  .append( new BStep("z0").getSuccessEvent() )
		  .appendStar( all );
		
		assertTrue( gp.matches(history) );
		
		gp = new EventPattern();
		gp.append( new BStep("a0").getStartEvent() )
		  .append( new BStep("a0").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("b1").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("b1").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("c1").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("c1").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("z0").getStartEvent() )
		  .append( new BStep("z0").getSuccessEvent() )
		  .appendStar( all );
		
		assertTrue( gp.matches(history) );
		
		gp = new EventPattern();
		gp.append( new BStep("a0").getStartEvent() )
		  .append( new BStep("a0").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("b2").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("b2").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("c2").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("c2").getSuccessEvent() )
		  .appendStar( all )		 
		  .append( new BStep("d2").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("d2").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("z0").getStartEvent() )
		  .append( new BStep("z0").getSuccessEvent() )
		  .appendStar( all );
		
		assertTrue( gp.matches(history) );
		
	}
}
