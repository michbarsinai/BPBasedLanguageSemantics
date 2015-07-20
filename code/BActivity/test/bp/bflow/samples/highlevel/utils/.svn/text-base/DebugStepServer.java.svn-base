package bp.bflow.samples.highlevel.utils;

import bp.Event;
import bp.bflow.core.BStep;
import bp.bflow.core.BStepServer;

public class DebugStepServer extends BStepServer<Object> {
	
	static volatile int WORK_DURATION = 250;
	
	public DebugStepServer(BStep step) {
		super(step);
	}

	@Override
	protected Object serveStep(Event evt) throws Exception {
		System.out.println("[DebugStepServer " + getStep().toString() + "] starting " + getCurrentStep().toString() );
		Thread.sleep( WORK_DURATION ); 
		System.out.println("[DebugStepServer " + getStep().toString() + "] finished " + getCurrentStep().toString() );
		return "{BStep " + getCurrentStep().toString() + " was served}";
	}

}
