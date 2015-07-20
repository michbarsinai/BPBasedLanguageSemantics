package bp.bflow.samples.lowlevel.forkjoinvalue;

import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.List;

import bp.BThread;
import bp.bflow.samples.lowlevel.valueevents.StepEndEvent;
import bp.bflow.samples.lowlevel.valueevents.StepStartEvent;
import bp.bflow.samples.lowlevel.valueevents.ValueEvent;
import bp.contrib.BThreadPriorityManager;

/**
 * Provides the BStepServers for doing the
 * calculations of the {@link ForkJoinValueBActivity}.
 * 
 * @author michaelbar-sinai
 */
public class BStepServers {
	
	public static List<BThread> getServers() {
		List<BThread> servers = new ArrayList<BThread>();
		
		servers.add( new StepServer("A") {
			@Override
			public Double calculate(Double inValue) {
				return 1d;
			}} );
		servers.add( new StepServer("B") {
			@Override
			public Double calculate(Double inValue) {
				return inValue + 1d;
			}} );
		servers.add( new StepServer("C1") {
			@Override
			public Double calculate(Double inValue) {
				return inValue*2;
			}} );
		servers.add( new StepServer("C1a") {
			@Override
			public Double calculate(Double inValue) {
				return -inValue;
			}} );
		servers.add( new StepServer("C2") {
			@Override
			public Double calculate(Double inValue) {
				return inValue*3;
			}} );
		servers.add( new StepServer("C3") {
			@Override
			public Double calculate(Double inValue) {
				return inValue*4;
			}} );
		
		servers.add( new DStepServer() );
		
		return servers;
	}
}

/**
 * Waits for the {@link #start} event to be invoked,
 * does what it has to do (this is the abstract part) and then
 * requests the invocation of the {@link #end} event.
 * The events are automatically created from the event name that is passed
 * in the constructor.
 * 
 * @author michaelbar-sinai
 */
abstract class StepServer extends BThread {
	
	ValueEvent start;
	ValueEvent end;
	
	public StepServer(String stepName) {
		super();
		this.start = new StepStartEvent( stepName );
		this.end = new StepEndEvent( stepName );
	}

	@Override
	public void runBThread() throws InterruptedException {
		System.out.println("## BStepServer waits for " + start );
		getBProgram().bSync( none, start, none );
		
		// Copy the value from the lastEvent locally, we can only
		// read it between bSync()s, so the worker thread can't
		// access it safely.
		start.setValue( ((ValueEvent)getBProgram().lastEvent).getValue() );
		
		// spawn a worker thread to do the actual task.
		Runnable activityRunner = new Runnable() {
			@Override
			public void run() {
				// run activity here
				System.out.println("Running activity " + start + " ->  " + end );
				
				end.setValue( calculate(start.getValue()) );
				
				// span a BThread to do report task completion.
				BThread endEventRequestor = new BThread(){
					@Override
					public void runBThread() throws InterruptedException {
						getBProgram().bSync(end, none, none);
					}
					
					@Override
					public String toString() { return "StepServerEndReporter ( " + end + " )"; }
				};
				getBProgram().add(endEventRequestor, BThreadPriorityManager.nextPriority());
				
				// go task completion reporter
				endEventRequestor.startBThread();
			}};
		
		// go worker thread.
		new Thread( activityRunner ).start();
	}
	
	public abstract Double calculate( Double inValue );
	
	@Override
	public String toString() {
		return "BStepServer [start=" + start + ", end=" + end + "]";
	}	
}

class DStepServer extends BThread {
	
	public DStepServer() {
	}

	@Override
	public void runBThread() throws InterruptedException {
		System.out.println("## DStepServer is wating for a DStartRequest");
		getBProgram().bSync( none, new DStartRequest(), none );
		
		DStartRequest req = (DStartRequest) getBProgram().lastEvent;
		
		final double result = req.getC1a()+req.getC2()+req.getC3() - req.getA();
		
		// spawn a worker thread to do the actual task.
		Runnable activityRunner = new Runnable() {
			@Override
			public void run() {
				// run activity here
				System.out.println("Running activity for step D" );
		
				final ValueEvent resultEvent = new StepEndEvent(result, "D");
				
				// span a BThread to do report task completion.
				BThread endEventRequestor = new BThread(){
					@Override
					public void runBThread() throws InterruptedException {
						getBProgram().bSync(resultEvent, none, none);
					}
					
					@Override
					public String toString() { return "StepServerEndReporter ( D )"; }
				};
				getBProgram().add(endEventRequestor, BThreadPriorityManager.nextPriority());
				
				// go task completion reporter
				endEventRequestor.startBThread();
			}};
		
		// go worker thread.
		new Thread( activityRunner ).start();
	}
	
	@Override
	public String toString() {
		return "DStepServer";
	}
	
	
	
}
