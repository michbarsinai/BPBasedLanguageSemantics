package bp.bflow.core;

import bp.eventSets.EventSetInterface;

/**
 * Default implementation for (@link TriggeredBFlow}. Useful when the
 * trigger, termination and re-run need do not change during the run. 
 * 
 * @author michaelbar-sinai
 */
public abstract class TriggeredBFlowAdapter extends TriggeredBFlow {
	
	private EventSetInterface triggers;
	private EventSetInterface terminators;
	private boolean isReRun;
	
	public TriggeredBFlowAdapter(EventSetInterface triggers, EventSetInterface termies,
			boolean isReRun) {
		super();
		this.triggers = triggers;
		this.terminators = termies;
		this.isReRun = isReRun;
	}
	
	/**
	 * Creates an instance with no termination events.
	 * @param triggers 
	 * @param isReRun
	 */
	public TriggeredBFlowAdapter(EventSetInterface triggers, boolean isReRun) {
		this( triggers, bp.eventSets.EventSetConstants.none, isReRun );
	}
	
	/**
	 * Creates an instance that runs once.
	 * @param triggers
	 */
	public TriggeredBFlowAdapter(EventSetInterface triggers, EventSetInterface termies) {
		this( triggers, termies, false );
	}
	
	/**
	 * Creates an instance with no termination events that runs once.
	 * @param triggers
	 */
	public TriggeredBFlowAdapter(EventSetInterface triggers) {
		this( triggers, bp.eventSets.EventSetConstants.none, false );
	}

	@Override
	public EventSetInterface getTerminationEventSet() {
		return terminators;
	}

	@Override
	public EventSetInterface getTriggerEventSet() {
		return triggers;
	}

	@Override
	protected boolean isRunAgain() {
		return isReRun;
	}

}
