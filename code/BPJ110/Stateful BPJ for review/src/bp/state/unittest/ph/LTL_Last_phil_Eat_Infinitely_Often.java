package bp.state.unittest.ph;

import static bp.BProgram.markNextVerificationStateAsHot;
import static bp.eventSets.EventSetConstants.none;
import static bp.state.unittest.ph.DiningPhilsBApp.nPhils;
import static bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class LTL_Last_phil_Eat_Infinitely_Often extends BThread {

	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		EventSet postEat = new EventSet(putDowns[nPhils - 1], putDowns[0]);

		while (true) {
			markNextVerificationStateAsHot();
			// bp.bSync(none, pickUp[nPhils-1], none);
			// ***** Need to fix to have correct fork based on right or left
			System.out.println("LTL1: saw LAST eating");
			bp.bSync(none, postEat, none);
		}

	}

}
