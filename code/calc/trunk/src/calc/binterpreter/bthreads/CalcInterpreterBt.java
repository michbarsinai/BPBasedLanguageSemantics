package calc.binterpreter.bthreads;

import java.util.concurrent.atomic.AtomicInteger;
import org.antlr.runtime.tree.CommonTree;

import calc.binterpreter.CliSessionCtrl;
import calc.binterpreter.events.EvaluationErrorEvent;
import calc.binterpreter.events.ExpressionEvaluatedEvent;
import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.BooleanComposableEventSet.*;

import bp.exceptions.BPJRequestableSetException;

public class CalcInterpreterBt extends CalcBThread {
	
	public interface Listener {
		public void done( int value );
		public void error( Throwable t );
		
		public static final Listener NULL = new Listener(){
			@Override public void done(int value) {}
			@Override public void error(Throwable t) {}
		};
		public static final Listener STDOUT = new Listener(){
			@Override public void done(int value) {
				System.out.println("Result: " + value);
			}
			@Override public void error(Throwable t) {
				System.out.println("Error: " + t.getMessage() );
				t.printStackTrace( System.out );
			}
		};
	}
	
	private static final AtomicInteger SERIAL = new AtomicInteger(0);
	
	private final CommonTree ast;
	private final int id = SERIAL.incrementAndGet();
	private int curCalcId = 0;
	private double prio = CliSessionCtrl.BASE_PRIORITY + 1.0;
	private double prioDelta = 0.05;
	
	private Listener listener = Listener.NULL;
	
	public CalcInterpreterBt( CommonTree anAst ) {
		super("CalcInterpreter");
		ast = anAst;
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		Object evalId = evaluateTree( ast );
		bp.bSync(none, anyOf( ExpressionEvaluatedEvent.forCalcId(evalId), EvaluationErrorEvent.ALL ), none);
		if ( bp.lastEvent instanceof ExpressionEvaluatedEvent ) {
			listener.done( ((ExpressionEvaluatedEvent)bp.lastEvent).getValue() );
		} else {
			EvaluationErrorEvent ee = (EvaluationErrorEvent)bp.lastEvent;
			listener.error( ee.asThrowable() );
		}

	}
	
	/**
	 * Creates the BThreads to evaluate the tree.
	 * @param ct the tree to evaluate
	 * @return the return id of the tree root calculation.
	 */
	public Object evaluateTree( CommonTree ct ) {
		if ( isLeaf(ct) ) {
			// corner case - the expression is a single number
			AtomEvaluator ae = new AtomEvaluator(nextCalcId(), ct.getToken().getText() );
			bp.add( ae, nextPrio() );
			ae.startBThread();
			return ae.getReturnId();
			
		} else  {
			EvaluationBThread boeb;
			try {
				String operator = ct.getToken().getText();
				
				if ( operator.equals("+") ) {
					boeb = new AdditionBThread(evaluateTree((CommonTree)ct.getChild(0)),
							nextCalcId(),
							evaluateTree((CommonTree)ct.getChild(1)));
				} else if ( operator.equals("*") ) {
					boeb = new MultiplicationBThread(evaluateTree((CommonTree)ct.getChild(0)),
							nextCalcId(),
							evaluateTree((CommonTree)ct.getChild(1)));
				} else if ( operator.equals("-") ) {
					boeb = new SubtractionBThread(evaluateTree((CommonTree)ct.getChild(0)),
							nextCalcId(),
							evaluateTree((CommonTree)ct.getChild(1)));
				} else if ( operator.equals("=") ) {
					boeb = new AssignmentBThread(nextCalcId(),
							((CommonTree)ct.getChild(0)).getToken().getText(),
							evaluateTree((CommonTree)ct.getChild(1)));
				} else {
					boeb = new EvalErrorBThread(nextCalcId(), "Unknown operator '" + operator + "'", null );
				}
			} catch ( NullPointerException noe ) {
				boeb = new EvalErrorBThread(nextCalcId(), "Unknown AST '" + ct + "'", noe );
			}
			bp.add( boeb, nextPrio() );
			boeb.startBThread();
			return boeb.getReturnId();
		}
		
	}
	
	private boolean isLeaf( CommonTree ct ) {
		return ct.getChildCount()==0;
	}
	
	private String nextCalcId() {
		return id + "/" + (++curCalcId);
	}
	
	public Listener getListener() {
		return listener;
	}
	
	private double nextPrio() {
		prio += prioDelta;
		return prio;
	}
	
	public void setListener(Listener listener) {
		this.listener = listener;
	}
	
}