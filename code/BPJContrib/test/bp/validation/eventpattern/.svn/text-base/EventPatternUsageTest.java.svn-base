package bp.validation.eventpattern;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.contrib.NamedEvent;
import bp.contrib.eventhistory.EventHistory;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.exceptions.BPJRequestableSetException;
import static bp.eventSets.EventSetConstants.*;
import static org.junit.Assert.*;

public class EventPatternUsageTest {
	
	@Test
	public void sampleTest() throws InterruptedException {
		
		// sample program setup
		BThread testThread1 = new BThread() {
			@Override
			public void runBThread() throws InterruptedException,
					BPJRequestableSetException {
				getBProgram().bSync( new NamedEvent("A"), none, none);
				getBProgram().bSync( new NamedEvent("B"), none, none);
				getBProgram().bSync( none, new NamedEvent("2"), none);
				getBProgram().bSync( new NamedEvent("C"), none, none);
			}
		};
		BThread testThread2 = new BThread() {
			@Override
			public void runBThread() throws InterruptedException,
					BPJRequestableSetException {
				getBProgram().bSync( new NamedEvent("1"), none, none);
				getBProgram().bSync( new NamedEvent("2"), none, none);
				getBProgram().bSync( new NamedEvent("3"), none, none);
			}
		};
		
		
		// This is the Idle event pattern.
		final CountDownLatch bpDone = new CountDownLatch(1);
		BThread idleEventBt = new BThread() {
			@Override
			public void runBThread() throws InterruptedException,
					BPJRequestableSetException {
				getBProgram().bSync( new NamedEvent("DONE"), none, none);
				bpDone.countDown();
			}
		};
		
		// setting up the BProgram
		BProgram bp = new BProgram();
		bp.add(testThread1, 1.0);
		bp.add(testThread2, 1.1);
		bp.add( idleEventBt, 100d );
		bp.setBThreadEpsilon(1);
		
		// start recording history
		EventHistory.start(bp);
		
		// go BProgram, wait test.
		testThread1.startBThread();
		testThread2.startBThread();
		idleEventBt.startBThread();
		bpDone.await();
		
		// shutdown history recording
		EventHistory.shutdown(bp);
		
		// List the events. Note that the normal order is recent-to-old, so we reverse it.
		List<Event> events = EventHistory.historian(bp)
									  	 .select( all ).direction( Direction.OLD_FIRST ).getEvents();
		
		for ( Event e: events ) {
			System.out.println(":- " + e);
		}
		
		// now for the assertions.
		assertTrue( "testThread1 failed",
				    new EventPattern().appendStar( all )
					.append( new NamedEvent("A")).appendStar( all )
					.append( new NamedEvent("B")).appendStar( all )
					.append( new NamedEvent("C")).appendStar( all )
				    .matches(events));
		assertTrue( "testThread2 failed",
			    new EventPattern().appendStar( all )
				.append( new NamedEvent("1")).appendStar( all )
				.append( new NamedEvent("2")).appendStar( all )
				.append( new NamedEvent("3")).appendStar( all )
			    .matches(events));
		assertFalse( "C fired before 2",
			    new EventPattern().appendStar( all )
				.append( new NamedEvent("C")).appendStar( all )
				.append( new NamedEvent("2")).appendStar( all )
			    .matches(events));
	}
}
