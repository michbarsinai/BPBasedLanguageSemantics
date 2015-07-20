package bp.bflow.samples.lowlevel.sanitycheckvalue;

import bp.BThread;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import static bp.eventSets.EventSetConstants.none;


public class CStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, new StepStartEvent("C"), none );
		
		System.out.println("C Started");
		Double data = ((StepStartEvent)getBProgram().lastEvent).getValue(); 
		Double result = data*3d;
		System.out.println(" C result = " + result );
		
		getBProgram().bSync( new StepEndEvent(result, "C"), none, none );
	}

}
