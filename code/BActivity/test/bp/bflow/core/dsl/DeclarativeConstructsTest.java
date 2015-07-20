package bp.bflow.core.dsl;

import static bp.eventSets.EventSetConstants.all;
import static bp.eventSets.EventSetConstants.none;
import static org.junit.Assert.assertTrue;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.UniversalBStepServer;
import bp.contrib.eventhistory.EventHistory;
import bp.exceptions.BPJRequestableSetException;
import bp.validation.eventpattern.EventPattern;
import static bp.eventSets.BooleanComposableEventSet.*;

public class DeclarativeConstructsTest {
	@Test
	public void testConstructs() throws InterruptedException {
		BProgram bp = new BProgram();
		
		bp.add(new UniversalBStepServer(), 1d);
		bp.add(new SeqCReverser(), 1.5d); // Adding this to validate that inParallel works.
		
		bp.startAll();

		EventHistory.start(bp);
		CountDownLatch cdl = new CountDownLatch(1);
		new EnvBactivity( cdl ).startBThread(bp);
		
		cdl.await();

		// validate history
		EventPattern ep = new EventPattern();
		ep.appendStar(all)
		  .append( new BStep("A").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("B").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("CC").getStartEvent() )
		  .appendStar( all )
		  .append( theEventSet( new BStep("AA").getStartEvent()).or( new BStep("BB").getStartEvent()) )
		  .appendStar( all )
		  .append( theEventSet( new BStep("AA").getStartEvent()).or( new BStep("BB").getStartEvent()) )
		  .appendStar( all );
		assertTrue( ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
		
		ep = new EventPattern();
		ep.appendStar( all )
		  .append( new BStep("seqA").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("seqB").getSuccessEvent() )
		  .appendStar( all )
		  .append( new BStep("seqC2").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("seqC1").getStartEvent() )
		  .appendStar( all )
		  .append( new BStep("seqD").getStartEvent() )
		  .appendStar( all );
		
		assertTrue( ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
	}
	
}

class EnvBactivity extends BFlowBatch {
	
	CountDownLatch cdl;
	
	EnvBactivity( CountDownLatch aCdl ) {
		cdl = aCdl;
	}
	
	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( new InParallelBACT(), 
				broadcast(UniversalBStepServer.TERMINATE) );
		cdl.countDown();
	}
	
}

/**
 * @author michaelbar-sinai
 */
class InParallelBACT extends BFlowBatch {

	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( bstep("A"),
				bstep("B"),
				sequence( bstep("CC"),
						  parallel( bstep("AA"), bstep("BB"))
				));
		bpExec( bstep("seqA"),
				bstep("seqB"),
				parallel( bstep("seqC1"), bstep("seqC2") ),
				bstep("seqD") );
	}
}

/**
 * Forces the safety premise that seqC1 cannot start right after seqB is done.
 * 
 * @author michaelbar-sinai
 */
class SeqCReverser extends BThread {
	
	private Event seqBDoneEvent = new BStep("seqB").getSuccessEvent();
	
	@Override
	public void runBThread() throws InterruptedException, BPJRequestableSetException {
		Event lastEvent = null;
		while ( lastEvent != UniversalBStepServer.TERMINATE ) {
			getBProgram().bSync(none, all, none);
			lastEvent = getBProgram().lastEvent;
			if ( seqBDoneEvent.contains(lastEvent) ) {
				getBProgram().bSync( none, all, new BStep("seqC1").getStartEvent() );
			}
		}	
	}
	
}