package bp.bflow.samples.highlevel.sanitycheck;

import static org.junit.Assert.*;

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
import bp.validation.eventpattern.*;

public class SanityCheckRunner {
	
	@Test
	public void runTest() throws InterruptedException {
		BProgram aBp = new BProgram();
		
		for ( String s : Arrays.asList("A","B","C","D") ) {
			StepServers.prepareServerFor( aBp, new BStep(s) );
		}

		final CountDownLatch waiter = new CountDownLatch(1);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start( getBProgram() );
				bpExec( new SanityCheckBActivity(), broadcast(EventHistory.getShutdownEvent())  );
				waiter.countDown();
			}
		}.startBThread(aBp);
		
		waiter.await();
		
		List<Event> events = EventHistory.historian(aBp)
									.select(bp.eventSets.EventSetConstants.all)
									.direction(Direction.OLD_FIRST)
									.getEvents();
		
		EventPattern pat = new EventPattern();
		pat.append( new BStep("A").getStartEvent() );
		pat.append( new BStep("A").getSuccessEvent() );
		pat.append( new BStep("B").getStartEvent() );
		pat.append( new BStep("B").getSuccessEvent() );
		pat.append( new BStep("C").getStartEvent() );
		pat.append( new BStep("C").getSuccessEvent() );
		pat.append( new BStep("D").getStartEvent() );
		pat.append( new BStep("D").getSuccessEvent() );
		
		assertTrue( pat.matches(events) );
	}
}
