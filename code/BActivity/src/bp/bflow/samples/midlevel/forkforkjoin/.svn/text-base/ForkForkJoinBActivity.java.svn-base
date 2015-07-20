package bp.bflow.samples.midlevel.forkforkjoin;

import static bp.eventSets.EventSetConstants.none;

import java.util.Arrays;
import bp.BThread;
import bp.Event;
import bp.bflow.core.BStep;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.NamedEvent;
import bp.contrib.eventhistory.EventHistory;
import bp.eventSets.EventSetInterface;

/**
 * <pre><code>
 * [a0]-+-[b0]--------+-[d0]
 *      |			  |
 *      +-[b1]-+-[c1]-+
 *     	  	   |	  |
 *      	   +-[c2]-+
 *     
 * </code></pre>
 * @author michaelbar-sinai
 */
public class ForkForkJoinBActivity extends BThread {
	
	@Override
	public void runBThread() throws InterruptedException {
		
		doStep("a0", none);

		final Event bFork = new NamedEvent("bFork");
				
		BThread bBranch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				doStep("b0", bFork);
			}};

		BThread b1Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				doStep("b1", bFork);
				
				final Event cFork = new NamedEvent("cFork");
				
				BThread c1Branch = new BThread() {
					@Override
					public void runBThread() throws InterruptedException {
						doStep("c1", cFork);
					}};
					
				BThread c2Branch = new BThread() {
					@Override
					public void runBThread() throws InterruptedException {
						doStep("c2", cFork);
					}};
					
				// Go all BThreads 
				for ( BThread bt : Arrays.asList(c1Branch, c2Branch) ) {
					getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
					bt.startBThread();
				}
				
				getBProgram().bSync( cFork, none, bFork );
			}};

		// Go all BThreads 
		for ( BThread bt : Arrays.asList(bBranch, b1Branch) ) {
			getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
			bt.startBThread();
		}
		
		// wait until all the subs are done
		getBProgram().bSync(bFork, none, none);
		
		doStep("d0", none);
		

		EventHistory.shutdown(getBProgram());
		
	}

	private void doStep(String name, EventSetInterface block) throws InterruptedException {
		BStep a = new BStep(null, name);
		getBProgram().bSync( a.getStartEvent(), none, block);
		getBProgram().bSync( none, a.getSuccessEvent(), block );
	}

}
