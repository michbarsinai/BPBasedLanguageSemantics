package bp.bflow.core.dslhelpers;

import java.util.ArrayList;
import java.util.List;

import bp.BProgram;
import bp.BThread;
import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;
import bp.contrib.BThreadPriorityManager;

public class BFlowSpawner extends BFlow {
	
	private Iterable<BFlowUnit> units;
	
	public BFlowSpawner( BProgram aBp, Iterable<BFlowUnit> unitsToSpawn ){
		super( aBp );
		units = unitsToSpawn;
	}

	@Override
	public void runBFlow() throws InterruptedException {
		List<BThread> bts = new ArrayList<BThread>();
		for ( BFlowUnit bu : units ) {
			final BFlowUnit fBu = bu;
			final BFlow executor = new BFlow( getBProgram() ) {
				@Override
				public void runBFlow() throws InterruptedException {
					fBu.acceptSequence(this);
				}
			};
			executor.setParent( this );
			executor.setExceptingEvents(getExceptingEvents());

			BThread bt = new BThread(){
				{setName("spawn-BThread");}
				@Override
				public void runBThread() throws InterruptedException {
					executor.runBFlow();
				}};
				bt.setName( "[spawn-"
						+ Thread.currentThread()
						+ "/" + fBu + "]");
				getBProgram().add( bt, BThreadPriorityManager.nextPriority() );
				bts.add( bt );
		}
		for ( BThread bt : bts ) {
			bt.startBThread();
		}
	}

	public Iterable<BFlowUnit> getUnits() {
		return units;
	}
	
}
