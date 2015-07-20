package calc.binterpreter.events;

public class StReadRequest extends SymbolTableEvent {

	public StReadRequest( String symbol ) {
		super(symbol);
		setName( "Read " + symbol );
	}

	@Override
	public boolean contains( Object other ) {
		return ( other instanceof StReadRequest ) 
			? super.contains( other )
			: false;
	}
}
