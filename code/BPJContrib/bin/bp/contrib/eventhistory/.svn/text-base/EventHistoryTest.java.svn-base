package bp.contrib.eventhistory;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.eventSets.RequestableInterface;

public class EventHistoryTest {

	@Before
	public void setUp() throws Exception {
	}
	
	@Test
	public void testHistorian() throws InterruptedException {
		
		final CountDownLatch junitTestBlock = new CountDownLatch(1);
		final List<Event> events = new ArrayList<Event>(10);
		for ( char c='a'; c<'k'; c++ ) {
			events.add( new Event(Character.toString(c) ) );
		}
		
		BProgram bp = new BProgram();
		
		BThread bt = new BThread(){

			@Override
			public void runBThread() throws InterruptedException {
				for ( Event e : events ) {
					getBProgram().bSync(e, none, none );
				}
				junitTestBlock.countDown();
		}};
		
		EventHistory.start(bp);
		
		bp.add(bt, 1d);
		
		bt.startBThread();

		junitTestBlock.await();
		
		List<EventOccurrence> actual = EventHistory.historian( bp ).select( all )
															   .direction( Direction.OLD_FIRST )
															   .go();
		assertEquals( events.size(), actual.size() );
		
		Iterator<Event> eventItr = events.iterator();
		for ( EventOccurrence efr : actual ) {
			RequestableInterface event = efr.getEvent();
			assertEquals( event, eventItr.next() );
		}
		
		EventHistory.shutdown(bp);
			
	}
	
	@Test
	public void testEventMultiBPs() {
		BProgram bp1 = new BProgram();
		BProgram bp2 = new BProgram();
		
		EventHistory.start(bp1);
		EventHistory.start(bp2);
		
		EventHistorian h1 = EventHistory.historian(bp1);
		EventHistorian h2 = EventHistory.historian(bp2);
		
		assertFalse( h1.equals(h2) );
		assertFalse( h2.equals(h1) );
		
		EventHistory.shutdown(bp1);
		EventHistory.shutdown(bp2);
		
	}
}
