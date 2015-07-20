package bp.bflow.samples.highlevel.utils;

import java.util.List;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.events.BStepEvent;
import bp.contrib.eventhistory.EventHistory;
import bp.contrib.eventhistory.EventHistoryQuery.Direction;

/**
 * Place to put utility methods that deal with events and bsteps.
 * 
 * @author michaelbar-sinai
 */
public class EventHistoryBFlowTestHelper {
	
	public static List<Event> getStepEventHistory( BProgram bp ) {
		return EventHistory.historian(bp)
					.select( BStepEvent.ALL_BSTEP_EVENTS )
					.direction(Direction.OLD_FIRST).getEvents();
	}
	
	public static void dumpEventHistory( BProgram bp ) {
		for ( Event e : getStepEventHistory(bp) ) {
			System.out.println( "* " + e );
		}
	}
}
