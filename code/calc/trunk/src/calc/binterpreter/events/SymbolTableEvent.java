package calc.binterpreter.events;

import bp.eventSets.EventSetInterface;
import bp.eventSets.EventsOfClass;

public class SymbolTableEvent extends CalcEvent {

	public static final EventSetInterface ALL = new EventsOfClass( SymbolTableEvent.class );

	public SymbolTableEvent(String symbol) {
		super(symbol);
	}
	
	public String getSymbol() {
		return (String)getCalcId();
	}
}
