package bp.bflow.core.dslhelpers;

import static bp.eventSets.BooleanComposableEventSet.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import bp.BThread;
import bp.Event;
import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;
import bp.bflow.core.ExceptingEventFiredException;
import bp.bflow.core.events.IdentityEvent;
import bp.contrib.BThreadPriorityManager;

/**
 * A sequence that has many sub sequences it runs in parallel. All the sub-sequences are added to the
 * BProgram before the first one starts to run, and so effectively (in bp-time) they start
 * simultaneously.
 * 
 * @author michaelbar-sinai
 */
public class ParallelBFlow extends BFlow {

	/** Counts how many instances were created. Used for naming. */
	private AtomicInteger doInParallelCount = new AtomicInteger(0);

	private Iterable<BFlowUnit> subs;
	private int serial = doInParallelCount.incrementAndGet();
	
	public ParallelBFlow( Iterable<BFlowUnit> someSubs ) {
		subs = someSubs;
	}
	
	@Override
	public void runBFlow() throws InterruptedException {
		
		// prepare the data shared by the sub-threads/
		final Event allSubsDone = new IdentityEvent("doneInParallel#"
				+ Integer.toHexString(hashCode()) + "-"
				+ serial);
		final AtomicReference<ExceptingEventFiredException> exRef = new AtomicReference<ExceptingEventFiredException>();
		
		// prepare the sub-threads.
		List<BThread> bts = new ArrayList<BThread>();
		for (BFlowUnit f : subs) {
			final BFlowUnit bu = f;
			final BFlow executor = new BFlow(getBProgram()) {
				@Override
				public void runBFlow() throws InterruptedException {
					try {
						bu.acceptSequence(this);
					} catch (ExceptingEventFiredException efe) {
						exRef.set(efe);
					}
				}
			};
			executor.setParent(this);
			executor.setTerminatedEvent(anyOf(allSubsDone, getTerminatedEvent()));
			executor.setExceptingEvents(getExceptingEvents());

			BThread bt = new BThread() {
				@Override
				public void runBThread() throws InterruptedException {
					executor.runBFlow();
				}
			};
			bt.setName("[doInParallel-" + Thread.currentThread() + "/" + bu + "]");
			getBProgram().add(bt, BThreadPriorityManager.nextPriority());
			bts.add(bt);
		}
		
		// Go sub-threads
		for (BThread bt : bts) {
			bt.startBThread();
		}
		
		// wait until they're done
		getBProgram().bSync(allSubsDone, getExceptingEvents(), getTerminatedEvent());
		assertNoExceptingEvents( getBProgram().lastEvent );
		
		// aftermath
		if (exRef.get() != null) {
			// re-throw the exception on the BFlowBatch's control thread.
			throw exRef.get();
		}
	}
	
	@Override
	public String toString() {
		return "[ParallelBFlow #" + serial +"]";
	}

	public Iterable<BFlowUnit> getSubs() {
		return subs;
	}
	
}
