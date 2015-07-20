package bp.bflow.samples.lowlevel.valueevents;

import java.util.LinkedList;
import java.util.List;

import bp.BThread;
import bp.eventSets.EventsOfClass;
import static bp.eventSets.EventSetConstants.none;

public class ResultMonitorBThread extends BThread {
		
	@Override
	public void runBThread() throws InterruptedException {
		
		EventsOfClass stepEndFilter = new EventsOfClass(StepEndEvent.class);
		List<Double> results = new LinkedList<Double>();
		
		boolean go = true;
		while( go ) {
			getBProgram().bSync( none, stepEndFilter, none );
			StepEndEvent req = (StepEndEvent)getBProgram().lastEvent; 
			results.add( req.getValue() );
			
			go = !(req.getStepName().equals("C"));
		}
		
		System.out.println("Results were: ");
		for ( Double res : results ) {
			System.out.println(" - " + res );
		}
		
	}

}
