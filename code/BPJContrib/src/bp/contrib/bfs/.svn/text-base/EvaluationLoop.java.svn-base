package bp.contrib.bfs;

import static bp.eventSets.EventSetConstants.none;
import bp.BProgram;
import bp.BThread;
import bp.contrib.bfs.BFuzzySystem.filteringMethod;
import bp.contrib.bfs.events.InitialOpMode;
import bp.contrib.bfs.events.OpModeEvent;
import bp.contrib.bfs.events.StopEvaluation;
import bp.eventSets.EventsOfClass;
import bp.eventSets.RequestableEventSet;
import bp.eventSets.RequestableInterface;
import bp.exceptions.BPJException;

/*
 * This is a bThread that simulates a control loop. 
 * It runs a system step and request all resulting monitored events.
 */

public class EvaluationLoop extends BThread {

	private BFuzzySystem bfs;
	private filteringMethod filter;

	public EvaluationLoop(BFuzzySystem bfs, filteringMethod filter) {
		this.bfs = bfs;
		this.filter = filter;
	}

	@Override
	public void runBThread() throws BPJException {
		interruptingEvents = new EventsOfClass(StopEvaluation.class);

		BProgram bp = getBProgram();
		
		// wait for initial state
		bp.bSync(none, new EventsOfClass(InitialOpMode.class), none);

		while (true) {

			bfs.evaluate();

			System.out
					.println("~~~~~~~~~~~~~~~~~~~New System Step~~~~~~~~~~~~~~~~~~~");

			RequestableEventSet monitoredEvents = bfs.getAllMonitoredEvents(filter);

			/*
			 * The idea is to request all events simultaneously, which is
			 * currently not supported by BPJ. Therefore we apply the following:
			 * request them one after the other, relying on the fact that these
			 * are "unblockable" events and therefore all will be triggered.
			 * 
			 * Although it is possible to pass monitoredEvents as the first
			 * parameter in the bSync call, but if done so, only one (the first)
			 * event will be requested, denying the other events the possibility
			 * of being requested.
			 */

			for (RequestableInterface e : monitoredEvents) {
				bp.bSync(e, new EventsOfClass(OpModeEvent.class), none);
			}

		}

	}

}
