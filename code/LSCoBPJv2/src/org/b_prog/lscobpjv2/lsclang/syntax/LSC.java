package org.b_prog.lscobpjv2.lsclang.syntax;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * A an LSC (live sequence chart). This object covers the static aspects of
 * the chart - to get an live copy of an LSC, invoke {@link #activate()}.
 * 
 * @author michael
 */
public class LSC {
    
    private static AtomicInteger COUNT = new AtomicInteger();
    private String name;
    
    public LSC( String aName ) {
        name = aName;
    }
    
    public LSC() {
        this("LSC-" + COUNT.incrementAndGet());
    }
    
    /**
     * Lifelines participating in this LSC.
     */
    private final Map<String, Lifeline> lifelines = new TreeMap<>();
    
    public Lifeline addNewLifeline( String anId, LscClass aClass ) {
        Lifeline theNewLifeline = new Lifeline(anId, this, aClass);
        lifelines.put(anId, theNewLifeline);
        return theNewLifeline;
    }
    
    public LSC addLifeline( Lifeline aLifeline ) {
    	lifelines.put( aLifeline.getId(), aLifeline );
    	aLifeline.setChart(this);
    	return this;
    }
    
    public Set<String> lifelineIds() {
        return lifelines.keySet();
    }
    
    public Lifeline getLifeline( String id ) {
        return lifelines.get( id );
    }
    
    @Override
    public String toString() {
        return "[LSC " + name + "]";
    }

    public String getName() {
        return name;
    }
    
    public void setName( String aName ) {
    	name = aName;
    }
    
}
