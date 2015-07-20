package bp.bflow.samples.lowlevel.forkjoin;

import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.ABeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.AEndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.BBeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.BEndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C1BeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C1EndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C1aBeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C1aEndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C2BeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C2EndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C3BeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.C3EndRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.DBeginRequest;
import static bp.bflow.samples.lowlevel.forkjoin.ForkJoinBActivity.DEndRequest;
import static bp.eventSets.EventSetConstants.none;

import java.util.ArrayList;
import java.util.List;

import bp.BThread;
import bp.Event;
import bp.contrib.BThreadPriorityManager;

/**
 * Provides step servers for the simple fork-join BFlowBatch
 * 
 * @author michaelbar-sinai
 */
public class BStepServers {
	
	public static List<BThread> getServers() {
		Event[] begins = { ABeginRequest, BBeginRequest, C1BeginRequest,
							C1aBeginRequest, C2BeginRequest, C3BeginRequest, DBeginRequest};
		Event[] ends = {AEndRequest, BEndRequest, C1EndRequest, C1aEndRequest, 
							 C2EndRequest, C3EndRequest, DEndRequest};
		
		List<BThread> servers = new ArrayList<BThread>(begins.length);
		for ( int i=0; i<begins.length; i++ ) {
			servers.add( new StepServer(begins[i], ends[i]) );
		}
		
		return servers;
	}
	
	static class StepServer extends BThread {
		
		Event start;
		Event end;
		
		public StepServer(Event start, Event end) {
			super();
			this.start = start;
			this.end = end;
		}

		@Override
		public void runBThread() throws InterruptedException {
			getBProgram().bSync( none, start, none );
			
			// spawn a worker thread to do the actual task.
			Runnable activityRunner = new Runnable() {
				@Override
				public void run() {
					// run activity here
					System.out.println("Running activity " + start + " ->  " + end );
					
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

		@Override
		public String toString() {
			return "BStepServer [start=" + start + ", end=" + end + "]";
		}
		
		
		
	}
	
}
