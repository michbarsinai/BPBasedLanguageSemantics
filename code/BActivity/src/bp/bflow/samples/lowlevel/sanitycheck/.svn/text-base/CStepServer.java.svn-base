package bp.bflow.samples.lowlevel.sanitycheck;

import bp.BThread;
import static bp.eventSets.EventSetConstants.none;


public class CStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, SanityCheckBActivity.CBeginRequest, none );
		System.out.println("C in progress.... [DONE]");
		getBProgram().bSync( SanityCheckBActivity.CEndRequest, none, none );
	}

}
