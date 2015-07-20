package bp.bflow.samples.highlevel.utils;

import static bp.eventSets.EventSetConstants.none;
import bp.BProgram;
import bp.BThread;
import bp.contrib.BThreadPriorityManager;
import bp.bflow.core.BStep;
import bp.bflow.core.events.BStepEvent;

public class StepServers {
	
	public static void prepareServerFor(BProgram bp, BStep s ) {
		StepServer stse = new StepServer( s );
		bp.add(stse, BThreadPriorityManager.nextPriority() );
		stse.startBThread();
	}
	
	static class StepServer extends BThread {
		
		private BStep step; 
		
		public StepServer(BStep aStep) {
			super();
			step = aStep;
		}

		@Override
		public void runBThread() throws InterruptedException {
			System.out.println("[Step Server] wating for step " + step );
			getBProgram().bSync(none, step.getStartEvent(), none);
			
			final BStep currentStep = ((BStepEvent)getBProgram().lastEvent).getBStep();
			// spawn a worker thread to do the actual task.
			Runnable activityRunner = new Runnable() {
				@Override
				public void run() {
					// run activity here
					System.out.println("[Step Server] Running step " + currentStep );
					
					// spawn a BThread to do report task completion.
					BThread endEventRequestor = new BThread(){
						@Override
						public void runBThread() throws InterruptedException {
							getBProgram().bSync(currentStep.getSuccessEvent(), none, none);
						}
						
						@Override
						public String toString() { return "StepServerEndReporter ( " + currentStep + " )"; }
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
