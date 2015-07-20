package bp.bflow.utils;

import java.util.Comparator;

import bp.Event;

/**
 * Creates a natural order over Events. Used to allow
 * usage of order-based collections.
 * <br />
 * This class is stateless, so we use a singleton pattern, as there is
 * no point in having more than a single instance.
 * 
 * @author michaelbar-sinai
 */
public class EventComparator implements Comparator<Event> {
	
	public static final EventComparator INSTANCE = new EventComparator();
	
	private EventComparator(){}
	
	@Override
	public int compare(Event o1, Event o2) {
		return o1.hashCode() - o2.hashCode();
	}

}
