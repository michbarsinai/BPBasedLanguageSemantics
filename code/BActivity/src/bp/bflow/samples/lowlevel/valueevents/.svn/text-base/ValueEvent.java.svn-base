package bp.bflow.samples.lowlevel.valueevents;

import bp.Event;

/** 
 * An event that has a value, possibly a result of a computation by a BStepServer.
 * @author michaelbar-sinai
 */
public class ValueEvent extends Event {
	
	private Double value;
	protected String stepName;
	
	public ValueEvent( Double aValue, String aStepName ) {
		value = aValue;
		stepName = aStepName;
	}
	
	public Double getValue() {
		return value;
	}
	
	public void setValue( Double aValue ) {
		value = aValue;
	}

	@Override
	public String toString() {
		return "ValueEvent [getValue()=" + getValue() + "]";
	}

	public String getStepName() {
		return stepName;
	}

}
