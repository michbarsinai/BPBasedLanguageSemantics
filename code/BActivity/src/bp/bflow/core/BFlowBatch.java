package bp.bflow.core;

import bp.BProgram;
import bp.BThread;
import bp.contrib.BThreadPriorityManager;

/**
 * Intuitively, this class models what the system users thinks of as a "Job in the system".
 * As such it may hold some meta data (source file, invoking user id, permissions... ), and is
 * the base objects users will address, e.g when starting/pausing/canceling "Jobs".<br />
 * As such, this class is responsible for starting the actual work by the BPJ system - this is 
 * done by invoking {@link #startBThread()}, which calls the abstract method {@link #runBFlowBatch()}
 * in a newly created {@link BThread}. 
 * 
 * @author michaelbar-sinai
 */
public abstract class BFlowBatch extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		runBFlowBatch();
	}
	
	public abstract void runBFlowBatch() throws InterruptedException;
	
	public BThread startBThread( BProgram bp ) {
		setBProgram( bp );
		BThread bt = new BThread(){

			@Override
			public void runBThread() throws InterruptedException {
				runBFlowBatch();
			}};
			
		bp.add(bt, BThreadPriorityManager.nextPriority() );
		bt.setName("BT for BFlowBatch " + this );
		bt.startBThread();
		
		return bt;
	}
	
}