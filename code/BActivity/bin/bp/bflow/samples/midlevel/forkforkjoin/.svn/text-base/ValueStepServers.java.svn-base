package bp.bflow.samples.midlevel.forkforkjoin;

import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bp.BThread;
import bp.bflow.core.BStep;
import bp.bflow.core.events.BStepSuccessEvent;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.eventhistory.EventHistory;
import bp.contrib.eventhistory.EventOccurrence;

public class ValueStepServers {
	static Map<String, String> before = new HashMap<String, String>();
	
	public static List<BThread> getServers() {
		String[] eventNames = {"a0","b0","b1","c1","c2","d0"};
		
		before.put("b0", "a0");
		before.put("b1", "a0");
		before.put("c1", "b1");
		before.put("c2", "b1");
		before.put("d0", "c1 c2 b0");
		
		List<BThread> servers = new ArrayList<BThread>(eventNames.length);
		for ( String name : eventNames ) {
			servers.add( new StepServer(name) );
		}
		
		return servers;
	}
	
	static class StepServer extends BThread {
		
		private BStep step; 
		
		public StepServer(String aStepName) {
			super();
			step = new BStep(null, aStepName);
		}

		@Override
		public void runBThread() throws InterruptedException {
			getBProgram().bSync(none, step.getStartEvent(), none);
			
			// spawn a worker thread to do the actual task.
			Runnable activityRunner = new Runnable() {
				
				Long value = null;
				
				@Override
				public void run() {
					// run activity here
					System.out.println("[Step Server] Running step " + step );
					
					if ( step.getId().equals("a0") ) {
						value = 1l;
					} else {
						value = 0l;
						String[] beforeEventNames = before.get(step.getId()).split(" ");
						for ( String eventName : beforeEventNames ) {
							EventOccurrence efr = EventHistory.historian(getBProgram())
													.selectRecent( new BStep(null, eventName).getSuccessEvent() );
							BStepSuccessEvent end = (BStepSuccessEvent) efr.getEvent();
							value += (Long)end.getValue();
						}
						value++;
					}
					
					
					// spawn a BThread to do report task completion.
					BThread endEventRequestor = new BThread(){
						@Override
						public void runBThread() throws InterruptedException {
							getBProgram().bSync(step.getSuccessEvent(value), none, none);
						}
						
						@Override
						public String toString() { return "StepServerEndReporter ( " + step + " )"; }
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
			return "BStepServer ["+ step + "]";
		}
		
	}
	
}
