package bp.bflow.samples.lowlevel.sanitycheckvalue;

import bp.BThread;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;

import static bp.eventSets.EventSetConstants.none;

/**
 * Performs step "A" by printing "A" to {@code System.out}.
 * 
 * @author michaelbar-sinai
 */
public class AStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, SanityCheckValueBActivity.ABeginRequest, none );
		System.out.println("A Started");
		Double result = 5d;
		System.out.println("A result = " + result );
		
		getBProgram().bSync( new StepEndEvent(result, "A"), none, none );
	}

}
