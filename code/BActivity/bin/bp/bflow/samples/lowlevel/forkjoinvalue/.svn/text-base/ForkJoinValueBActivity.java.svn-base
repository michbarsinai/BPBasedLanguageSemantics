package bp.bflow.samples.lowlevel.forkjoinvalue;

import static bp.eventSets.EventSetConstants.none;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

import bp.BThread;
import bp.Event;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import bp.bflow.samples.lowlevel.valueevents.ValueEvent;
import bp.bflow.utils.EventComparator;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.NamedEvent;

/**
 *<pre><code>
 *         +->C1-->C1a-+
 *         |           |
 *  A-->B--+->C2-------+-->D
 *         |           |
 *         +->C3-------+
 * </code></pre>
 *  Each step Calculates a value:
 *  A: a
 *  B: a+1
 *  C1: 2b 
 *   C1a: -c1
 *  C2: 3b
 *  C3: 4b
 *  D: c1a+c2+c3-a
 *  D': (c1%c1a)+c2+c3-a
 * 
 * 
 * @author michaelbar-sinai
 */
public class ForkJoinValueBActivity extends BThread {
	
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
		
		// fork, wait for join
		final Set<Event> barrier = new ConcurrentSkipListSet<Event>(EventComparator.INSTANCE);
		
		barrier.add( erC1a );
		barrier.add( erC2 );
		barrier.add( erC3 );
		
		final Map<String, Double> results = new ConcurrentHashMap<String, Double>();

		final Event joinRequest = new NamedEvent("JoinRequest");
		
		BThread c1Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C1"), none, none );
				getBProgram().bSync( none, new StepEndEvent("C1"), none );
				
				Double val = ((ValueEvent)getBProgram().lastEvent).getValue();
				
				getBProgram().bSync( new StepStartEvent(val, "C1a"), none, none );
				getBProgram().bSync( none, erC1a, none );
				results.put("C1a", ((ValueEvent)getBProgram().lastEvent).getValue() );
				
				// tell the main thread that C1 branch is done.
				barrier.remove( erC1a );
				getBProgram().bSync( joinRequest, none, none );
			}};

		BThread c2Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C2"), none, none );
				getBProgram().bSync( none, new StepEndEvent("C2"), none );
				results.put("C2", ((ValueEvent)getBProgram().lastEvent).getValue() );
				
				// tell the main thread that C2 branch is done.
				barrier.remove( erC2 );
				getBProgram().bSync( joinRequest, none, none );
			}};

		BThread c3Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( new StepStartEvent(bResult, "C3"), none, none );
				getBProgram().bSync( none, new StepEndEvent("C3"), none );
				results.put("C3", ((ValueEvent)getBProgram().lastEvent).getValue() );
				
				// tell the main thread that C2 branch is done.
				barrier.remove( erC3 );
				getBProgram().bSync( joinRequest, none, none );
			}};
		
		// Go all BThreads 
		for ( BThread bt : Arrays.asList(c1Branch, c2Branch, c3Branch) ) {
			getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
			bt.startBThread();
		}
		
		// Wait for all threads to complete.
		while ( ! barrier.isEmpty() ) {
			System.out.println("Bactivity waits on " + barrier );
			getBProgram().bSync( none, joinRequest, none );
		}
		
		// collect values and create D start req
		DStartRequest srD = new DStartRequest(aResult, results.get("C1a"),
											  results.get("C2"), results.get("C3"));
		
		// Perform D
		getBProgram().bSync( srD, none, none );
		getBProgram().bSync( none, new StepEndEvent("D"), none );
		
		System.out.println("Final result is: " + (((ValueEvent)getBProgram().lastEvent).getValue()) );
	}

}
