package bp.contrib.eventhistory.defaultimpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import bp.Event;
import bp.contrib.eventhistory.EventOccurrence;
import bp.contrib.eventhistory.EventHistorian;
import bp.contrib.eventhistory.EventHistoryQuery;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;
import bp.contrib.eventhistory.defaultimpl.DefaultEventHistoryQuery.DefaultEventHistoryQueryCallback;
import bp.eventSets.EventSetInterface;

/**
 * Reads the history that the {@link DefaultEventHistoryWriter} writes.
 * 
 * 
 * @author michaelbar-sinai
 */
public class DefaultEventHistorian 
		implements EventHistorian, DefaultEventHistoryQueryCallback {
	
	private LinkedList<EventOccurrence> history;
	
	DefaultEventHistorian( LinkedList<EventOccurrence> aHistory ) {
		history = aHistory;
	}
	
	@Override
	public EventOccurrence selectFirst(EventSetInterface aFilter) {
		for ( EventOccurrence efr : history ) {
			if ( aFilter.contains(efr.getEvent())) {
				return efr;
			}
		}
		return null;
	}

	@Override
	public EventOccurrence selectRecent(EventSetInterface aFilter) {
		for ( Iterator<EventOccurrence> it=history.descendingIterator() ; it.hasNext() ; ) {
			EventOccurrence efr = it.next();
			if ( aFilter.contains(efr.getEvent()) ) {
				return efr;
			}
		}
		return null;
	}

	@Override
	public EventHistoryQuery select(EventSetInterface aFilter) {
		return new DefaultEventHistoryQuery( aFilter, this );
	}	

	@Override
	public List<EventOccurrence> go(DefaultEventHistoryQuery q) {
		// decide on direction
		Iterator<EventOccurrence> itr = (q.getDirection()==Direction.RECENT_FIRST) ? 
										history.descendingIterator() :
										history.iterator();
		
		// prepare for work
		List<EventOccurrence> workList;
		int amountLeft = -1;
		if ( q.getLimit() == null ) {
			workList = new LinkedList<EventOccurrence>();
		} else {
			workList = new ArrayList<EventOccurrence>(q.getLimit());
			amountLeft = q.getLimit();
		}
		int offset = (q.getOffset() != null ) ? q.getOffset() : 0;

		// go over the history
		EventSetInterface filter = q.getFilter();
		while ( itr.hasNext()  && (amountLeft!=0) ) {
			EventOccurrence efr = itr.next();
			if ( filter.contains(efr.getEvent()) ) {
				if ( offset > 0 ) {
					offset--;
				} else {
					workList.add(efr);
					amountLeft--;
				}
			}
		}
		
		// return the list (tidied up, if needed)
		if ( workList instanceof LinkedList ) {
			return new ArrayList<EventOccurrence>(workList);
		} else {
			((ArrayList<EventOccurrence>)workList).trimToSize();
			return workList;
		}
	}
	
	@Override
	public List<Event> getEvents(DefaultEventHistoryQuery q) {
		ArrayList<Event> events = new ArrayList<Event>();
		List<EventOccurrence> occs = go( q );
		events.ensureCapacity( occs.size() );
		
		for ( EventOccurrence eo : occs ) {
			events.add( eo.getEvent().getEvent() );
		}
		
		return events;
		
	}
	
	@Override
	public String toString() {
		return "DefaultEventHistorian";
	}



}
