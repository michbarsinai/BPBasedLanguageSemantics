
package bp.state.unittest.ph;

import static bp.BProgram.markNextVerificationStateAsBad;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import bp.BThread;
import bp.exceptions.BPJException;

public class Logger extends BThread{

	int count = 0;
	int bound;
	

	@Override
	public void runBThread() throws BPJException {

		while (true){
			bp.bSync(none, all, none);
			System.out.println("LOG:" + bp.lastEvent);
				if (++count == bound)
				markNextVerificationStateAsBad("Event count bound Exceeded = " + bound); 
		}			
	}
}
