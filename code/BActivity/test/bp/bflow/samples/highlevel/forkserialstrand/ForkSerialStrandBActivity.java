package bp.bflow.samples.highlevel.forkserialstrand;

import bp.bflow.core.BFlow;

/**
 * <pre><code>
 * [a0]--+--[b0]--------------+--[z0]
 *       |                    |
 *       +--[b1]--[c1]--------+
 *       |                    |
 *       +--[b2]--[c2]--[d2]--+
 * </code></pre>
 * 
 * @author michaelbar-sinai
 */
public class ForkSerialStrandBActivity extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		bpExec( bstep("a0"),
				parallel( bstep("b0"),
							sequence( bstep("b2"), bstep("c2"), bstep("d2")),
							new BFlow( getBProgram() ){
								@Override
								public void runBFlow() throws InterruptedException {
									bpExec( bstep("b1"), bstep("c1") );
								}}
							),
				bstep("z0"));
		
	}
	
}
