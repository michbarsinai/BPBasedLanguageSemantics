package bp.bflow.samples.highlevel.combinations;

import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowBatch;

/**
 * See that we can run bflows in parallel, from another bflow.
 * 
 * @author michaelbar-sinai
 */
public class CombiningBActivity extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		bpExec( parallel(new BAct1(), new BAct2()) );
	}

}


/** --[1A]--x */
class BAct1 extends BFlowBatch {

	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( bstep("1A") );
	}
	
}

/** 
 *        +--[2B0]--+---x
 * --[2A]-+         |
 *        +--[2B1]--+
 *
 */
class BAct2 extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		bpExec( bstep("2A"),
				parallel( bstep("2B0"),
						  bstep("2B1") ) );
		
	}
	
}