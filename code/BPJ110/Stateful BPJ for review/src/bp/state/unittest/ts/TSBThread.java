package bp.state.unittest.ts;

import java.util.ArrayList;

import bp.BThread;
import bp.eventSets.RequestableInterface;

@SuppressWarnings("serial")
public class TSBThread extends BThread {

	public ArrayList<TSState> states = new ArrayList<TSState>();
	public TSState initialState;
	public ArrayList<TSTransition> transitions = new ArrayList<TSTransition>();
	public String name; 
	TSState currentState;

	public TSBThread(String name) {
		super(name); 
	}

	public void runBThread() {
		currentState = initialState;
		// populate the watched events in each state
		for (TSState s : states) {
			for (TSTransition t : transitions)
				if (t.fromState.equals(s)) {
						s.watchedEvents.add(t.transitionEvents);
						for (RequestableInterface e:t.transitionEvents) { 
							System.out.println("BT=" + this + " State=" + s + " FromState=" + t.fromState + " ToState=" + t.toState + " TransitionEvent=" + e);
						}
				}
		}

		while (true) {
		// 	labelNextVerificationState(currentState.name);
			bp.bSync(currentState.requestedEvents, currentState.watchedEvents, currentState.blockedEvents);
			for (TSTransition t: transitions){ 
				if (t.fromState.equals(currentState) && t.transitionEvents.contains(bp.lastEvent)) { 
					currentState = t.toState;
					break; 
				}
			}
			

		}

	}
}