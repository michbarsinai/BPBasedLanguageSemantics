package bp.bflow.samples.lowlevel.sanitycheckvalue;

import bp.BThread;
import bp.Event;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import static bp.eventSets.EventSetConstants.none;
/**
 * A[get number]->B[Add 2]->C[ Multiply by 3],
 * 
 * We also print the end value we got from C, and there's a BStepServer-like BThread that prints all the 
 * medial values when the process ends. 
 */
public class SanityCheckValueBActivity extends BThread {
	
	static final Event ABeginRequest = new Event();
	
	
	@Override
	public void runBThread() throws InterruptedException {
		
		getBProgram().bSync( ABeginRequest, none, none );
		getBProgram().bSync( none, new StepEndEvent("A"), none );
		
		Double initialValue = ((StepEndEvent)getBProgram().lastEvent).getValue();
		
		getBProgram().bSync( new StepStartEvent(initialValue, "B"), none, none );
		getBProgram().bSync( none, new StepEndEvent("B"), none );
		
		Double nextValue = ((StepEndEvent)getBProgram().lastEvent).getValue();
		
		getBProgram().bSync( new StepStartEvent(nextValue, "C"), none, none );
		getBProgram().bSync( none, new StepEndEvent("C"), none );
		
	}

}
