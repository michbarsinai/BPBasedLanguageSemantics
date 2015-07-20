package org.b_prog.lscobpjv2.lsclang.syntax.locations;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import org.b_prog.lscobpjv2.lsclang.Modality.Temperature;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;

/**
 * A single point on a lifeline in an LSC.
 * @author michael
 */
public abstract class Location {

    public interface Visitor<T> {
        T visit( MessageReceiveLocation mrl );
        T visit( MessageSendLocation msl );
        T visit( SyncLocation sl );
    }
    
    public static abstract class VoidVisitor implements Visitor<Void> {
        
        @Override
        public Void visit( MessageReceiveLocation mrl ) {
            visitImpl( mrl );
            return null;
        }
        
        @Override
        public Void visit( MessageSendLocation msl ) {
            visitImpl( msl );
            return null;
        }
        
        @Override
        public Void visit( SyncLocation sl ) {
            visitImpl( sl );
            return null;
        }
        
        public abstract void visitImpl( MessageReceiveLocation mrl );
        public abstract void visitImpl( MessageSendLocation msl );
        public abstract void visitImpl( SyncLocation sl );
    }
    
    private static final AtomicLong idGenerator = new AtomicLong(0);
    
    private final long id = idGenerator.incrementAndGet();
    private String title;
    private Lifeline lifeline;
    
    public abstract Temperature getTemperature();
    
    public Location( String aTitle, Lifeline aLifeline ) {
        title = aTitle;
        lifeline = aLifeline;
    }
    
    public Location( String anId ) {
        this(anId, null);
    }
    
    public Location() {
        this("loc-" + idGenerator.incrementAndGet() );
    }
    
    public abstract <T> T accept( Visitor<T> visitor );

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getId() {
        return id;
    }

    public Lifeline getLifeline() {
        return lifeline;
    }

    public void setLifeline(Lifeline lifeline) {
        this.lifeline = lifeline;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ( obj == this ) return true;
        if ( obj == null ) return false;
        
        if (!( obj instanceof Location)) {
            return false;
        }
        final Location other = (Location) obj;
        return Objects.equals(this.id, other.id);
    }
    
    @Override
    public String toString() {
        return String.format("<loc %d%s %s>", 
                             getId(), 
                             (getTitle()!=null) ? " "+getTitle() : "",
                             getLifeline().getId());
    }
}
