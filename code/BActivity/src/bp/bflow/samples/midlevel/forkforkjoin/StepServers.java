package bp.bflow.samples.midlevel.forkforkjoin;

import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.List;

import bp.BThread;
import bp.bflow.core.BStep;
import bp.contrib.BThreadPriorityManager;

public class StepServers {
	public static List<BThread> getServers() {
		String[] eventNames = {"a0","b0","b1","c1","c2","d0"};
		
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
				@Override
				public void run() {
					// run activity here
					System.out.println("[Step Server] Running step " + step );
					
					// span a BThread to do report task completion.
					BThread endEventRequestor = new BThread(){
						@Override
						public void runBThread() throws InterruptedException {
							getBProgram().bSync(step.getSuccessEvent(), none, none);
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

