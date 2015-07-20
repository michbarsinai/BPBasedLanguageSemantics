package bp.bflow.samples.lowlevel.sanitycheckvalue;

import bp.BProgram;
import bp.bflow.samples.lowlevel.valueevents.ResultMonitorBThread;

public class SanityCheckValueRunner {
	
	public static void main( String[] args ) {
		BProgram bp = new BProgram();
		bp.add( new AStepServer(), 1d);
		bp.add( new BStepServer(), 2d);
		bp.add( new CStepServer(), 3d);
		bp.add( new ResultMonitorBThread(), 4d);
		bp.add( new SanityCheckValueBActivity(), 5d);
		
		bp.startAll();
	}
}
