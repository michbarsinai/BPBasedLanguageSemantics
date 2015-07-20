package calc.binterpreter.bthreads;

/**
 * A Bthread that evaluates binary operators.
 * 
 * @author michaelbar-sinai
 */
public abstract class BinaryOpEvalBThread extends EvaluationBThread {

	/** Id of the calculation that will yield the value of the left operand. */
	private final Object leftOpId;
	/** Id of the calculation that will yield the value of the right operand. */
	private final Object rightOpId;
	
	public BinaryOpEvalBThread( Object aLeftOpId, Object aReturnId, Object aRightOpId ) {
		super( aReturnId, "CalcBthread[" + aReturnId + "]" );
		leftOpId = aLeftOpId;
		rightOpId = aRightOpId;
	}
	
	public Object getLeftOpId() {
		return leftOpId;
	}

	public Object getRightOpId() {
		return rightOpId;
	}

}
