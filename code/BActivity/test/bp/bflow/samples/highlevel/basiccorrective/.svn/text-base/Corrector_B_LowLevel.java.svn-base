package bp.bflow.samples.highlevel.basiccorrective;

import java.util.logging.Logger;

import bp.BThread;
import bp.Event;
import bp.bflow.core.BStep;
import bp.eventSets.EventSet;
import static bp.eventSets.EventSetConstants.none;

/**
 * Corrects errors on BStep B, by re-requesting the start event.
 * 
 * @author michaelbar-sinai
 */
public class Corrector_B_LowLevel extends BThread{
	
	private static Logger LOGGER = Logger.getLogger(Corrector_B_LowLevel.class.getCanonicalName());
	
	@Override
	public void runBThread() throws InterruptedException {
		
		BStep myStep = new BStep( "B" );
		EventSet wakeMeOn = new EventSet();
		wakeMeOn.add( myStep.getFailureEvent() );
		wakeMeOn.add( myStep.getSuccessEvent() );
		
		while ( true ) {
			LOGGER.fine("[Corr B] Waiting for step B Over event");
			getBProgram().bSync(none, wakeMeOn, none);
			Event last = getBProgram().lastEvent;
			if ( last.contains(myStep.getFailureEvent()) ) {
				LOGGER.fine("[Corr B] Requesting re-run of step B");
				getBProgram().bSync(myStep.getStartEvent(), none, none);
				
			} else if ( last.equals(myStep.getSuccessEvent()) ) {
				break;
			}
		}
		
		
	}

}
