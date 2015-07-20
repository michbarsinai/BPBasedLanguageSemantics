package calc.binterpreter.events;

/**
 * Event signalling the BThreads its time to leave the building.
 * 
 * @author michaelbar-sinai
 */
public class CalcSystemShutdownEvent extends CalcEvent {
	
	public static CalcSystemShutdownEvent INSTANCE = new CalcSystemShutdownEvent();
	
	private CalcSystemShutdownEvent() {
		super("SHUTDOWN");
	}

}
