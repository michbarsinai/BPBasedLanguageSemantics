package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import bp.exceptions.BPJRequestableSetException;
import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.lsc.VisibleLscEvent;

/**
 * CAB dealing w
 * @author michael
 */
public class LiveLscCopyCab extends AgentBThread {
    
    private final LiveLscCopy liveCopy;

    public LiveLscCopyCab(LiveLscCopy liveCopy) {
        this.liveCopy = liveCopy;
    }
    
    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        
        getBProgram().bSync( liveCopy.getCompletionEvent().getEnabledEvent(),
                             liveCopy.getAbortionEvent(),
                             liveCopy.getCompletionEvent() );
        
        if ( liveCopy.getCompletionEvent().getEnabledEvent().contains(getBProgram().lastEvent) ) {
            getBProgram().bSync( liveCopy.getCompletionEvent(),
                				 liveCopy.getAbortionEvent(),
                    			 VisibleLscEvent.ALL );
        }
    }
    
}
