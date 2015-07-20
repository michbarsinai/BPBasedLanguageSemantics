package incremental.addonbthreads;

import static bp.eventSets.EventSetConstants.none;

import bp.exceptions.BPJRequestableSetException;
import calc.binterpreter.bthreads.CalcBThread;
import calc.binterpreter.events.ExpressionEvaluatedEvent;

public class RomanNumeralsPrinter extends CalcBThread {
	private static final String[] RCODE = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
	private static final int[]    BVAL  = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

	public RomanNumeralsPrinter() {
		super( "RomanNumeralsPrinter" );
	}

	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		while ( true ) {
			bp.bSync( none, ExpressionEvaluatedEvent.ALL, none );
			System.out.println( "In roman: " + binaryToRoman(((ExpressionEvaluatedEvent)bp.lastEvent).getValue()) );
		}
	}
	

	/**
	 * @author Fred Swartz
	 * @param binary
	 * @return
	 */
	String binaryToRoman(int binary) {
		if (binary <= 0 || binary >= 4000) {
		    throw new NumberFormatException("Value outside roman numeral range.");
		}
		StringBuilder roman = new StringBuilder();         // Roman notation will be accumualated here.
		
		// Loop from biggest value to smallest, successively subtracting,
		// from the binary value while adding to the roman representation.
		for (int i = 0; i < RCODE.length; i++) {
		    while (binary >= BVAL[i]) {
		        binary -= BVAL[i];
		        roman.append(RCODE[i]);
		    }
		}
		return roman.toString();
	}  
}
