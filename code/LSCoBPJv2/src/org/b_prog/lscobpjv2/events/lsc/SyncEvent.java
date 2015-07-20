package org.b_prog.lscobpjv2.events.lsc;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.lsclang.syntax.Sync;

/**
 * Signaling an LSC Sync occuring in a {@link LiveLscCopy}.
 * @author michael
 *
 */
public class SyncEvent extends HiddenLscEvent {
	
	private final Sync sync;

    public SyncEvent(LiveLscCopy aLiveLscCopy, Sync aSync) {
        super( aLiveLscCopy );
        sync = aSync;
        setName( "SyncEvent(" + sync.getName() + ")" );
    }

    public Sync getSync() {
        return sync;
    }

    @Override
    public boolean contains(Object o) {
        if ( o==this ) return true;
        if ( o==null ) return false;
        if ( o instanceof SyncEvent ) {
            SyncEvent evt = (SyncEvent) o;
            return evt.getSync().equals(getSync())
                    && containsAsHiddenLscEvent(evt);
        } else {
            return false;
        }
    }
    
    @Override
    public String toString() {
        return "[SyncEvent " + sync.getName() + "]";
    }
    
}
