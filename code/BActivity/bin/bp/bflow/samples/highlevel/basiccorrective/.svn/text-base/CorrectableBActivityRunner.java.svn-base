package bp.bflow.samples.highlevel.basiccorrective;

import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.BStepServer;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;
import static org.junit.Assert.assertTrue;
/**
 * Sets up the environment and runs the correctable bactivity.
 * 
 * @author michaelbar-sinai
 */
public class CorrectableBActivityRunner {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	@Test
	public void TestCorrectableBFlow() throws InterruptedException {
		final BProgram bp = new BProgram();
		
		final CountDownLatch cl = new CountDownLatch(1);
		
		StepServers.prepareServerFor(bp,  new BStep("A") );
		StepServers.prepareServerFor(bp,  new BStep("C") );
		
		BFlowBatch evn = new BFlowBatch(){
			@Override
			public void runBFlowBatch() throws InterruptedException {
				System.out.println("Bflow run is on");
				
				bpExec(parallel(
						// set up Environment
						new BStepServer_B(),
						new CorrectorB_Triggered(),

						// Go Bactivity (+cleanup)
						sequence( new CorrectableBActivity(),
									broadcast(BStepServer.TERMINATE_SERVERS),
									broadcast( EventHistory.getShutdownEvent()) )
						
						));
				System.out.println("After bpExec");
				cl.countDown();
		}};
		
		EventHistory.start(bp);
		evn.startBThread(bp);
		
		// wait for bactivity to end.
		cl.await();
		
		EventPattern matcher = new EventPattern();
		matcher.append( new BStep("A").getStartEvent() )
			   .append( new BStep("A").getSuccessEvent() )
			   .append( new BStep("B").getStartEvent() )
			   .append( new BStep("B").getFailureEvent() )
			   .append( new BStep("B").getStartEvent() )
			   .append( new BStep("B").getFailureEvent() )
			   .append( new BStep("B").getStartEvent() )
			   .append( new BStep("B").getSuccessEvent() )
			   .append( new BStep("C").getStartEvent() )
			   .append( new BStep("C").getSuccessEvent() );
		
		assertTrue( matcher.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
		
	}
}
