package bp.bflow.samples.highlevel.beeriprintcasestudy.passbyhistory;

import bp.bflow.core.BFlowBatch;

/**
 * 
 * <pre><code>
 *            +--[EM 1]--[EM 2]--[SendMails()]--+
 *            |                                 |
 * --[Split]--+--+--[Bulk]------+--[Print]------+--[invoice]--x
 *               |              |
 *               +--[P1]--[P2]--+
 * 
 * </code></pre>
 * 
 * @author michaelbar-sinai
 */
public class BeeriprintPbhCsBActivity extends BFlowBatch {

	@Override
	public void runBFlowBatch() throws InterruptedException {
		bpExec( bstep("splitByDistroChannel"),
				parallel(
						sequence( bstep("EM-1"), bstep("EM-2"), spawn(new SendEmailsBFlow())),
						sequence( parallel( bstep("bulk"), 
											sequence( bstep("P1"), bstep("P2") )),
								  bstep("print"))),
				bstep("invoice") 
		);
	}

}


class SendEmailsBFlow extends BFlowBatch {

	@Override
	public void runBFlowBatch() throws InterruptedException {
		System.out.println("[SendEmailsBActivity] Sending emails...");
		System.out.println("[SendEmailsBActivity] ...DONE");
	}
	
}