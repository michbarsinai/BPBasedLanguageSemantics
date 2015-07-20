package bp.bflow.core;

import bp.Event;

/**
 * Thrown when an event that was contained in {@link BFlow#getExceptingEvents()} set was fired.
 * 
 * @author michaelbar-sinai
 */
public class ExceptingEventFiredException extends RuntimeException {
	
	private final Event firedEvent;

	public ExceptingEventFiredException(Event firedEvent) {
		this.firedEvent = firedEvent;
	}
	
	public ExceptingEventFiredException(ExceptingEventFiredException cause, Event firedEvent) {
		super( cause );
		this.firedEvent = firedEvent;
	}

	public Event getFiredEvent() {
		return firedEvent;
	}

	@Override
	public String toString() {
		return "ExceptingEventFiredException [firedEvent=" + firedEvent + "]";
	}
	
	@Override
	public ExceptingEventFiredException getCause() {
		return (ExceptingEventFiredException) super.getCause();
	}
	
}
