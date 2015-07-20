package calc.binterpreter;

import static bp.eventSets.EventSetConstants.none;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.runtime.CommonTokenStream;
import org.antlr.runtime.RecognitionException;
import org.antlr.runtime.tree.CommonTree;

import bp.BProgram;
import bp.BThread;
import bp.exceptions.BPJRequestableSetException;
import calc.binterpreter.bthreads.SymbolTableBThread;
import calc.binterpreter.events.CalcSystemShutdownEvent;
import calc.binterpreter.bthreads.CalcInterpreterBt;
import calc.grammar.ExprLexer;
import calc.grammar.ExprParser;


/**
 * Another option: make each application of reach operator a bthread on its own.
 * They can all start as we parse them, but they will only run when the
 * values they need are available.
 * This will actually work with a "narrator" bthread.
 * 
 * @author michaelbar-sinai
 */
public class CliSessionCtrl implements CalcInterpreterBt.Listener {
	
	public static double BASE_PRIORITY = 1.0;
	private BProgram systemBProgram;
	private final CyclicBarrier barrier = new CyclicBarrier(2);
	
	public static void main( String[] args ) throws IOException, RecognitionException, InterruptedException, BrokenBarrierException {
		CliSessionCtrl main = new CliSessionCtrl();
		main.runCliSession( new BProgram() );
	}
	
	private void systemShutdown() {
		BThread shutdown = new BThread("system-shutdown"){
			@Override
			public void runBThread() throws InterruptedException, BPJRequestableSetException {
				bp.bSync( CalcSystemShutdownEvent.INSTANCE, none, none);
			}};
		systemBProgram.add( shutdown, 0d );
		shutdown.startBThread();
	}

	private void systemSetup() {
		
		systemBProgram.add( new SymbolTableBThread(), 0.1d );
		systemBProgram.startAll();
		
		System.out.println("RWB-based parser for integer calculations");
		System.out.println("Write arithmetic expressions, using numbers");
		System.out.println("and + - * ()");
		System.out.println("Assign values to variables using a=(expression)");
		System.out.println("Then you can use the variables normally (a+4-b)aa");
		System.out.println("<enter>: evaluate");
		System.out.println("bye: exit");
		
	}

	public void runCliSession( BProgram aBp ) throws IOException, InterruptedException, BrokenBarrierException {
		systemBProgram = aBp;
		
		systemSetup();
		BufferedReader cons = new BufferedReader( new InputStreamReader(System.in) );
		String line;
		double prio = 200.0;
		double delta = 0.01;
		System.out.print( ">> " );
		
		while ( (line=cons.readLine()) != null ) {
			if ( "bye".equalsIgnoreCase(line) ) break;
			
			line = line + "\n";
			try {
				ANTLRStringStream input = new ANTLRStringStream(line);
		        ExprLexer lexer = new ExprLexer(input);
		        CommonTokenStream tokens = new CommonTokenStream(lexer);
		        ExprParser parser = new ExprParser(tokens);
		        ExprParser.prog_return r = parser.prog();
		        System.out.println( r );
	
		        CommonTree tree = (CommonTree) r.getTree();
		        CalcInterpreterBt itrptr = new CalcInterpreterBt(tree);
		        systemBProgram.add( itrptr, prio );
		        itrptr.setListener( this );
		        itrptr.startBThread();
		        
		        barrier.await();
		        prio += delta;
		        
			} catch ( RecognitionException err ) {
				System.out.println( "Parse error: " + err.getMessage() );
			}
			
	        System.out.print( ">> " );	       
		}
		systemShutdown();
		System.out.println("Bye!");
	}

	@Override
	public void done(int value) {
		System.out.println( "=> " + value);
		try {
			barrier.await();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void error(Throwable t) {
		System.out.println("Error while evaluating: " + t.getMessage() );
		try {
			barrier.await();
		} catch ( Exception e) {
			e.printStackTrace();
		}
	}
	
}
