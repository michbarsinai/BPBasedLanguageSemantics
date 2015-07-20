package calc.binterpreter.bthreads;

import calc.binterpreter.events.EvaluationErrorEvent;
import bp.exceptions.BPJRequestableSetException;
import static bp.eventSets.EventSetConstants.none;

/**
 * A BThread that reports of an error in the evaluation.
 * 
 * @author michaelbar-sinai
 */
public class EvalErrorBThread extends EvaluationBThread {
	
	private final String message;
	private final Throwable error;
	
	public EvalErrorBThread(Object calcId, String aMessage, Throwable anError ) {
		super(calcId);
		message = aMessage;
		error = anError;
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		bp.bSync( new EvaluationErrorEvent(getReturnId(), message, error), none, none );
	}

}
