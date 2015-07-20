package bp.contrib.eventhistory;

import java.io.Serializable;

import bp.eventSets.RequestableInterface;

/**
 * Records a single {@link Event} - or, rather, a single 
 * instance of {@link RequestableInterface}, being fired.
 * Holds the event fired and the system time.<br/>
 * This object is {@link Serializable}, providing that the event is 
 * serializable too.
 * 
 * @author michaelbar-sinai
 */
public interface EventOccurrence extends Serializable {
	
	/**
	 * @return the time this event was fired.
	 */
	public long getTime();
	
	/**
	 * @return the event that was fired.
	 */
	public RequestableInterface getEvent();

}
