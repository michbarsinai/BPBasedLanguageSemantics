package bp.bflow.core;

import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.Before;
import org.junit.Test;

import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.TriggeredBFlow;
import bp.bflow.core.TriggeredBFlowAdapter;
import bp.contrib.NamedEvent;

public class TriggeredBSequenceTest {
	
	BProgram bProg;
	final AtomicInteger executionCounter = new  AtomicInteger();
	CountDownLatch bflowComplete=null;
	
	@Before
	public void setup() {
		bProg = new BProgram();
		executionCounter.set(0);
		bflowComplete = new CountDownLatch(1);
	}
	
	@Test
	public void testSingleTriggered() throws InterruptedException {
		final BStep t = new BStep("Trigger");
		
		
		final TriggeredBFlow sut = new TriggeredBFlowAdapter(t.getStartEvent()) {
			@Override
			public void sequenceTriggered(Event evt) throws InterruptedException {
				executionCounter.incrementAndGet();
				bpExec( broadcast(t.getSuccessEvent()) );
			}
		};
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				bpExec(parallel( sut, t ));
				bflowComplete.countDown();
			}
		}.startBThread(bProg);
		
		bflowComplete.await();
		
		assertTrue( executionCounter.get() == 1 );
	}
	
	@Test
	public void testSingleTriggeredReverseRequestOrder() throws InterruptedException {
		final BStep t = new BStep("Trigger");
		
		
		final TriggeredBFlow sut = new TriggeredBFlowAdapter(t.getStartEvent()) {
			@Override
			public void sequenceTriggered(Event evt) throws InterruptedException {
				executionCounter.incrementAndGet();
				bpExec(broadcast( t.getSuccessEvent() ));
				
			}
		};
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				bpExec(parallel( t, sut ));
				bflowComplete.countDown();
			}
		}.startBThread(bProg);
		
		bflowComplete.await();
		
		assertTrue( executionCounter.get() == 1 );
	}
	
	@Test
	public void testTwoTriggered() throws InterruptedException {
		final BStep t1 = new BStep("Trigger1");
		final Event term = new NamedEvent("Terminate");
		
		final TriggeredBFlow sutT1 = new TriggeredBFlowAdapter(t1.getStartEvent()) {
			@Override
			public void sequenceTriggered(Event evt) throws InterruptedException {
				executionCounter.incrementAndGet();
				bpExec( broadcast( t1.getSuccessEvent() )) ;
			}
		};
		
		final TriggeredBFlow sutT2 = new TriggeredBFlowAdapter(t1.getSuccessEvent(),term) {
			@Override
			public void sequenceTriggered(Event evt) throws InterruptedException {
				executionCounter.incrementAndGet();
			}
		};
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				bpExec( parallel( sutT1,
								  sutT2,
								  t1)
						);
				bflowComplete.countDown();
			}
		}.startBThread(bProg);
		
		bflowComplete.await();
		
		assertTrue( executionCounter.get() == 2 );
	}
}
