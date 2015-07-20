package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import static bp.eventSets.EventSets.none;
import bp.exceptions.BPJRequestableSetException;
import org.b_prog.lscobpjv2.events.lsc.SyncEvent;
import org.b_prog.lscobpjv2.events.lsc.VisibleLscEvent;

/**
 * Construct Agent BThread of a Sync.
 * @author michael
 */
public class SyncCab extends AgentBThread {
    
    private final SyncEvent syncEvent;

    public SyncCab(SyncEvent syncEvent) {
        super( syncEvent.getLiveLscCopy() );
        this.syncEvent = syncEvent;
        setName("[SyncCab " + syncEvent.getSync().getName() + "]");
    }

    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        
        getBProgram().bSync( syncEvent.getEnabledEvent(),
                             none(), 
                             syncEvent );
        
        getBProgram().bSync(syncEvent, none(), VisibleLscEvent.ALL);
    }
    
}
