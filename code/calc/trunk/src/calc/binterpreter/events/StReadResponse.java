package calc.binterpreter.events;

public class StReadResponse extends SymbolTableEvent {

	public static StReadResponse forSymbol( String symbol ) {
		return new StReadResponse(symbol, null);
	}
	
	Integer value;
	
	public StReadResponse( String symbol, Integer aValue ) {
		super(symbol);
		value = aValue;
		setName("ReadResponse " + symbol + "=" + value );
	}

	public Integer getValue() {
		return value;
	}
	
	@Override
	public boolean contains(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.contains(obj)) {
			return false;
		}
		if (!(obj instanceof StReadResponse)) {
			return false;
		}
		StReadResponse other = (StReadResponse) obj;
		return value==null || value.equals( other.getValue() );
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof StReadResponse)) {
			return false;
		}
		StReadResponse other = (StReadResponse) obj;
		if (value == null) {
			if (other.value != null) {
				return false;
			}
		} else if (!value.equals(other.value)) {
			return false;
		}
		return true;
	}
	
	
}
