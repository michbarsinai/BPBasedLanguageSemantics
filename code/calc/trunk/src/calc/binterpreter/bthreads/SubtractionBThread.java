package calc.binterpreter.bthreads;

import static bp.eventSets.EventSetConstants.none; 
import calc.binterpreter.events.ExpressionEvaluatedEvent;
import bp.exceptions.BPJRequestableSetException;

public class SubtractionBThread extends BinaryOpEvalBThread {

	public SubtractionBThread(Object aLeftOpId, Object aReturnId, Object aRightOpId) {
		super(aLeftOpId, aReturnId, aRightOpId);
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		bp.bSync(none, ExpressionEvaluatedEvent.forCalcId(getLeftOpId()), ExpressionEvaluatedEvent.forCalcId(getRightOpId()) );
		int left = ((ExpressionEvaluatedEvent)bp.lastEvent).getValue();
		
		bp.bSync(none, ExpressionEvaluatedEvent.forCalcId(getRightOpId()), none );
		int right = ((ExpressionEvaluatedEvent)bp.lastEvent).getValue();
		
		bp.bSync( new ExpressionEvaluatedEvent(getReturnId(), left-right), none, none );
	}


}