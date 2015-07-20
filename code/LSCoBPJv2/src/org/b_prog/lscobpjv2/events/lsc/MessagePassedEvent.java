package org.b_prog.lscobpjv2.events.lsc;

import org.b_prog.lscobpjv2.lsclang.syntax.Message;

/**
 *
 * @author michael
 */
public class MessagePassedEvent extends VisibleLscEvent {
    
    private final Message message;

    public MessagePassedEvent(Message message) {
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public boolean contains(Object o) {
        if ( o==this ) return true;
        if ( o==null ) return false;
        if ( ! (o instanceof MessagePassedEvent) ) return false;
        
        MessagePassedEvent other = (MessagePassedEvent) o;
        
        return getMessage().isUnifiable(other.getMessage());
    }
    
    
}