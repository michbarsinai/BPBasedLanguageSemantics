package calc.binterpreter.bthreads;

import calc.binterpreter.events.ExpressionEvaluatedEvent;
import bp.exceptions.BPJRequestableSetException;
import static bp.eventSets.EventSetConstants.none;

public class MultiplicationBThread extends BinaryOpEvalBThread {

	public MultiplicationBThread(Object aLeftOpId, Object aReturnId, Object aRightOpId) {
		super(aLeftOpId, aReturnId, aRightOpId);
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		bp.bSync(none, ExpressionEvaluatedEvent.forCalcId(getLeftOpId()), ExpressionEvaluatedEvent.forCalcId(getRightOpId()) );
		int left = ((ExpressionEvaluatedEvent)bp.lastEvent).getValue();
		
		bp.bSync(none, ExpressionEvaluatedEvent.forCalcId(getRightOpId()), none );
		int right = ((ExpressionEvaluatedEvent)bp.lastEvent).getValue();
		
		bp.bSync( new ExpressionEvaluatedEvent(getReturnId(), left*right), none, none );
	}

}
