package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import static bp.eventSets.EventSets.none;
import bp.exceptions.BPJRequestableSetException;
import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.lsc.MessagePassedEvent;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;

/**
 * 
 * @author michael
 */
public class SynchronousMessagePassedCab extends AgentBThread {
    
    private final Message message;
    
    public SynchronousMessagePassedCab( Message aMessage, LiveLscCopy liveCopy ) {
        super( liveCopy );
        message = aMessage;
    }
    
    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        MessagePassedEvent evt = new MessagePassedEvent(message);
        getBProgram().bSync( evt.getEnabledEvent(), none(), evt );
        
        switch ( message.getTrigger() ) {
            case Execution:
                getBProgram().bSync( evt, none(), none() );
                break;
            case Monitoring:
                getBProgram().bSync( none(), evt, none() );
                break;
        } 
    }
    
}
