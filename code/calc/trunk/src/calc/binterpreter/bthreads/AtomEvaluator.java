package calc.binterpreter.bthreads;

import static bp.eventSets.EventSetConstants.none;

import java.util.regex.Pattern;

import calc.binterpreter.events.ExpressionEvaluatedEvent;
import calc.binterpreter.events.StReadRequest;
import calc.binterpreter.events.StReadResponse;
import bp.exceptions.BPJRequestableSetException;

public class AtomEvaluator extends EvaluationBThread {
	
	private String text;
	private Pattern digits = Pattern.compile("[0-9]+");
	
	public AtomEvaluator( Object aReturnId, String aText ) {
		super( aReturnId, "AtomEvaluator" );
		text = aText;
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		int value;
		if ( isNumber(text) ) {
			// number literal, evaluate
			value = Integer.valueOf( text );
			
		} else {
			// variable name, read
			bp.bSync( new StReadRequest(text), none, none );
			bp.bSync( none, StReadResponse.forSymbol(text), none );
			value = ((StReadResponse)bp.lastEvent).getValue(); // TODO handle missing values
		}
		bp.bSync( new ExpressionEvaluatedEvent(returnId, value), none, none);
	}

	boolean isNumber( String text ) {
		return digits.matcher(text).matches();
	}
}
