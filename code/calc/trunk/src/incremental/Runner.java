package incremental;

import incremental.addonbthreads.BFlowAdapter;
import incremental.addonbthreads.RomanNumeralsPrinter;
import incremental.addonbthreads.StackTracePrinterBThread;

import java.io.IOException;
import java.util.concurrent.BrokenBarrierException;

import calc.binterpreter.CliSessionCtrl;
import bp.BProgram;

public class Runner {
	
	public static void main(String args[]) throws Exception {
		Runner r = new Runner();
//		r.runPlain();
//		r.runWithStackTrace();
//		r.runStackTraceInRome();
		r.runWithBFlow();
	}
	
	public void runPlain() throws IOException, InterruptedException, BrokenBarrierException {
		BProgram bp = new BProgram();
		CliSessionCtrl csc = new CliSessionCtrl();
		csc.runCliSession(bp);
	}
	
	public void runWithStackTrace() throws IOException, InterruptedException, BrokenBarrierException {
		BProgram bp = new BProgram();
		CliSessionCtrl csc = new CliSessionCtrl();
		bp.add( new StackTracePrinterBThread(), CliSessionCtrl.BASE_PRIORITY - 0.1 );
		csc.runCliSession(bp);
	}
	
	public void runStackTraceInRome() throws IOException, InterruptedException, BrokenBarrierException {
		BProgram bp = new BProgram();
		CliSessionCtrl csc = new CliSessionCtrl();
		bp.add( new StackTracePrinterBThread(), CliSessionCtrl.BASE_PRIORITY - 0.1 );
		bp.add( new RomanNumeralsPrinter(), CliSessionCtrl.BASE_PRIORITY - 0.2 );
		csc.runCliSession(bp);
	}
	
	public void runWithBFlow() throws IOException, InterruptedException, BrokenBarrierException {
		BProgram bp = new BProgram();
		CliSessionCtrl csc = new CliSessionCtrl();
		bp.add( new BFlowAdapter(), CliSessionCtrl.BASE_PRIORITY - 0.1 );
		csc.runCliSession(bp);
	}
}