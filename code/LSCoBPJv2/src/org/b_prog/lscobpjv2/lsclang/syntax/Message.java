package org.b_prog.lscobpjv2.lsclang.syntax;

import java.util.Arrays;
import java.util.List;
import org.b_prog.lscobpjv2.lsclang.Modality;

/**
 * An LSC message, sent from one lifeline to another (they can also be the same, of course).
 * @author michael
 */
public class Message extends ModalLscConstruct {
    private final String methodName;
    private final List<Object> params;
    private Lifeline sender, receiver;

    public Message(String methodName, Modality.Temperature temperature, Modality.Trigger trigger, Object... params) {
        super(temperature, trigger);
        this.methodName = methodName;
        this.params = Arrays.asList(params);
    }
    
    public boolean isUnifiable( Message other ) {
        
        // Short-circuit for trivial case.
        if ( other == this ) return true;
        
        // TODO complete this to support the full message unification spectrum, including 
        // symbolic lifelines and variables.
        
        return ( isUnifiable(getSender(), other.getSender())
                 && isUnifiable(getReceiver(), other.getReceiver())
                 && methodName.equals(other.getMethodName())
                 && params.equals(other.params) );
        
    }
    
    private boolean isUnifiable( Lifeline a, Lifeline b ) {
        // TODO expend to include symbolic lifelines
        return ( a.getId().equals(b.getId())
                    && a.getLscClass().equals(b.getLscClass()));
    }
    
    public String getMethodName() {
        return methodName;
    }

    public List<Object> getParams() {
        return params;
    }

    public Lifeline getSender() {
        return sender;
    }

    public void setSender(Lifeline aSender) {
        if ( sender != null ) {
            throw new IllegalStateException("Message sender already set");
        }
        sender = aSender;
    }

    public Lifeline getReceiver() {
        return receiver;
    }

    public void setReceiver(Lifeline aReceiver) {
        if ( receiver != null ) {
            throw new IllegalStateException("Message receiver already set");
        }
        receiver = aReceiver;
    }
    
}
