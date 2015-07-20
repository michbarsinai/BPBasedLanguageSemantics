package bp.bflow.samples.highlevel.basiccorrective;

import bp.Event;
import static bp.eventSets.EventSetConstants.none;
import bp.bflow.core.BStep;
import bp.bflow.core.TriggeredBFlow;
import bp.bflow.core.events.BStepEvent;
import bp.eventSets.EventSetInterface;

public class BStepServer_B extends TriggeredBFlow {
	
	int count = 3;
	
	@Override
	public EventSetInterface getTerminationEventSet() {
		return none;
	}

	@Override
	public EventSetInterface getTriggerEventSet() {
		return new BStep("B").getStartEvent();
	}

	@Override
	protected void setup() {
		System.out.println("BStepServer_b ready");
	}
	
	
	
	@Override
	protected void cleanup() {
		System.out.println("BStepServer_B done");
	}

	@Override
	public void sequenceTriggered(Event evt) throws InterruptedException  {
		
		count--;
		System.out.println("BStepServer_b triggered. " + count + " invocations left.");
		
		BStep step = ((BStepEvent)evt).getBStep();
		if ( count != 0 ) {
			requestAndWaitForEvent( step.getFailureEvent(new Throwable("nope! ")));
		} else {
			requestAndWaitForEvent( step.getSuccessEvent());
		}
		

	}

	@Override
	protected boolean isRunAgain() {
		return (count>0);
	}

}
