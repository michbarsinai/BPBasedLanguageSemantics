package org.b_prog.lscobpjv2.events;

import bp.Event;

/**
 * Base class for all events of the LSC over BPJ engine.
 * Main usage is to monitor the engine, and block it in interoperability scenarios.
 * 
 * @author michael
 *
 */
public abstract class LscOverBpjEvent extends Event {

	public LscOverBpjEvent() {
		super();
	}

	public LscOverBpjEvent(String name) {
		super(name);
	}

}
