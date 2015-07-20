package org.b_prog.lscobpjv2.events.lsc;

import org.b_prog.lscobpjv2.events.LscOverBpjEvent;

/**
 * Base class for events of the LSC language. These are also
 * BP events, and create their own "enabled" event.
 * @author michael
 */
public abstract class LscRuntimeEvent extends LscOverBpjEvent {
    
    private LscMetaEvent<LscRuntimeEvent> enabledEvent = null;
    
    /**
     * @return A BP event signaling that the LSC event is on the cut of the live LSC copy.
     */
    public LscMetaEvent<LscRuntimeEvent> getEnabledEvent() {
        if ( enabledEvent == null ) {
            enabledEvent = LscMetaEvent.forEvent(this, "enabled");
        }
        return enabledEvent;
    }
}
