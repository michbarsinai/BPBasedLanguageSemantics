package bp.bflow.samples.lowlevel.sanitycheck;

import bp.BThread;

import static bp.eventSets.EventSetConstants.none;

/**
 * Performs step "A" by printing "A" to {@code System.out}.
 * 
 * @author michaelbar-sinai
 */
public class AStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, SanityCheckBActivity.ABeginRequest, none );
		System.out.println("A in progress.... [DONE]");
		getBProgram().bSync( SanityCheckBActivity.AEndRequest, none, none );
	}

}
