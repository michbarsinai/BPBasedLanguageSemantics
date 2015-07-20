package bp.contrib.eventhistory.defaultimpl;

import java.util.List;

import bp.Event;
import bp.contrib.eventhistory.EventOccurrence;
import bp.contrib.eventhistory.EventHistoryQuery;
import bp.eventSets.EventSetInterface;

/**
 * Default implementation of the {@link EventHistoryQuery} interface.
 * Can be re-used if the generating {@link EventHistorian} class implements 
 * {@link DefaultEventHistoryQueryCallback} interface.
 * 
 * @author michaelbar-sinai
 */
public class DefaultEventHistoryQuery implements EventHistoryQuery {
	
	public interface DefaultEventHistoryQueryCallback {
		public List<EventOccurrence> go(DefaultEventHistoryQuery q );
		public List<Event> getEvents(DefaultEventHistoryQuery q );
	}
	
	private Integer offset;
	private Integer limit;
	private Direction direction = Direction.RECENT_FIRST;
	private EventSetInterface filter;
	
	private DefaultEventHistoryQueryCallback callback;
	
	public DefaultEventHistoryQuery( EventSetInterface aFilter,
									 DefaultEventHistoryQueryCallback aCallback ) {
		callback = aCallback;
		filter = aFilter;
	}
	
	@Override
	public EventHistoryQuery offset(Integer anAmount) {
		if ( anAmount < 0 ) throw new IllegalArgumentException("Offset cannot be negative (passed value: " + anAmount +")" );
		offset = anAmount;
		return this;
	}

	@Override
	public EventHistoryQuery limit(Integer anAmount) {
		if ( anAmount < 0 ) throw new IllegalArgumentException("Limit cannot be negative (passed value: " + anAmount +")" );
		limit = anAmount;
		return this;
	}

	@Override
	public EventHistoryQuery direction(Direction aDirection) {
		direction = aDirection;
		return this;
	}

	@Override
	public List<EventOccurrence> go() {
		return callback.go( this );
	}

	@Override
	public List<Event> getEvents() {
		return callback.getEvents( this );
	}
	
	public Integer getOffset() {
		return offset;
	}

	public Integer getLimit() {
		return limit;
	}

	public Direction getDirection() {
		return direction;
	}
	
	public EventSetInterface getFilter() {
		return filter;
	}
	
}
