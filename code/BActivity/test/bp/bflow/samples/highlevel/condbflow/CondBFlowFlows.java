package bp.bflow.samples.highlevel.condbflow;

import java.util.concurrent.Callable;

import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;

public class CondBFlowFlows {

	public static BFlowUnit makeFlow( final String value ) {
		return new BFlow(){
			@Override
			public void runBFlow() throws InterruptedException {
				bpExec( cond( callable( value ) )
						.eq("A").bpExec( bstep("A") )
						.eq("B").bpExec( bstep("B") )
						.elseBpExec( bstep("OTHER") )
				);
			}};
	}
	
	static <T> Callable<T> callable( final T t ) {
		return new Callable<T>(){
			@Override
			public T call() throws Exception {
				return t;
			}};
	}
	
}
