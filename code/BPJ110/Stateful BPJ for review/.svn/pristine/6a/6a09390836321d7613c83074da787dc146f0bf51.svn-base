package bp.state.unittest.ph;

import static bp.BProgram.markNextVerificationStateAsHot;
import static bp.eventSets.EventSetConstants.none;
import static bp.state.unittest.ph.DiningPhilsBApp.nPhils;
import static bp.state.unittest.ph.DiningPhilsBApp.pickUpLeftForkEvents;
import static bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bp.BThread;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class LTL_Phil_0_Eat_Infinitely_Often extends BThread {

	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		EventSet postEat = new EventSet(putDowns[0], putDowns[nPhils - 1]);

		while (true) {
			markNextVerificationStateAsHot();
			bp.bSync(none, pickUpLeftForkEvents[0], none);
			// System.out.println("LTL1: saw 0 eating");
			bp.bSync(none, postEat, none);
		}

	}

}
