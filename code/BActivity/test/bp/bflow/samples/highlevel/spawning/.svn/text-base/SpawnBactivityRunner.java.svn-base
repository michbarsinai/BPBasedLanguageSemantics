package bp.bflow.samples.highlevel.spawning;


import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static bp.eventSets.EventSetConstants.all;
import bp.BProgram;
import bp.Event;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BStep;
import bp.bflow.samples.highlevel.utils.StepServers;
import bp.contrib.NamedEvent;
import bp.validation.eventpattern.EventPattern;
import bp.validation.eventpattern.JUnitBpHelper;
import static junit.framework.Assert.*;

public class SpawnBactivityRunner {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	@Test
	public void runTest() throws InterruptedException {
		new JUnitBpHelper(){
			@Override
			protected void setupAndRun(BProgram bp) {
				for ( String s : Arrays.asList("a","b","c","A","B","C","1","2","3") ) {
					StepServers.prepareServerFor(bp, new BStep(s) );
				}
				new BFlowBatch(){

					@Override
					public void runBFlowBatch() throws InterruptedException {
						bpExec( new SpawnBActivity() );
						
				}}.startBThread(bp);
			}
			
			@Override
			protected void runAssertions(List<Event> events) {
				// see that they all run
				assertTrue( new EventPattern()
								.appendStar( all )
								.append( new BStep("a").getStartEvent()).appendStar( all )
								.append( new BStep("b").getStartEvent()).appendStar( all )
								.append( new BStep("c").getStartEvent()).appendStar( all )
								.matches( events ) );
				assertTrue( new EventPattern()
								.appendStar( all )
								.append( new BStep("A").getStartEvent()).appendStar( all )
								.append( new BStep("B").getStartEvent()).appendStar( all )
								.append( new BStep("C").getStartEvent()).appendStar( all )
								.matches( events ) );
				assertTrue( new EventPattern()
								.appendStar( all )
								.append( new BStep("1").getStartEvent()).appendStar( all )
								.append( new BStep("2").getStartEvent()).appendStar( all )
								.append( new BStep("3").getStartEvent()).appendStar( all )
								.matches( events ) );
				
				// see that they all waited for "E"
				for ( String eName : Arrays.asList("c","C","3") ) {
					assertTrue( new EventPattern()
								.appendStar( all )
								.append( new NamedEvent("E") ).appendStar( all )
								.append( new BStep( eName ).getStartEvent()).appendStar( all )
								.matches( events ) );
				}
				
			}

		}.runTest();
	}

}
