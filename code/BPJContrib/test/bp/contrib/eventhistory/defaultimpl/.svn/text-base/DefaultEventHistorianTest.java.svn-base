package bp.contrib.eventhistory.defaultimpl;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bp.Event;
import bp.contrib.eventhistory.EventOccurrence;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

public class DefaultEventHistorianTest {
	
	DefaultEventHistoryWriter sut;
	BooleanEventFilter positiveEvents = new BooleanEventFilter();
	
	@Before
	public void setUp() {
		sut = new DefaultEventHistoryWriter();
		sut.setup();
		BooleanEvent.COUNT=0;
	}

	@Test
	public void testSelectFirst() {
		// first is first event to happen
		BooleanEvent[] evts = {new BooleanEvent(true), new BooleanEvent(true), new BooleanEvent(false)};
		recordEvents( evts );
		RequestableInterface actual = sut.getHistorian().selectFirst( positiveEvents ).getEvent();
		assertSame( evts[0], actual );
		
		// cleanup
		setUp();
		
		// first is second event to happen
		evts = new BooleanEvent[]{new BooleanEvent(false), new BooleanEvent(true), new BooleanEvent(false)};
		recordEvents( evts );
		actual = sut.getHistorian().selectFirst( positiveEvents ).getEvent();
		assertSame( evts[1], actual );
	}

	@Test
	public void testSelectRecent() {
		// recent is last event to happen
		BooleanEvent[] evts = {new BooleanEvent(true), new BooleanEvent(false), new BooleanEvent(true)};
		recordEvents( evts );
		RequestableInterface actual = sut.getHistorian().selectRecent( positiveEvents ).getEvent();
		assertSame( evts[2], actual );
		
		// cleanup
		setUp();
		
		// recent is penultimate event to happen
		evts = new BooleanEvent[]{new BooleanEvent(false), new BooleanEvent(true), new BooleanEvent(false)};
		recordEvents( evts );
		actual = sut.getHistorian().selectFirst( positiveEvents ).getEvent();
		assertSame( evts[1], actual );
	}

	@Test
	public void testSelectSimple() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[0], evts[2], evts[3], evts[5]);
		Collections.reverse(expected); // default direction is backwards.
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents ).go() );
		
	}
	
	@Test
	public void testSelectReverseDirection() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[0], evts[2], evts[3], evts[5]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .direction(Direction.OLD_FIRST)
								  	 .go() 
								);
		
	}
	
	@Test
	public void testSelectOffset() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[2], evts[0]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .offset(2)
								  	 .go() 
								);		
	}
	
	
	@Test
	public void testSelectOffsetOvershoot() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		
		assertEquals( 0, sut.getHistorian().select( positiveEvents )
							.offset(2000)
							.go().size() );
	}
	
	@Test
	public void testSelectLimit() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[5], evts[3]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .limit(2)
								  	 .go() 
								);
	}
	
	@Test
	public void testSelectLimitOvershoot() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[5], evts[3],evts[2],evts[0]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .limit(20000)
								  	 .go() 
								);
		
	}
	
	@Test
	public void testSelectLimitAndOffset() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[3], evts[2]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .limit(2)
								  	 .offset(1)
								  	 .go() 
								);
	}
	
	@Test
	public void testSelectAll() {
		BooleanEvent[] evts = {new BooleanEvent(true),
							   new BooleanEvent(false), 
							   new BooleanEvent(true), 
							   new BooleanEvent(true), 
							   new BooleanEvent(false), 
							   new BooleanEvent(true)};
		recordEvents( evts );
		List<BooleanEvent> expected = Arrays.asList(evts[2], evts[3]);
		
		assertSameEventsAndOrder( expected,
								  sut.getHistorian().select( positiveEvents )
								  	 .limit(2)
								  	 .offset(1)
								  	 .direction(Direction.OLD_FIRST)
								  	 .go() 
								);
		
	}
	
	/**
	 * Asserts that all the events in the EventFireRecords are the same as those in the event list,
	 * and that they appear in the same order.
	 * @param evts
	 * @param recs
	 */
	void assertSameEventsAndOrder( List<BooleanEvent> evts, List<EventOccurrence> recs ) {
		assertEquals("Different sizes: evts:" + evts.size() + " recs:" + recs.size(), evts.size(), recs.size());
		
		for ( int offset=0; offset<evts.size(); offset++ ) {
			assertSame("Not same event at offset " + offset, evts.get(offset), recs.get(offset).getEvent() );
		}
	}
	
	void recordEvents( Event[] arr ) {
		for ( Event e : arr ) {
			sut.recordEvent(e);
		}
	}

}

class BooleanEventFilter implements EventSetInterface {

	@Override
	public boolean contains(Object o) {
		if ( o instanceof BooleanEvent ) {
			return ((BooleanEvent)o).isB();
		}
		return false;
	}
	
}

class BooleanEvent extends Event {
	static int COUNT=0;
	private boolean b;
	private int id;
	
	public BooleanEvent( boolean aB ) {
		b = aB;
		id = ++COUNT;
	}
	
	public boolean isB() {
		return b;
	}

	@Override
	public String toString() {
		return "BooleanEvent [b=" + b + ", id=" + id + "]";
	}
	
	
}
