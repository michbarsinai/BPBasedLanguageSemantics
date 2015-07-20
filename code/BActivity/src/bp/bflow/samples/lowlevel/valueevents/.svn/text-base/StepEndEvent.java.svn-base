package bp.bflow.samples.lowlevel.valueevents;

public class StepEndEvent extends ValueEvent {
	
	public StepEndEvent(Double aValue, String aStepName ) {
		super(aValue, aStepName);
	}
	
	public StepEndEvent( String aStepName ) {
		this( null, aStepName );
	}
	
	/**
	 * This event is equal to other events the request the end of the same step.
	 */
	@Override
	public boolean contains(Object o) {
		if ( o instanceof StepEndEvent ) {
			return ((StepEndEvent)o).getStepName().equals(getStepName());
		} else {
			return false;
		}
		
	}


	@Override
	public String toString() {
		return "StepEndRequest [stepName=" + getStepName() + " value=" + getValue() +"]";
	}
	
}
