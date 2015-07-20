package incremental.addonbthreads;

import bp.exceptions.BPJRequestableSetException;
import static bp.eventSets.EventSetConstants.none;
import calc.binterpreter.bthreads.CalcBThread;
import calc.binterpreter.events.EvaluationErrorEvent;

/**
 * Prints stack traces when errors happen.
 * 
 * @author michaelbar-sinai
 */
public class StackTracePrinterBThread extends CalcBThread {

	public StackTracePrinterBThread() {
		super("StackTracePrinter");
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		while ( true ) {
			bp.bSync( none, EvaluationErrorEvent.ALL, none );
			System.err.println("Error while evaluating:");
			((EvaluationErrorEvent)bp.lastEvent).asThrowable().printStackTrace( System.err );
		}
	}

}
