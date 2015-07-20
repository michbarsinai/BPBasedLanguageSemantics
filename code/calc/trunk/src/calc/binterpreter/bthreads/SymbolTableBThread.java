package calc.binterpreter.bthreads;

import java.util.HashMap; 
import java.util.Map;

import calc.binterpreter.events.EvaluationErrorEvent;
import calc.binterpreter.events.StReadRequest;
import calc.binterpreter.events.StReadResponse;
import calc.binterpreter.events.StWriteRequest;
import calc.binterpreter.events.SymbolTableEvent;
import static bp.eventSets.EventSetConstants.none;

import bp.Event;
import bp.exceptions.BPJRequestableSetException;

/**
 * A BThread responsible of the symbol table. Communicate with it using 
 * {@link ReadRequestEvent}, {@link ReadResponseEvent}, and {@link WriteRequestEvent}. 
 * 
 * @author michaelbar-sinai
 */
public class SymbolTableBThread extends CalcBThread {
	
	private Map<String, Integer> values = new HashMap<String, Integer>();
	
	public SymbolTableBThread() {
		super("SymbolTable");
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		System.out.println("Symbol table running");
		Event requestedEvent = null;
		while ( true ) {
			bp.bSync( (requestedEvent!=null) ? requestedEvent : none, SymbolTableEvent.ALL, none);
			requestedEvent = null;
			if ( bp.lastEvent instanceof SymbolTableEvent ) {
				SymbolTableEvent evt = (SymbolTableEvent) bp.lastEvent;
				
				if ( evt instanceof StReadRequest ) {
					Integer value = values.get( evt.getSymbol() );
					requestedEvent = (value != null) ? new StReadResponse(evt.getSymbol(), value) 
													 : new EvaluationErrorEvent(evt.getSymbol(), evt.getSymbol() + " not found");
					
				} else if ( evt instanceof StWriteRequest ) {
					values.put( evt.getSymbol(), ((StWriteRequest)evt).getValue() );
					
				}
			}
		}
		
	}

}
