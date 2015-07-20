package bp.state.unittest.ph;

import static bp.BProgram.labelNextVerificationState;
import static bp.eventSets.EventSetConstants.none;
import static bp.state.unittest.ph.DiningPhilsBApp.pickMeUpSets;
import static bp.state.unittest.ph.DiningPhilsBApp.pickUpLeftForkEvents;
import static bp.state.unittest.ph.DiningPhilsBApp.pickUpRightForkEvents;
import static bp.state.unittest.ph.DiningPhilsBApp.putDowns;
import bp.BThread;
import bp.Event;
import bp.eventSets.EventSet;
import bp.exceptions.BPJRequestableSetException;

@SuppressWarnings("serial")
public class Fork extends BThread {
	int id;
	// The fork has two possible pickup events.
	Event pickMeUp1;
	Event pickMeUp2;
	Event other;
	Event putMeDown;

	EventSet pickMeUpSet; // To wait for
	String upLabel;

	public Fork(int id) {
		super("Fork-" + id);
		this.id = id;
		pickMeUp1 = pickUpRightForkEvents[id];
		pickMeUp2 = pickUpLeftForkEvents[id];
		pickMeUpSet = pickMeUpSets[id];
		putMeDown = putDowns[id];

	}

	public void runBThread() throws BPJRequestableSetException {
		while (true) {
			labelNextVerificationState("D ");
			bp.bSync(none, pickMeUpSet, none);
			if (bp.lastEvent.equals(pickMeUp1)) {
				other = pickMeUp2;
				upLabel = "UL";
			} else {
				other = pickMeUp1;
				upLabel = "UR";
			}
			labelNextVerificationState(upLabel);
			bp.bSync(none, putMeDown, other);
		}
	}
}