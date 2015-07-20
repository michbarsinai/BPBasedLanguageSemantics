package bp.bflow.samples.highlevel.utils;

import static org.junit.Assert.assertTrue;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.BStepServer;
import bp.bflow.core.dslhelpers.Broadcaster;

public class StepServerTest {
	BProgram bProg;
	CyclicBarrier bpDone = new CyclicBarrier(2);
	final AtomicInteger executionCounter = new  AtomicInteger();
	
	@Before
	public void setup() {
		bProg = new BProgram();
		executionCounter.set(0);
		bpDone.reset();
	}
	
	@Test
	public void testSingleTriggered() throws InterruptedException, BrokenBarrierException {
		final BStep t = new BStep("Trigger");
	
		final BStepServer<Object> sut = new BStepServer<Object>(t){
			@Override
			protected Object serveStep(Event evt) throws Exception {
				executionCounter.incrementAndGet();
				return "Step Served";
			}
		};
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				bpExec( parallel( sut, 
						sequence(t, new Broadcaster(BStepServer.TERMINATE_SERVERS))) );
				try {
					bpDone.await();
				} catch (BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}.startBThread(bProg);
		
		bpDone.await();
		assertTrue( executionCounter.get() == 1 );
	}

}
