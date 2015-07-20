package bp.bflow.samples.lowlevel.sanitycheck;

import bp.BProgram;


public class SanityCheckRunner {
	
	public static void main( String[] args ) {
		BProgram bp = new BProgram();
		bp.add( new AStepServer(), 1d);
		bp.add( new BStepServer(), 2d);
		bp.add( new CStepServer(), 3d);
		bp.add( new SanityCheckBActivity(), 4d);
		
		bp.startAll();
	}
}
