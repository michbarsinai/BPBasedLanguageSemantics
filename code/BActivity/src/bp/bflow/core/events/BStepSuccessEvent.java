package bp.bflow.core.events;

import bp.bflow.core.BStep;

/**
 * Event signaling the success of a {@link BStep}.
 * 
 * @author michaelbar-sinai
 */
public class BStepSuccessEvent extends BStepOverEvent {

	public BStepSuccessEvent(BStep aStep) {
		super(aStep);
	}
	
	public BStepSuccessEvent(BStep aStep, Object aValue) {
		super(aStep, aValue);
	}

	@Override
	public boolean contains(Object o) {
		if ( o instanceof BStepSuccessEvent) {
			return super.contains(o);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[BStepSuccessEvent step:" + getBStep() + ", value:" + value + "]";
	}
	
}
