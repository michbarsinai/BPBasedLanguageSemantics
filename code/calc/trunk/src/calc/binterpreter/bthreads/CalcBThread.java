package calc.binterpreter.bthreads;

import calc.binterpreter.events.CalcSystemShutdownEvent;
import bp.BThread;

/**
 * Base class for all BThreads in the Calc language. Its main role,
 * other than tagging, is to add the system shutdown event to the interruptingt events.
 * 
 * @author michaelbar-sinai
 */
public abstract class CalcBThread extends BThread {
	
	public CalcBThread( String name ) {
		super(name);
		interruptingEvents = CalcSystemShutdownEvent.INSTANCE;
	}
}
