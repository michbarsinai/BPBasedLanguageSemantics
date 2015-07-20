package bp.bflow.samples.lowlevel.sanitycheck;

import bp.BThread;
import static bp.eventSets.EventSetConstants.none;

public class BStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, SanityCheckBActivity.BBeginRequest, none );
		System.out.println("B in progress.... [DONE]");
		getBProgram().bSync( SanityCheckBActivity.BEndRequest, none, none );
	}

}
