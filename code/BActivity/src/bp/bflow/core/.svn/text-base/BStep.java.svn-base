package bp.bflow.core;

import bp.bflow.core.events.BStepFailureEvent;
import bp.bflow.core.events.BStepStartEvent;
import bp.bflow.core.events.BStepSuccessEvent;

/**
 * A step in a {@link BFlow} - this is the "atomic" unit of bactivities.
 * Essentially, a bactivity is a series of BSteps.<br />
 * A {@code BStep} acts as a factory for its start/end events. Two BSteps are equal iff their
 * ids are {@code equal()}.<br />
 * At the moment, equals() does not take the parent BFlowBatch into account
 *  - in case the client code wants to check that they
 * belong to the same BFlowBatch, the client code should explicitly check that.
 * 
 * @author michaelbar-sinai
 */
public class BStep extends BFlowUnit {
	
	public BStep( Object anId ) {
		super( anId );
	}
	
	public BStep( BFlow aParent, Object anId ) {
		super( anId, aParent );
	}
	
	public BStepStartEvent getStartEvent(Object aValue) {
		return new BStepStartEvent(this, aValue );
	}

	public BStepStartEvent getStartEvent() {
		return new BStepStartEvent(this, null );
	}

	public BStepSuccessEvent getSuccessEvent(Object aValue) {
		return new BStepSuccessEvent(this, aValue);
	}
	
	public BStepSuccessEvent getSuccessEvent() {
		return new BStepSuccessEvent(this, null);
	}
	
	public BStepFailureEvent getFailureEvent(Throwable error) {
		return new BStepFailureEvent(this, null, error);
	}
	
	public BStepFailureEvent getFailureEvent() {
		return new BStepFailureEvent(this, null);
	}
	
	@Override
	public String toString() {
		return "[BStep id:" + getId() + "]";
	}

	@Override
	public void acceptSequence(BFlow bast) throws InterruptedException {
		bast.visitStep( this );
	}
	
	@Override
	public boolean equals( Object other ) {
		if ( other instanceof BStep ) {
			Object myId = getId();
			Object otherId = ((BStep)other).getId();
			return ( myId!=null ) ? myId.equals(otherId) : (otherId==null);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		Object myId = getId();
		return ( myId!=null ) ? myId.hashCode() : 0;
	}
	
}
