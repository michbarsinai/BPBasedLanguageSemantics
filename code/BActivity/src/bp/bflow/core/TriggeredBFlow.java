package bp.bflow.core;

import static bp.eventSets.BooleanComposableEventSet.*;
import static bp.eventSets.EventSetConstants.none;
import bp.Event;
import bp.eventSets.EventSetInterface;

/**
 * Templatic implementation for sequences that are triggered by events. Some properties allow adjusting the 
 * behavior to meet different needs (e.g. Step Server, Corrector thread, etc). 
 * 
 * @author michaelbar-sinai
 */
public abstract class TriggeredBFlow extends BFlow {

	@Override
	public void runBFlow() throws InterruptedException {
		setup();
		
		EventSetInterface evt = none;
		while ( ! getTerminationEventSet().contains( evt ) ) {
			getBProgram().bSync( none, 
								theEventSet( getTriggerEventSet() )
									.or( getExceptingEvents() )
									.or( getTerminationEventSet() ),
								getTerminatedEvent() );
			assertNoExceptingEvents( getBProgram().lastEvent );
			evt = getBProgram().lastEvent;
			
			if ( evt == null ) {
				break;
			} else { 
				if ( getTriggerEventSet().contains( evt ) ) {
					sequenceTriggered( (Event)evt ); // safe, as it was obtained from wakeOn.
					if ( ! isRunAgain() ) break;
					
				} else if ( getTerminationEventSet().contains( evt ) ) {
					break;
				}
			}
			
		}
		
		cleanup();
	}

	/** Hook method, called when the sequence starts. */
	protected void setup() {}

	/** Hook method, called when the sequence ends. */
	protected void cleanup() {}

	/**
	 * @return the event set for which we terminate the bthread.
	 */
	public abstract EventSetInterface getTerminationEventSet();
	
	/**
	 * @return The event set that trigger {@link #sequenceTriggered(Event)}. 
	 */
	public abstract EventSetInterface getTriggerEventSet();
	
	/**
	 * Called when the an event contained in {@link #getTriggerEventSet()} was fired.
	 * This is probably the main method to override, when implementing this class. 
	 * @param evt The event instance that triggered the sequence.
	 * @throws InterruptedException So that is may call bSync related calls.
	 */
	public abstract void sequenceTriggered( Event evt ) throws InterruptedException;
	
	/**
	 * Decides whether we should wait for another event. Called after the {@link #sequenceTriggered(Event)}
	 * method returns.
	 * @return {@code true} iff the BSeqeunce wants to run again, {@code false} otherwise.
	 */
	protected abstract boolean isRunAgain();
}
