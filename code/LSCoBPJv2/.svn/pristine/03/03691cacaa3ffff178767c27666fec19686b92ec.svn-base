package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import bp.eventSets.EventSetInterface;
import bp.eventSets.EventSets;
import bp.exceptions.BPJRequestableSetException;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.LocationEntry;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.Location;

/**
 *
 * @author michael
 */
public class BlockUntilArrival extends AgentBThread {
    
    private final Location location;
    private final LiveLscCopy llc;
    private final EventSetInterface events;

    public BlockUntilArrival(LiveLscCopy aLiveLscCopy, Location aLocation, EventSetInterface someEvents) {
        this.location = aLocation;
        llc = aLiveLscCopy;
        this.events = someEvents;
    }
    
    @Override
    public void runBThread() throws InterruptedException, BPJRequestableSetException {
        getBProgram().bSync( EventSets.none(), new LocationEntry(llc, location), events);
    }
    
}
