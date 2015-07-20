package bp.contrib.eventhistory;

import java.util.List;

import bp.Event;

/**
 * A query for the event history. Allows the client code to specify
 * which events it wants. The refinement methods return {@code this}
 * object, so one can chain calls, to get a "DSL" feel. When the query is
 * refined enough, call go(); 
 * 
 * @author michaelbar-sinai
 */
public interface EventHistoryQuery {
	
	/**
	 * Defines the direction in which the history will be scanned.
	 * 
	 * @author michaelbar-sinai
	 */
	public enum Direction { 
		/** 
		 * Scan the history from the most recent event, backwards.
		 * Returned results will be in reverse order, i.e. most
		 * recent event at index 0.
		 */
		RECENT_FIRST,
		
		/**
		 * Scan the history in chronological order. 
		 * Oldest event will be at index 0.
		 */
		OLD_FIRST 
	}
	
	/**
	 * Same as SQL's {@code OFFSET}.
	 * @param anAmount amount of matching events the {@link EventHistorian} should skip.
	 * 		  		   Passing {@code null} is the same as not calling this method. 
	 * @return {@code this}, to allow method call chaining.
	 */
	public EventHistoryQuery offset( Integer anAmount );
	
	/**
	 * Same as SQL's {@code LIMIT} - don't return more than {@code anAmount} results.
	 * @param anAmount maximum amount of records in the returned list.
	 * 	      		   Passing {@code null} is the same as not calling this method. 
	 * @return {@code this}, to allow method call chaining.
	 */		
	public EventHistoryQuery limit( Integer anAmount );
	
	/**
	 * Sets the direction of the scan and the list. When omitted,
	 * the query defaults to {@link Direction#RECENT_FIRST}.
	 * @param aDirection
	 * @return {@code this}, to allow method call chaining.
	 */
	public EventHistoryQuery direction( Direction aDirection );
	
	/**
	 * Executes the query, and returns the results.
	 * @return List of events and the time they were fired.
	 */
	public List<EventOccurrence> go();
	
	/**
	 * Executes the query and returns the events (rather than the event occurrences).
	 * @return List of events
	 */
	public List<Event> getEvents();
	
}