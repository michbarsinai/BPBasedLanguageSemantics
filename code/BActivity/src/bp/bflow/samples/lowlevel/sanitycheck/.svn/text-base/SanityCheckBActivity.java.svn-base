package bp.bflow.samples.lowlevel.sanitycheck;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import static bp.eventSets.EventSetConstants.none;
/**
 * A->B->C
 * 
 */
public class SanityCheckBActivity extends BThread {
	
	static final Event ABeginRequest = new Event(),
						AEndRequest = new Event(),
						BBeginRequest = new Event(),
						BEndRequest = new Event(),
						CBeginRequest = new Event(),
						CEndRequest = new Event();
	
	
	@Override
	public void runBThread() throws InterruptedException {
		BProgram bp = new BProgram();
		bp.bSync( ABeginRequest, none, none );
		bp.bSync( none, AEndRequest, none );
		
		bp.bSync( BBeginRequest, none, none );
		bp.bSync( none, BEndRequest, none );
		
		bp.bSync( CBeginRequest, none, none );
		bp.bSync( none, CEndRequest, none );
		
	}

}
