package calc.binterpreter.events;

import bp.Event;
import bp.eventSets.EventSetInterface;
import bp.eventSets.EventsOfClass;

/**
 * Base class for all calc-related events. Useful mainly for blocking all of them,
 * e.g. when one wants to pause a calculation.<br />
 * Holds a calculation id (we don't create it here to avoid inter-thread communication not by events).
 * As for unifying with other events, {@code null} values are considered wildcards.
 * @author michaelbar-sinai
 */
public class CalcEvent extends Event {
	private Object calcId;
	
	public static EventSetInterface ALL = new EventsOfClass( CalcEvent.class );
	
	public CalcEvent( Object aCalcId ) {
		this( "CalcEvent", aCalcId );
	}
	
	protected CalcEvent( String name, Object aCalcId ) {
		super( name );
		calcId = aCalcId;
	}
	
	@Override
	public boolean contains(Object obj) {
		return (obj instanceof CalcEvent) 
				&& ( getCalcId()==null || getCalcId().equals(((CalcEvent)obj).getCalcId()) );
	}
	
	public Object getCalcId() {
		return calcId;
	}
	
	@Override
	public String toString() {
		return "[" + getName() + " calcId:" + getCalcId() + "]";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CalcEvent) {
			CalcEvent other = (CalcEvent) obj;
			return getCalcId()!=null ? getCalcId().equals(other.getCalcId()) : other.getCalcId()==null; 
		
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return (getCalcId()!=null) ? getCalcId().hashCode() : 0;
	}
}