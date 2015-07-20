package bp.bflow.core.dslhelpers;

import java.util.Arrays;

import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;

/**
 * A flow that has some sub-flows, and executes them serially. 
 */
public class SequentialBFlow extends BFlow {
	
	private Iterable<BFlowUnit> units;
	
	public SequentialBFlow( BFlowUnit...activityUnits ) {
		units = Arrays.asList(activityUnits);
	}
	
	public SequentialBFlow(Iterable<BFlowUnit> units) {
		this.units = units;
	}

	@Override
	public void runBFlow() throws InterruptedException {
		for ( BFlowUnit unit : units ) {
			unit.acceptSequence(this);
		}
	}

	public Iterable<BFlowUnit> getUnits() {
		return units;
	}
	
	@Override
	public String toString() {
		return "[Sequence@" + Integer.toHexString(hashCode()) +"]";
	}
}
