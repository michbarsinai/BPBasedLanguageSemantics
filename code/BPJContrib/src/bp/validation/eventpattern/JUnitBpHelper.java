package bp.validation.eventpattern;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.contrib.NamedEvent;
import bp.contrib.eventhistory.EventHistory;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.eventSets.EventSetInterface;
import bp.exceptions.BPJRequestableSetException;

public abstract class JUnitBpHelper {
	
	public void runTest() throws InterruptedException {
		BProgram bp = new BProgram();
		
		EventHistory.start(bp);
		setupAndRun( bp );
		
		// This is the Idle event pattern.
		final CountDownLatch bpDone = new CountDownLatch(1); // TODO use the BpIdleLatch.
		BThread idleEventBt = new BThread() {
			@Override
			public void runBThread() throws InterruptedException,
					BPJRequestableSetException {
				getBProgram().bSync( new NamedEvent("DONE"), none, none);
				bpDone.countDown();
			}
		};
		bp.add( idleEventBt, Double.MAX_VALUE);
		idleEventBt.startBThread();
		
		bpDone.await();
		
		// List the events. Note that the normal order is recent-to-old, so we reverse it.
		List<Event> events = EventHistory.historian(bp)
									  	 .select( getEventFilter() )
									  	 .direction( Direction.OLD_FIRST )
									  	 .getEvents();
		// shutdown history recording
		EventHistory.shutdown(bp);
		
		if ( isPrintEvents() ) {
			for ( Event e: events ) {
				System.out.println(":- " + e);
			}
		}
		
		runAssertions( events );
	}

	protected abstract void runAssertions(List<Event> events);

	private EventSetInterface getEventFilter() {
		return all;
	}

	private boolean isPrintEvents() {
		return true;
	}

	protected abstract void setupAndRun(BProgram bp);	
}
