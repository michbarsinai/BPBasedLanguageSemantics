package calc.binterpreter.events;

public class StWriteRequest extends SymbolTableEvent {

	Integer value;
	
	public StWriteRequest(String symbol, Integer aValue ) {
		super(symbol);
		value = aValue;
		setName("WriteRequest " + symbol + "=" + value );
	}
	
	public Integer getValue() {
		return value;
	}

}
