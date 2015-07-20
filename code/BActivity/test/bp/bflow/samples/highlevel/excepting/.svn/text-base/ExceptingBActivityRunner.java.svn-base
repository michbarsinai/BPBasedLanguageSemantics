package bp.bflow.samples.highlevel.excepting;

import static bp.eventSets.EventSetConstants.all;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import org.junit.Test;

import bp.BProgram;
import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.core.events.BStepEvent;
import bp.bflow.samples.highlevel.utils.EventHistoryBFlowTestHelper;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.eventhistory.EventHistory;
import bp.validation.eventpattern.EventPattern;

public class ExceptingBActivityRunner {
	
	@Test
	public void runSimpleTest() throws InterruptedException {
		// setup
		BProgram bp = new BProgram();
		
		final CountDownLatch flowDoneSignal = new CountDownLatch( 1 );
		for ( String name : Arrays.asList("a","b","X","Y") )  {
			StepServers.prepareServerFor(bp, new BStep(name));
		}
		
		FailingStepServer bSvr = new FailingStepServer("c");
		bSvr.startBThread(bp);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start(getBProgram());
				bpExec( new ExceptingBActivitiesContainer.SimpleExceptingBFlow(),
						broadcast( EventHistory.getShutdownEvent() ) );
				
				flowDoneSignal.countDown();
				
			}
			
		}.startBThread( bp );
		
		flowDoneSignal.await();
		
		EventPattern ep = new EventPattern();
		ep.append( new BStep("a").getStartEvent() );
		ep.append( new BStep("a").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("b").getStartEvent() );
		ep.append( new BStep("b").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("c").getStartEvent() );
		ep.append( new BStep("c").getFailureEvent() );
		ep.appendStar( all );
		ep.append( new BStep("X").getStartEvent() );
		ep.append( new BStep("X").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("Y").getStartEvent() );
		ep.append( new BStep("Y").getSuccessEvent() );
		
		assertTrue( "Assertion failed",
				    ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
		
	}
	
	@Test
	public void runNestedHandeledTest() throws InterruptedException {
		// setup
		BProgram bp = new BProgram();
		
		final CountDownLatch flowDoneSignal = new CountDownLatch( 1 );
		for ( String name : Arrays.asList("a","X", "AA", "BB") )  {
			StepServers.prepareServerFor(bp, new BStep(name));
		}
		
		new FailingStepServer("Y").startBThread(bp);
		new FailingStepServer("b").startBThread(bp);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start(getBProgram());
				bpExec( new ExceptingBActivitiesContainer.NestedHandeledExceptingBFlow(),
						broadcast( EventHistory.getShutdownEvent() ) );
				
				flowDoneSignal.countDown();
				
			}
			
		}.startBThread( bp );
		
		flowDoneSignal.await();
		
		EventPattern ep = new EventPattern();
		ep.append( new BStep("a").getStartEvent() );
		ep.append( new BStep("a").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("b").getStartEvent() );
		ep.append( new BStep("b").getFailureEvent() );
		ep.appendStar( all );
		ep.append( new BStep("X").getStartEvent() );
		ep.append( new BStep("X").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("Y").getStartEvent() );
		ep.append( new BStep("Y").getFailureEvent() );
		ep.appendStar( all );
		ep.append( new BStep("AA").getStartEvent() );
		ep.append( new BStep("AA").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("BB").getStartEvent() );
		ep.append( new BStep("BB").getSuccessEvent() );
		
		EventHistoryBFlowTestHelper.dumpEventHistory(bp);
		
		assertTrue( "Assertion failed",
				    ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
		
	}
	
	@Test
	public void runNestedTest() throws InterruptedException {
		// setup
		BProgram bp = new BProgram();
		
		final CountDownLatch flowDoneSignal = new CountDownLatch( 1 );
		for ( String name : Arrays.asList("a", "AA", "BB") )  {
			StepServers.prepareServerFor(bp, new BStep(name));
		}
		
		new FailingStepServer("b").startBThread(bp);
		
		new BFlowBatch() {
			@Override
			public void runBFlowBatch() throws InterruptedException {
				EventHistory.start(getBProgram());
				bpExec( new ExceptingBActivitiesContainer.NestedExceptingBFlow(),
						broadcast( EventHistory.getShutdownEvent() ) );
				
				flowDoneSignal.countDown();
				
			}
			
		}.startBThread( bp );
		
		flowDoneSignal.await();
		
		EventPattern ep = new EventPattern();
		ep.append( new BStep("a").getStartEvent() );
		ep.append( new BStep("a").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("b").getStartEvent() );
		ep.append( new BStep("b").getFailureEvent() );
		ep.appendStar( all );
		ep.append( new BStep("AA").getStartEvent() );
		ep.append( new BStep("AA").getSuccessEvent() );
		ep.appendStar( all );
		ep.append( new BStep("BB").getStartEvent() );
		ep.append( new BStep("BB").getSuccessEvent() );
		
		EventHistoryBFlowTestHelper.dumpEventHistory(bp);
		
		assertTrue( "Assertion failed",
				    ep.matches(EventHistoryBFlowTestHelper.getStepEventHistory(bp)));
		
	}
}

class FailingStepServer extends BFlowBatch {
	
	String stepName;
	
	public FailingStepServer( String aStepName ) {
		stepName = aStepName;
		
	}
	
	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( waitFor( new BStep(stepName).getStartEvent()),
				new BFlow(){

					@Override
					public void runBFlow() throws InterruptedException {
						BStepEvent e = (BStepEvent) getBProgram().lastEvent;
						bpExec( broadcast( e.getBStep().getFailureEvent( new Throwable("Failed by the failing server"))) );
						
					}} );
		
	}
	
	@Override
	public String toString() {
		return "FailingStepServer [step="+stepName+"]";
	}
}