package bp.bflow.samples.highlevel.excepting;

import bp.bflow.core.BFlowBatch;


public class ExceptingBActivitiesContainer {
	
	/**
	 * <pre><code>
	 *  >--[a]--[b]--[c*]--[d]--x
	 *                 $-[X]--[Y]
	 * </code></pre>
	 */
	public static class SimpleExceptingBFlow extends BFlowBatch {
		@Override
		public void runBFlowBatch() throws InterruptedException {
			bpExec( bstep("a"),
					tryExec( bstep("b"), bstep("c"), bstep("d"))
						.on( bstep("c").getFailureEvent() )
						.bpExec( bstep("X"), bstep("Y")) );
		}
	}
	
	/**
	 * <pre><code>
	 *  >--[a]--[b*]--[c]--x
	 *           $-[X]--[Y*]--[Z]--x
	 *                    $-[AA]--[BB]--x
	 * </code></pre>
	 */
	public static class NestedHandeledExceptingBFlow extends BFlowBatch {

		@Override
		public void runBFlowBatch() throws InterruptedException {
			bpExec( tryExec( 
						tryExec( bstep("a"), bstep("b"), bstep("c"))
							 .on( bstep("b").getFailureEvent())
							 .bpExec(bstep("X"), bstep("Y"), bstep("Z")))
					.on( bstep("Y").getFailureEvent())
					.bpExec( bstep("AA"), bstep("BB")));
			
		}
		
	}
	
	/**
	 * <pre><code>
	 *  >--[a]--[b*]--[c]--x
	 *            $    // won't get done 
	 *            $-[AA]--[BB]--x
	 * </code></pre>
	 */
	public static class NestedExceptingBFlow extends BFlowBatch {

		@Override
		public void runBFlowBatch() throws InterruptedException {
			bpExec( tryExec( 
						tryExec( bstep("a"), bstep("b"), bstep("c"))
							 .on( bstep("c").getFailureEvent())
							 .bpExec(bstep("won't happen")))
					.on( bstep("b").getFailureEvent())
					.bpExec( bstep("AA"), bstep("BB")));
			
		}
		
	}

}
