package calc.binterpreter.bthreads;

import calc.binterpreter.events.EvaluationErrorEvent;
import static bp.eventSets.BooleanComposableEventSet.*;

/**
 * A {@link CalcBThread} that evaluates an expression.
 * Has a return id.
 * 
 * @author michaelbar-sinai
 */
public abstract class EvaluationBThread extends CalcBThread {

	/** Id of the value this thread has to calculate. */
	protected final Object returnId;

	public EvaluationBThread(Object aReturnId) {
		this(aReturnId, "EvaluationBThread");
	}
	
	public EvaluationBThread( Object aReturnId, String name ) {
		super( name );
		returnId = aReturnId;
		interruptingEvents = theEventSet( interruptingEvents ).or( EvaluationErrorEvent.ALL ); 
	}
	
	public Object getReturnId() {
		return returnId;
	}

}
