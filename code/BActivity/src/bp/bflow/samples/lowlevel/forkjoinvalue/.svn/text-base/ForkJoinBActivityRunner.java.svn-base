package bp.bflow.samples.lowlevel.forkjoinvalue;

import bp.BProgram;
import bp.BThread;
import bp.contrib.BThreadPriorityManager;

public class ForkJoinBActivityRunner {

	public static void main( String args[] ) {
		BProgram bp = new BProgram();
		for ( BThread bt : BStepServers.getServers() ) {
			bp.add(bt, BThreadPriorityManager.nextPriority() );
		}
		ForkJoinValueBActivity fjvb = new ForkJoinValueBActivity();
		bp.add( fjvb, BThreadPriorityManager.nextPriority() );
		
		bp.startAll();
		
	}
}
