package bp.bflow.samples.highlevel.basiccorrective;

import java.util.logging.Logger;

import bp.BThread;
import bp.bflow.core.BStep;
import static bp.eventSets.EventSetConstants.none;


/**
 * A Stepserver that does step "B". Fails for the first 2 times.
 * 
 * @author michaelbar-sinai
 */
public class BStep_B_Stse extends BThread {
	
	private static final Logger LOGGER = Logger.getLogger(BStep_B_Stse.class.getCanonicalName());
	
	@Override
	public void runBThread() throws InterruptedException {
		int count = 3;
		BStep myStep = new BStep("B");
		while ( count > 0 ) {
			LOGGER.fine("[StSe B] Waiting for start of " + myStep );
			getBProgram().bSync( none, myStep.getStartEvent(), none);
			if ( count > 1 ) {
				LOGGER.fine("[StSe B] Step 'b' failed " + count);
				getBProgram().bSync( myStep.getFailureEvent(new Throwable("Ouch. Try again")), none, none);
			
			} else {
				LOGGER.fine("[StSe B] Step 'b' succeeded");
				getBProgram().bSync( myStep.getSuccessEvent(), none, none);
				
			}
			count--;
		}

	}

}
