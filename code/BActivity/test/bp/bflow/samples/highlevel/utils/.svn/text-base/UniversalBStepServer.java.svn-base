package bp.bflow.samples.highlevel.utils;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.Event;
import bp.bflow.core.BStep;
import bp.bflow.core.eventfilters.BStepEventFilters;
import bp.bflow.core.events.BStepStartEvent;
import bp.exceptions.BPJRequestableSetException;

public class UniversalBStepServer extends BThread {
	
	public static Event TERMINATE = new Event();
	
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		Event lastEvent = null;
		while ( lastEvent != TERMINATE ) {
			getBProgram().bSync(none, all, none);
			lastEvent = getBProgram().lastEvent;
			if ( lastEvent instanceof BStepStartEvent ) {
				BStepStartEvent bsse = (BStepStartEvent)lastEvent;
				BStep bStep = bsse.getBStep();
				System.out.println("-- Running bstep " + bStep.toString() );
				getBProgram().bSync(bStep.getSuccessEvent(), none, BStepEventFilters.ANY_START);
			}
		}	
	}
}