package bp.bflow.core.dslhelpers;

import bp.eventSets.RequestableInterface;

/** 
 * A class for easily requesting events.
 * Create new instances for later use, or
 * statically import {@link #broadcastEvent(RequestableInterface)}.
 * 
 * @author michaelbar-sinai
 */
public class Broadcaster extends bp.bflow.core.BFlow {
	
	private RequestableInterface broadcastee;
	
	public static Broadcaster broadcastEvent( RequestableInterface broadcastee ) {
		return new Broadcaster(broadcastee);
	}
	
	public Broadcaster(RequestableInterface broadcastee) {
		super();
		this.broadcastee = broadcastee;
	}


	@Override
	public void runBFlow() throws InterruptedException {
		getBProgram().bSync(broadcastee, bp.eventSets.EventSetConstants.none, bp.eventSets.EventSetConstants.none);
	}

	public RequestableInterface getBroadcastee() {
		return broadcastee;
	}
	
	@Override
	public String toString() {
		return "[Broadcaster@"+ Integer.toHexString(hashCode())+"]";
	}
	
}
