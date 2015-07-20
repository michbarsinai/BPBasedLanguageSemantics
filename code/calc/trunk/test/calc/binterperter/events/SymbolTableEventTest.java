package calc.binterperter.events;

import static org.junit.Assert.*;

import org.junit.Test;

import calc.binterpreter.events.StReadRequest;
import calc.binterpreter.events.StReadResponse;
import calc.binterpreter.events.StWriteRequest;
import calc.binterpreter.events.SymbolTableEvent;

public class SymbolTableEventTest {

	@Test
	public void test() {
		assertTrue( SymbolTableEvent.ALL.contains( new StReadRequest("a") ) );
		assertTrue( SymbolTableEvent.ALL.contains( new StWriteRequest("a",9) ) );
		assertTrue( SymbolTableEvent.ALL.contains( new StReadResponse("a",9) ) );
	}

}
