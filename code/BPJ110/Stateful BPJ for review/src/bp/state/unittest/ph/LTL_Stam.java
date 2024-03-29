package bp.state.unittest.ph;

import static bp.BProgram.markNextVerificationStateAsBad;
import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static bp.state.unittest.ph.DiningPhilsBApp.NOEVENT;
import bp.BThread;
import bp.exceptions.BPJException;

/**
 * A BThread that detects deadlock in the BProgram. To be used only in closed
 * programs, where no dynamic BThreads exist, thus every case in which the
 * program cannot choose an event is a deadlock. NOTE: This BTrhead should have
 * the MAXIMAL priority (highest number, lowest priority) in the BProgram, to
 * make sure it is chosen only when no other request can be chosen.
 */
@SuppressWarnings("serial")
public class LTL_Stam extends BThread {

	public LTL_Stam() {
		super();
	}

	public void runBThread() throws BPJException {
		while (true) {
			bp.bSync(none, all, none);
			if (bp.lastEvent.toString().equals("PickUp-F1-by-Person1")) {
				System.out.println(bp.lastEvent + "BBB");
				// labelNextVerificationState("Stam-Bad-state");
				markNextVerificationStateAsBad("STAM");
				bp.bSync(none, NOEVENT, none);
			}
		}
	}
}
