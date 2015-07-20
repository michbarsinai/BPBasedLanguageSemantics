package bp.bflow.samples.highlevel.basiccorrective;

import bp.bflow.core.BFlowBatch;

/**
 * <code><pre>
 * 
 * -->[A]--[B]--[C]
 * 
 * </pre></code>
 * 
 * @author michaelbar-sinai
 */
public class CorrectableBActivity extends BFlowBatch {

	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( bstep("A"), bstep("B"), bstep("C") );
	}

}
