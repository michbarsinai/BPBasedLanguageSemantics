package bp.bflow.samples.lowlevel.sanitycheckvalue;

import bp.BThread;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import static bp.eventSets.EventSetConstants.none;

public class BStepServer extends BThread {

	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( none, new StepStartEvent("B"), none );
		
		System.out.println("B Started");
		Double data = ((StepStartEvent)getBProgram().lastEvent).getValue(); 
		Double result = data+2d;
		System.out.println(" B result = " + result );
		
		getBProgram().bSync( new StepEndEvent(result, "B"), none, none );
	}

}
