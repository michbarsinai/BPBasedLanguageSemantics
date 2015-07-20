package org.b_prog.lscobpjv2.events.lsc;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;

/**
 * An LSC event that only affects the live copy it part of.
 * @author michael
 */
public abstract class HiddenLscEvent extends LscRuntimeEvent {
    
    private final LiveLscCopy llc;

    public HiddenLscEvent(LiveLscCopy llc) {
        this.llc = llc;
    }

    public LiveLscCopy getLiveLscCopy() {
        return llc;
    }
    
    /**
     * @param other The event to possibly be merged.
     * @return {@code true} iff {@code other} event and {@code this} one are on the same {@link LiveLscCopy}
     */
    protected boolean containsAsHiddenLscEvent( HiddenLscEvent other ) {
        return other.getLiveLscCopy().equals( getLiveLscCopy() );
    }
    
}
