package org.b_prog.lscobpjv2.lsclang.syntax;

import java.util.Objects;
import org.b_prog.lscobpjv2.lsclang.Modality;

/**
 * Base class for LSC constructs. Defines all that is common to them.
 * @author michael
 */
public class ModalLscConstruct extends LscConstruct {
    
    private final Modality.Temperature temperature;
    private final Modality.Trigger trigger;

    public ModalLscConstruct(Modality.Temperature temperature, Modality.Trigger trigger) {
        this.temperature = temperature;
        this.trigger = trigger;
    }

    public Modality.Temperature getTemperature() {
        return temperature;
    }

    public Modality.Trigger getTrigger() {
        return trigger;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.temperature);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if ( ! (obj instanceof ModalLscConstruct) ) {
            return false;
        }
        final ModalLscConstruct other = (ModalLscConstruct) obj;
        if (this.temperature != other.temperature) {
            return false;
        }
        return this.trigger == other.trigger;
    }
    
    
    
}
