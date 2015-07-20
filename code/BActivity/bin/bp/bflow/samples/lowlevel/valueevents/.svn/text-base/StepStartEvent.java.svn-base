package bp.bflow.samples.lowlevel.valueevents;

public class StepStartEvent extends ValueEvent {

	public StepStartEvent(Double aValue, String aStepName) {
		super(aValue, aStepName);
	}
	
	public StepStartEvent(String aStepName) {
		this(null, aStepName);
	}
	
	/**
	 * This event is equal to other events that request the end of the same step.
	 */
	@Override
	public boolean contains(Object o) {
		if ( o instanceof StepStartEvent ) {
			return ((StepStartEvent)o).getStepName().equals(getStepName());
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "StepStartRequest [stepName=" + getStepName() + " value=" + getValue() +"]";
	}
	

}
