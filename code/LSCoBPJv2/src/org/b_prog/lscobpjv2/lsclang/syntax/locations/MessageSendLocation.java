package org.b_prog.lscobpjv2.lsclang.syntax.locations;

import org.b_prog.lscobpjv2.lsclang.syntax.Message;
import org.b_prog.lscobpjv2.lsclang.Modality;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;

/**
 *
 * @author michael
 */
public class MessageSendLocation extends Location {
    private final Message message;

    public MessageSendLocation(Message message, Lifeline aLifeline) {
        super( aLifeline.getId() + "-send-" + message.getMethodName(), aLifeline);
        this.message = message;
    }

    public Message getMessage() {
        return message;
    }

    @Override
    public Modality.Temperature getTemperature() {
        return getMessage().getTemperature();
    }
        
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }

}
