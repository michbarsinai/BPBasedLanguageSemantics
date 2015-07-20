package bp.contrib.eventhistory.defaultimpl;

import java.util.LinkedList;

import bp.contrib.eventhistory.EventOccurrence;
import bp.contrib.eventhistory.EventHistorian;
import bp.contrib.eventhistory.EventHistoryWriter;
import bp.eventSets.RequestableInterface;

/**
 * Default implementation of the {@link EventHistoryWriter} interface. Records events
 * in memory. The number of recorded events is unlimited, so for very eventful runs might
 * exhaust the RAM. 
 *  
 * @author michaelbar-sinai
 */
public class DefaultEventHistoryWriter implements EventHistoryWriter {
	
	
	// TODO replace with a data structure that would be easier on the GC.
	private LinkedList<EventOccurrence> history = new LinkedList<EventOccurrence>();

	@Override
	public void recordEvent(RequestableInterface anEvent) {
		history.add( new DefaultEventFireRecord(System.currentTimeMillis(), anEvent) );
	}

	@Override
	public void setup() {}

	@Override
	public void shutdown() {}

	@Override
	public EventHistorian getHistorian() {
		return new DefaultEventHistorian( history );
	}

}
