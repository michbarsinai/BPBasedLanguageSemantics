package bp.bflow.analysis.graphviz;

import java.util.concurrent.Callable;

import bp.bflow.core.BFlow;
import bp.bflow.core.dslhelpers.SequentialBFlow;

public class GraphvizBFlowCharterTester extends BFlow {
	
	public static void main( String[] args ) {
		new GraphvizBFlowCharterTester().test();
	}
	
	public void test() {
		GraphvizBFlowCharter c = new GraphvizBFlowCharter(System.out);
		
		c.emitHeader();
		c.chart( new SequentialBFlow( bstep("a0"),
									  parallel( bstep("b0"),
											   sequence( bstep("b2"), bstep("c2"), bstep("d2"))),
									  cond( getCond() )
									  .eq("A").bpExec( bstep("option A") )
									  .eq("B").bpExec( sequence(bstep("option Bi"), bstep("option Bii")) )
									  .elseBpExec( broadcast( bstep("ouch").getStartEvent()) )) );
		c.emitFooter();
	}

	private Callable<String> getCond() {
		return new Callable<String>(){

			@Override
			public String call() throws Exception {
				return "A";
			}
			
			@Override
			public String toString() { return "test"; }
		};
	}
	
	@Override
	public void runBFlow() throws InterruptedException {
		// TODO Auto-generated method stub
	}
}
