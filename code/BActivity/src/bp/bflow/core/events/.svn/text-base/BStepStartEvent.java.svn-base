package bp.bflow.core.events;

import bp.bflow.core.BStep;


/**
 * Base class for events that request the launch of a {@link BStep}.
 * 
 * @author michaelbar-sinai
 */
public class BStepStartEvent extends BStepEvent {

	public BStepStartEvent(BStep aStep) {
		super(aStep);
	}
	
	public BStepStartEvent(BStep aStep, Object aValue) {
		super(aStep, aValue);
	}
	
	@Override
	public boolean contains(Object o) {
		if ( o instanceof BStepStartEvent ) {
			return super.contains(o);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[BStepStartEvent step:" + getBStep() + "]";
	}
	
	

}
