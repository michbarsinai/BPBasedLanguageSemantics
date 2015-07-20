package calc.binterpreter.events;

import bp.eventSets.EventSetInterface;
import bp.eventSets.EventsOfClass;

/**
 * A response to a {@link CalcRequestEvent}. Contains an integer value, which
 *  is the result of the evaluation.
 * 
 * @author michaelbar-sinai
 */
public class ExpressionEvaluatedEvent extends CalcEvent {
	
	public static final EventSetInterface ALL = new EventsOfClass( ExpressionEvaluatedEvent.class );
	
	private Integer value;
	
	public static EventSetInterface forCalcId( Object calcId ) {
		return new ExpressionEvaluatedEvent( calcId );
	}
	
	public ExpressionEvaluatedEvent( Object aCalcId, Integer aValue ) {
		super( "ExpressionEvaluatedEvent", aCalcId );
		value = aValue;
	}
	
	public ExpressionEvaluatedEvent( Object aCalcId ) {
		this( aCalcId, null );
	}
	
	public Integer getValue() {
		return value;
	}
	
	public boolean hasValue() {
		return value!=null;
	}
	
	@Override
	public boolean contains(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.contains(obj)) {
			return false;
		}
		if (!(obj instanceof ExpressionEvaluatedEvent)) {
			return false;
		}
		ExpressionEvaluatedEvent other = (ExpressionEvaluatedEvent) obj;
		return value==null || value.equals(other.getValue());
	}
	
	@Override
	public String toString() {
		return "[ExpressionEvaluatedEvent calcId:" + getCalcId() + " value:" + getValue() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof ExpressionEvaluatedEvent)) {
			return false;
		}
		ExpressionEvaluatedEvent other = (ExpressionEvaluatedEvent) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}

}
