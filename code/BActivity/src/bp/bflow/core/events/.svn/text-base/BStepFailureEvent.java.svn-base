package bp.bflow.core.events;

import bp.bflow.core.BStep;

/**
 * An event decalring that the work on its {@link BStep} failed.
 * 
 * @author michaelbar-sinai
 */
public class BStepFailureEvent extends BStepOverEvent {
	
	private Throwable error;

	public BStepFailureEvent(BStep aStep, Object aValue, Throwable ouch) {
		super(aStep, aValue);
		error = ouch;
	}
	
	public BStepFailureEvent(BStep aStep, Object aValue) {
		this(aStep, aValue, null);
	}
	
	public BStepFailureEvent(BStep aStep) {
		super(aStep);
	}
	
	@Override
	public boolean contains(Object o) {
		if ( o instanceof BStepFailureEvent) {
			if ( getError() != null ) {
				if ( ! getError().equals( ((BStepFailureEvent)o).getError()) ) {
					return false;
				}
			}
			return super.contains(o);
			
		} else {
			return false;
		}
	}

	public Throwable getError() {
		return error;
	}

	public void setError(Throwable error) {
		this.error = error;
	}

	@Override
	public String toString() {
		return "[BStepFailureEvent step:" + getBStep() + ", error:" + error + "]";
	}
	
	
	
}
