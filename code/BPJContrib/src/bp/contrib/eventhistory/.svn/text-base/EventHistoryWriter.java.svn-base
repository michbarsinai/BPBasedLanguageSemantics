package bp.contrib.eventhistory;

import bp.eventSets.RequestableInterface;

/**
 * Service provider interface (SPI) for the EventHistory package. People that want to record the event history
 * in a different way than the default implementation, need to implement this interface and then install
 * an instance of it (see {@link EventHistory} for details).
 * 
 * @author michaelbar-sinai
 */
public interface EventHistoryWriter {


	/**
	 * Hook method. Called by {@link EventHistory} after construction.
	 * Allocate any resources here.
	 */
	public void setup();
	
	/**
	 * @return An event historian that reads the history this writer writes.
	 */
	public EventHistorian getHistorian();
	
	/**
	 * Records a single event being fired. Called by {@link EventHistory}'s BThread
	 * after any event is fired.
	 * @param anEvent the event that was just fired.
	 */
	public void recordEvent( RequestableInterface anEvent );
	
	/**
	 * Hook method. Called by {@link EventHistory} after shutdown.
	 * Do your cleanup here.
	 */
	public void shutdown();
}
