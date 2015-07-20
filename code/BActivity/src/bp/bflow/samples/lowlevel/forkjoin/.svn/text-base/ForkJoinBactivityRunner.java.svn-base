package bp.bflow.samples.lowlevel.forkjoin;

import bp.BProgram;
import bp.BThread;
import bp.contrib.BThreadPriorityManager;


public class ForkJoinBactivityRunner {
	public static void main( String[] args ) {
		BProgram bp = new BProgram();
		bp.add( new ForkJoinBActivity(), BThreadPriorityManager.nextPriority() );
		for ( BThread bt : BStepServers.getServers() ) {
			bp.add( bt, BThreadPriorityManager.nextPriority() );
		}
		
		bp.startAll();
	}
}
