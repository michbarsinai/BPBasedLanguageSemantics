package bp.bflow.samples.lowlevel.forkjoinvalue;

import static bp.eventSets.EventSetConstants.none;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import bp.BThread;
import bp.Event;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import bp.bflow.samples.lowlevel.valueevents.ValueEvent;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.NamedEvent;



/**
 * An alternative approach to the parallelization in in the fork-join scenario:
 * The main BFlowBatch requests an event that is blocked by the parallel steps.
 * 
 * @author michaelbar-sinai
 */
public class BarrierlessForkJoinValueBActivity extends BThread {
	
	Event erC1a = new StepEndEvent("C1a");
	Event erC2 = new StepEndEvent("C2");
	Event erC3 = new StepEndEvent("C3");
	
	@Override
	public void runBThread() throws InterruptedException {
		getBProgram().bSync( new StepStartEvent("A"), none, none);
		getBProgram().bSync( none, new StepEndEvent("A"), none );
		
		Double aResult = ((StepEndEvent)getBProgram().lastEvent).getValue();
		Event bStartReq = new StepStartEvent(aResult, "B");
		getBProgram().bSync( bStartReq, none, none);
		getBProgram().bSync( none, new StepEndEvent("B"), none );
		
		final Double bResult = ((StepEndEvent)getBProgram().lastEvent).getValue(); 
		
		//===== We now prepare for the fork
		// Map to store the values
		final Map<String, Double> results = new ConcurrentHashMap<String, Double>();

		final Event allSubsDoneEvent = new NamedEvent("All Subs Done");
		
		BThread c1Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C1"), none, allSubsDoneEvent );
				getBProgram().bSync( none, new StepEndEvent("C1"), allSubsDoneEvent );
				
				Double val = ((ValueEvent)getBProgram().lastEvent).getValue();
				
				getBProgram().bSync( new StepStartEvent(val, "C1a"), none, allSubsDoneEvent );
				getBProgram().bSync( none, erC1a, allSubsDoneEvent );
				results.put("C1a", ((ValueEvent)getBProgram().lastEvent).getValue() );
				
			}};

		BThread c2Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C2"), none, allSubsDoneEvent );
				getBProgram().bSync( none, new StepEndEvent("C2"), allSubsDoneEvent );
				results.put("C2", ((ValueEvent)getBProgram().lastEvent).getValue() );
				
			}};

		BThread c3Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C3"), none, allSubsDoneEvent );
				getBProgram().bSync( none, new StepEndEvent("C3"), allSubsDoneEvent );
				results.put("C3", ((ValueEvent)getBProgram().lastEvent).getValue() );
			}};
		
		// Go all BThreads 
		for ( BThread bt : Arrays.asList(c1Branch, c2Branch, c3Branch) ) {
			getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
			bt.startBThread();
		}
		
		// wait until all the subs are done
		getBProgram().bSync(allSubsDoneEvent, none, none);
		
		// collect values and create D start request
		DStartRequest srD = new DStartRequest(aResult, results.get("C1a"),
											  results.get("C2"), results.get("C3"));
		
		// Perform D
		getBProgram().bSync( srD, none, none );
		getBProgram().bSync( none, new StepEndEvent("D"), none );
		
		System.out.println("Final result is: " + (((ValueEvent)getBProgram().lastEvent).getValue()) );
	}

}
