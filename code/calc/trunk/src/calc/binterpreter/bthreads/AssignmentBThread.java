package calc.binterpreter.bthreads;

import static bp.eventSets.EventSetConstants.none;
import calc.binterpreter.events.ExpressionEvaluatedEvent;
import calc.binterpreter.events.StWriteRequest;
import bp.exceptions.BPJRequestableSetException;

public class AssignmentBThread extends EvaluationBThread {
	
	String lhsSymbol;
	Object rhsId;
	public AssignmentBThread(Object aReturnId, String anLhsSymbol, Object anRhsId) {
		super( aReturnId, "Assign " + anLhsSymbol + " to " + anRhsId );
		lhsSymbol = anLhsSymbol;
		rhsId = anRhsId;
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {

		bp.bSync(none, ExpressionEvaluatedEvent.forCalcId(rhsId), none );
		int value = ((ExpressionEvaluatedEvent)bp.lastEvent).getValue();
		
		bp.bSync( new StWriteRequest(lhsSymbol, value), none, none );
		
		bp.bSync( new ExpressionEvaluatedEvent(returnId, value), none, none );

	}

}
