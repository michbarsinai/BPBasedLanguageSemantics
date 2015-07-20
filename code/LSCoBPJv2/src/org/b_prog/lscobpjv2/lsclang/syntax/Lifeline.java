package org.b_prog.lscobpjv2.lsclang.syntax;

import org.b_prog.lscobpjv2.lsclang.syntax.locations.Location;
import java.util.LinkedList;
import java.util.List;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.MessageReceiveLocation;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.MessageSendLocation;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.SyncLocation;

/**
 * An LSC lifeline, following the listing in the paper.
 * Lifelines have to be apart of an {@link LSC}.
 * Thus, to create a new lifeline, call {@link LSC#addNewLifeline(org.b_prog.lscobpjv2.lscatoms.LscClass, java.lang.String)}.
 * 
 * @author michael
 */
public class Lifeline {
    
    private final String id;
    private LSC chart;
    private final LscClass lscClass;
    private final List<Location> locations = new LinkedList<>();

    public Lifeline(String id, LSC chart, LscClass lscClass) {
        this.id = id;
        this.chart = chart;
        this.lscClass = lscClass;
    }
    
    public Lifeline(String id, LscClass lscClass) {
        this(id, null, lscClass );
    }
    
    public void append( Sync sync ) {
        appendLocation( new SyncLocation(sync, this) );
    }
    
    public void appendSend( Message m ) {
    	m.setSender(this);
        appendLocation( new MessageSendLocation(m, this) );
    }
    
    public void appendReceive( Message m ) {
    	m.setReceiver(this);
        appendLocation( new MessageReceiveLocation(m, this) );
    }
    
    protected void appendLocation(Location aLocation) {
        aLocation.setLifeline(this);
        locations.add( aLocation );
    }
    
    public Location getLastLocation() {
        return locations.get( locations.size()-1 );
    }
    
    public List<Location> getLocations() {
        return locations;
    }

    public LSC getChart() {
        return chart;
    }

    public void setChart( LSC anLsc ) {
    	chart = anLsc;
    }
    
    public String getId() {
        return id;
    }

    public LscClass getLscClass() {
        return lscClass;
    }
    
    
}