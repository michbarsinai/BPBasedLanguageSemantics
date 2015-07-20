package bp.bflow.samples.highlevel.basicfork;

import bp.bflow.core.BFlow;

/**
 * [a0]--+--[b0]--+--[c0]
 *       |        |
 *       +--[b1]--+
 *       |        |
 *       +--[b2]--+
 * 
 * @author michaelbar-sinai
 */
public class BasicForkBActivity extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		bpExec( bstep("a0"),
				parallel( bstep("b0"),
					  bstep("b1"),
					  bstep("b2")
				),
				bstep("c0") );
	}

}
