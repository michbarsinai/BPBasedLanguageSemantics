package bp.bflow.samples.midlevel.forkforkjoin;

import bp.BProgram;
import bp.BThread;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.eventhistory.EventHistory;

public class ForkForkJoinRunner {
	
	
	public static void main( String[] args ) {
		
		BProgram bp = new BProgram();
		for ( BThread bt : ValueStepServers.getServers() ) {
//		for ( BThread bt : StepServers.getServers() ) {
			bp.add(bt, BThreadPriorityManager.nextPriority());
		}
		bp.add( new ForkForkJoinBActivity(), BThreadPriorityManager.nextPriority() );
		
		EventHistory.start( bp );
		bp.startAll();
		

	}
}
