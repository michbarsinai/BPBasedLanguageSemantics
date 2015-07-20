package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import bp.BThread;
import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;

/**
 * Base class for all agent BThreads, such as LABs, CABs etc.
 * 
 * @author michael
 */
public abstract class AgentBThread extends BThread {
    
    AgentBThread(){}
    
    AgentBThread( LiveLscCopy liveCopy ) {
        interruptingEvents = liveCopy.getExitEvents();
    }
    
}
