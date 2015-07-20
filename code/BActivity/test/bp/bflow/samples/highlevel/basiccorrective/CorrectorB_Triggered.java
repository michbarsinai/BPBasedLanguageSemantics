package bp.bflow.samples.highlevel.basiccorrective;

import bp.Event;
import bp.bflow.core.BStep;
import bp.bflow.core.TriggeredBFlow;
import bp.bflow.core.events.BStepEvent;
import bp.eventSets.EventSetInterface;

public class CorrectorB_Triggered extends TriggeredBFlow {

	@Override
	public EventSetInterface getTerminationEventSet() {
		return new BStep("B").getSuccessEvent();
	}

	@Override
	public EventSetInterface getTriggerEventSet() {
		return new BStep("B").getFailureEvent();
	}

	@Override
	public void sequenceTriggered(Event evt) throws InterruptedException {
		System.out.println("[CorrectorB] requests a re-run");
		bpExec( broadcast( ((BStepEvent)evt).getBStep().getStartEvent("Requested by corrB") )) ;
	}

	@Override
	protected boolean isRunAgain() {
		return true;
	}
	
	@Override
	protected void cleanup() {
		System.out.println( this + " done.");
	}
}
