package bp.contrib.eventhistory;

import bp.eventSets.EventSetInterface;

/**
 * <p>
 * Interface for an object that records all the fired events and 
 * allows client code to query them. Obtaining an instance should
 * normally be done by invoking {@link EventHistory#historian()}.
 * </p><p>
 * {@link EventHistory} obtains a single instance of this interface, before
 * the first event is fired, and keeps it for the entire BPJ run.
 * <p></p>
 * To install your own historian, implement this interface and either:
 * <ul>
 * 	<li>Call {@link EventHistory#installEventHistorian(EventHistorian)}
 * 		before the first event is fired, or</li>
 *	<li>Start the JVM with the environment variable "bp.contrib.eventhistory.EventHistorian"
 *		set to your historian's fully qualified class name. If you use this approach,
 *		<em>your EventHistorian should have a no-args constructor.</em></li>
 * </ul>
 * </p>
 * 
 * @see EventHistory
 * 
 * @author michaelbar-sinai
 */
public interface EventHistorian {
	
	/**
	 * Returns the first event (in chronological order) that was fired and
	 * that the passed {@link EventSetInterface#contains(Object)}.<br />
	 * A convenience method for <code>historian.select(aFilter).direction(OLD).go().get(0)</code>
	 * @param aFilter Filters all the events until one is found.
	 * @return the event and the time it was fired, wrapped in a {@link EventOccurrence}.
	 */
	public EventOccurrence selectFirst( EventSetInterface aFilter );
	
	/**
	 * Returns the most recent event that was fired and
	 * that the passed {@link EventSetInterface#contains(Object)}.<br />
	 * A convenience method for <code>historian.select(aFilter).go().get(0)</code>
	 * @param aFilter Filters all the events until one is found.
	 * @return the event and the time it was fired, wrapped in a {@link EventOccurrence}.
	 */
	public EventOccurrence selectRecent( EventSetInterface aFilter );

	/**
	 * Starts an SQL-like call sequence that allows client code to 
	 * query the fired events. The method returns an {@link EventHistoryQuery}
	 * object that allows some query refinements. 
	 * 
	 * @param aFilter Filters the events the returned query will contain
	 * @return an {@code EvenyHistoryQuery}
	 */
	public EventHistoryQuery select( EventSetInterface aFilter );
	
}
