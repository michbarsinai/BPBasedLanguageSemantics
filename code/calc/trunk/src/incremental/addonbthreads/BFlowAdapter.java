package incremental.addonbthreads;

import static bp.eventSets.EventSetConstants.none;

import java.util.Arrays;

import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.basicfork.BasicForkBActivity;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.exceptions.BPJRequestableSetException;
import calc.binterpreter.bthreads.CalcBThread;
import calc.binterpreter.events.CalcEvent;
import calc.binterpreter.events.ExpressionEvaluatedEvent;
import calc.binterpreter.CliSessionCtrl;

/**
 * Running the following BFlow:
 * 
 * [a0]--+--[b0]--+--[c0]
 *       |        |
 *       +--[b1]--+
 *       |        |
 *       +--[b2]--+
 * 
 * @author michaelbar-sinai
 */
public class BFlowAdapter extends CalcBThread {
	
	static final Event BFLOW_STARTED = new Event("BFLOW_STARTED");
	static final Event BFLOW_DONE = new Event("BFLOW_DONE");
	
	public BFlowAdapter() {
		super("BFlowAdapter");
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		BlockerBT blocker = new BlockerBT();
		bp.add( blocker, CliSessionCtrl.BASE_PRIORITY-0.5 );
		blocker.startBThread();
				
		while ( true ) {
			bp.bSync( none, new ExpressionEvaluatedEvent(null, 42), none );
			for ( String s : Arrays.asList("a0","b0","b1","b2","c0") ) {
				StepServers.prepareServerFor(bp, new BStep(s) );
			}
			new BFlowBatch() {
				@Override
				public void runBFlowBatch() throws InterruptedException {
					bpExec( broadcast(BFLOW_STARTED), 
							new BasicForkBActivity(),
							broadcast(BFLOW_DONE) );
				}
				
			}.startBThread( bp );
			
		}
	}

	static class BlockerBT extends CalcBThread {

		public BlockerBT() {
			super("BlockerBT");
		}

		@Override
		public void runBThread() throws InterruptedException,
				BPJRequestableSetException {
			while ( true ) {
				bp.bSync( none, BFLOW_STARTED, none );
				System.out.println("Blocking calc");
				bp.bSync( none, BFLOW_DONE, CalcEvent.ALL );
				System.out.println("NOT Blocking calc");
			}
		}
		
	}
}
