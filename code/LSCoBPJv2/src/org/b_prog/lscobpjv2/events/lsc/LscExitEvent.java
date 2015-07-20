package org.b_prog.lscobpjv2.events.lsc;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;

/**
 * Signal LSC exiting. There are two concrete classes for this - termination and completion.
 * @author michael
 */
public abstract class LscExitEvent extends HiddenLscEvent {
    
    
    public LscExitEvent(LiveLscCopy liveCopy) {
        super(liveCopy);
    }

    @Override
    public boolean contains(Object o) {
        return ( getClass().isAssignableFrom(o.getClass())
                 && containsAsHiddenLscEvent((LscExitEvent)o));
    }
    
    public static class Completion extends LscExitEvent {
        public Completion(LiveLscCopy liveCopy) {
            super(liveCopy);
        }
    }
    
    public static class Abortion extends LscExitEvent {
        public Abortion(LiveLscCopy liveCopy) {
            super(liveCopy);
        }
    }
    
    
}
