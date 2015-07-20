package org.b_prog.lscobpjv2.lsclang.syntax.locations;

import org.b_prog.lscobpjv2.lsclang.Modality;
import static org.b_prog.lscobpjv2.lsclang.Modality.Temperature.Cold;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.Sync;

/**
 * @author michael
 */
public class SyncLocation extends Location {
    
    private final Sync sync;

    public SyncLocation(Sync sync, Lifeline ll) {
        super( ll.getId() + "@" + sync.getName(), ll);
        this.sync = sync;
    }
    
    @Override
    public Modality.Temperature getTemperature() {
        return Cold;
    }

    public Sync getSync() {
        return sync;
    }
    
    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visit(this);
    }
    
}
