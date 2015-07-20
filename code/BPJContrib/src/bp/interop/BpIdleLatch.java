package bp.interop;

import java.util.concurrent.CountDownLatch;

import static bp.eventSets.EventSetConstants.*;
import bp.BProgram;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;

/**
 * Allows regular java code to wait for superstep ends.
 * Has a latch that unlocks when a {@link BProgram} has no requested events.
 *
 * @author michaelbar-sinai
 */
public class BpIdleLatch {
	
	private BProgram bprogram;
	private CountDownLatch latch;
	
	public BpIdleLatch( BProgram aBp ) {
		bprogram = aBp;
		BThread bil = new IdleEventBThread();
		bprogram.add(bil, Double.MAX_VALUE);
		latch = new CountDownLatch(1);
		bil.startBThread();
	}
	
	public void await() throws InterruptedException {
		latch.await();
	}
	
	static class IdleEvent extends bp.Event {
		@Override
		public String toString() {
			return "[BpIdleLatch#IdleEvent]";
		}
	}
	
	class IdleEventBThread extends BThread {
		@Override
		public void runBThread() throws InterruptedException,
				BPJRequestableSetException {
			// Wait for something to happen (i.e. superstep start)
			getBProgram().bSync( none, all, none );
			
			// Wait for nothing to happen
			getBProgram().bSync( new IdleEvent(), none, none );
			latch.countDown();
		}
		
	}
	
}
