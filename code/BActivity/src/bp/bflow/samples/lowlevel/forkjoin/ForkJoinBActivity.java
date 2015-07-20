package bp.bflow.samples.lowlevel.forkjoin;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

import bp.BThread;
import bp.Event;
import bp.bflow.utils.EventComparator;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.NamedEvent;
import static bp.eventSets.EventSetConstants.none;

/**
 *  ForkJoinBActivity
 *         +->C1-->C1a-+
 *         |           |
 *  A-->B--+->C2-------+-->D
 *         |           |
 *         +->C3-------+
 * 
 * @author michaelbar-sinai
 */
public class ForkJoinBActivity extends BThread {
	public static final Event ABeginRequest   = new NamedEvent("ABegin"),
							  AEndRequest     = new NamedEvent("AEnd"),
							  BBeginRequest   = new NamedEvent("BBegin"),
							  BEndRequest     = new NamedEvent("BEnd"),
							  C1BeginRequest  = new NamedEvent("C1Begin"),
							  C1EndRequest    = new NamedEvent("C1End"),
							  C1aBeginRequest = new NamedEvent("C1aBegin"),
							  C1aEndRequest   = new NamedEvent("C1aEnd"),
							  C2BeginRequest  = new NamedEvent("C2Begin"),
							  C2EndRequest    = new NamedEvent("C2End"),
							  C3BeginRequest  = new NamedEvent("C3Begin"),
							  C3EndRequest    = new NamedEvent("C3End"),
							  DBeginRequest   = new NamedEvent("DBegin"),
							  DEndRequest     = new NamedEvent("DEnd");

	@Override
	public void runBThread() throws InterruptedException {
		
		getBProgram().bSync( ABeginRequest, none, none );
		getBProgram().bSync( none, AEndRequest, none );
		
		getBProgram().bSync( BBeginRequest, none, none );
		getBProgram().bSync( none, BEndRequest, none );
		
		final Set<Event> barrier = new ConcurrentSkipListSet<Event>(EventComparator.INSTANCE);
		barrier.add( C1aEndRequest );
		barrier.add( C2EndRequest );
		barrier.add( C3EndRequest );
		final Event joinRequest = new NamedEvent("JoinRequest");
		
		BThread c1Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( C1BeginRequest, none, none );
				getBProgram().bSync( none, C1EndRequest, none );
				getBProgram().bSync( C1aBeginRequest, none, none );
				getBProgram().bSync( none, C1aEndRequest, none );
				
				// tell the main thread that C1 branch is done.
				barrier.remove( C1aEndRequest );
				getBProgram().bSync( joinRequest, none, none );
			}};

		BThread c2Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( C2BeginRequest, none, none );
				getBProgram().bSync( none, C2EndRequest, none );
				
				// tell the main thread that C2 branch is done.
				barrier.remove( C2EndRequest );
				getBProgram().bSync( joinRequest, none, none );
			}};

		BThread c3Branch = new BThread(){
			@Override
			public void runBThread() throws InterruptedException {
				getBProgram().bSync( C3BeginRequest, none, none );
				getBProgram().bSync( none, C3EndRequest, none );
				
				// tell the main thread that C3 branch is done.
				barrier.remove( C3EndRequest );
				getBProgram().bSync( joinRequest, none, none );
			}};
		
		for ( BThread bt : Arrays.asList(c1Branch, c2Branch, c3Branch) ) {
			getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
			bt.startBThread();
		}
		
		// Wait for all threads to complete.
		while ( ! barrier.isEmpty() ) {
			System.out.println("Bactivity waits on " + barrier );
			getBProgram().bSync( none, joinRequest, none );
		}
		
		getBProgram().bSync( DBeginRequest, none, none );
		getBProgram().bSync( none, DEndRequest, none );
		
		
	}
	
	@Override
	public String toString() {
		return "ForkJoinBActivity-main BThread";
	}
	
}
