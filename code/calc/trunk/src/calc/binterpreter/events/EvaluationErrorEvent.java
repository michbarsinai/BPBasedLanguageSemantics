package calc.binterpreter.events;

import bp.eventSets.EventSetInterface;
import bp.eventSets.EventsOfClass;

/**
 * An event raised by a bthread that got an error while evaluating an expression.
 * 
 * @author michaelbar-sinai
 */
public class EvaluationErrorEvent extends CalcEvent {

	private Throwable cause;
	private String message;
	
	public static final EventSetInterface ALL = new EventsOfClass( EvaluationErrorEvent.class );
	
	public EvaluationErrorEvent(Object aCalcId, String aMessage, Throwable aCause) {
		super("Error: " + aMessage, aCalcId);
		message = aMessage;
		cause = aCause;
	}
	
	public EvaluationErrorEvent(Object aCalcId, String aMessage ) {
		this( aCalcId, aMessage, null );
	}
	
	public Throwable asThrowable() {
		return new Exception(getMessage(), getCause());
	}
	
	public String getMessage() {
		return message;
	}
	
	public Throwable getCause() {
		return cause;
	}
}
