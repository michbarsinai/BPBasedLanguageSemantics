package bp.bflow.core.dslhelpers;

import bp.bflow.core.BFlow;
import bp.eventSets.EventSetInterface;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.BooleanComposableEventSet.*;

/**
 * Waits for an event to be fired.
 * 
 * @author michaelbar-sinai
 */
public class EventWaiter extends BFlow {
	
	private EventSetInterface event;
	
	public EventWaiter( EventSetInterface e ) {
		event = e;
	}
	
	@Override
	public void runBFlow() throws InterruptedException {
		
		getBProgram().bSync(none, theEventSet( getExceptingEvents() ).or( event ), getTerminatedEvent() );
		assertNoExceptingEvents( getBProgram().lastEvent );
		
	}

	public EventSetInterface getEvent() {
		return event;
	}
	
}
