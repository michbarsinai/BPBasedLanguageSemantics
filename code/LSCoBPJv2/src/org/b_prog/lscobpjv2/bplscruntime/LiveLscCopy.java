package org.b_prog.lscobpjv2.bplscruntime;

import bp.BThread;
import static bp.eventSets.BooleanComposableEventSet.*;
import bp.eventSets.EventSetInterface;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.AgentBThread;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.BlockUntilArrival;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.LifelineAgentBthread;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.LiveLscCopyCab;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.SyncCab;
import org.b_prog.lscobpjv2.bplscruntime.agentbthreads.SynchronousMessagePassedCab;
import org.b_prog.lscobpjv2.events.lsc.LscRuntimeEvent;

import org.b_prog.lscobpjv2.events.lsc.LscExitEvent;
import org.b_prog.lscobpjv2.events.lsc.MessagePassedEvent;
import org.b_prog.lscobpjv2.events.lsc.SyncEvent;
import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.LscConstruct;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.Location;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.MessageReceiveLocation;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.MessageSendLocation;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.SyncLocation;

/**
 * A live copy of an {@link LSC}. Provides execution context for a single execution of a given LSC,
 * for all the BThreads that take part in executing it. 
 * 
 * @author michael
 */
public class LiveLscCopy {
    
    private final LSC lsc;
    private final LscExitEvent.Completion completionEvent;
    private final LscExitEvent.Abortion abortionEvent;
    private final EventSetInterface exitEvents;
    private final Map<Location, LscRuntimeEvent> locationEvents = new HashMap<>();
    private final Set<LscRuntimeEvent> allEvents = new HashSet<>();
    private final Set<AgentBThread> agentBThreads = new HashSet<>();
    private String name;
    
    public LiveLscCopy(LSC lsc) {
        this( lsc.getName(), lsc);
    }
    
    public LiveLscCopy(String aName, LSC lsc) {
        this.lsc = lsc;
        name = aName;
        
        completionEvent = new LscExitEvent.Completion(this);
        abortionEvent = new LscExitEvent.Abortion(this);
        exitEvents = theEventSet(abortionEvent).or(completionEvent);
        
    }

    /**
     * Interpret the LSC into a set of BThreads that be run by a BProgram.
     * @return The set of BThreads that will execute the LSC.
     */
    public Set<? extends BThread> interpret() {
        final Set<LscConstruct> visitedConstructs = new HashSet<>();
        
        Location.Visitor<?> builder = new Location.VoidVisitor() {

            @Override
            public void visitImpl(MessageSendLocation msl) {
                MessagePassedEvent mpe = new MessagePassedEvent(msl.getMessage());
                locationEvents.put(msl, mpe);
                
                agentBThreads.add( new BlockUntilArrival(LiveLscCopy.this,
                                                         msl,
                                                         mpe.getEnabledEvent()));
                agentBThreads.add( new SynchronousMessagePassedCab(msl.getMessage(), LiveLscCopy.this) );
            }
            
            @Override
            public void visitImpl(MessageReceiveLocation mrl) {
                MessagePassedEvent mpe = new MessagePassedEvent(mrl.getMessage());
                locationEvents.put(mrl, mpe);
                
                agentBThreads.add( new BlockUntilArrival(LiveLscCopy.this,
                                                         mrl,
                                                         mpe.getEnabledEvent()));
            }

            @Override
            public void visitImpl(SyncLocation syncLocation) {
                SyncEvent syncEvent = new SyncEvent(LiveLscCopy.this, syncLocation.getSync());
                locationEvents.put(syncLocation, syncEvent);
                
                // Block the sync from being enabled until this lifeline arrives.
                agentBThreads.add( new BlockUntilArrival(LiveLscCopy.this,
                                                         syncLocation,
                                                         syncEvent.getEnabledEvent()));
                
                // add sync's CABs (on the first time we see it)
                if ( ! visitedConstructs.contains(syncLocation.getSync()) ) {
                    agentBThreads.add( new SyncCab(syncEvent) );
                    visitedConstructs.add( syncLocation.getSync() );
                }
            }
        };
        
        for ( String id : lsc.lifelineIds() ) {
            Lifeline lifeline = lsc.getLifeline(id);
            for ( Location loc : lifeline.getLocations() ) {
                loc.accept(builder);
            }
            agentBThreads.add( new BlockUntilArrival(this, lifeline.getLastLocation(), getCompletionEvent().getEnabledEvent()));
            agentBThreads.add( new LifelineAgentBthread(lifeline, this) );
        }
        agentBThreads.add(new LiveLscCopyCab(this) );
        
        return agentBThreads;
    }
    
    public LSC getLsc() {
        return lsc;
    }

    /**
     * @return The event of this chart completing successfully.
     */
    public LscExitEvent.Completion getCompletionEvent() {
        return completionEvent;
    }

    /**
     * @return The event of this chart being aborted (e.g due to cold condition violation).
     */
    public LscExitEvent.Abortion getAbortionEvent() {
        return abortionEvent;
    }
    
    /**
     * @return Set of all the events signaling the execution of this chart has ended.
     */
    public EventSetInterface getExitEvents() {
    	return exitEvents;
    }
    
    /**
     * Retrieve the {@link LscRuntimeEvent} associated
     * @param loc The location whose event we seek
     * @return the sought event
     */
    public LscRuntimeEvent getEventFor( Location loc ) {
        return locationEvents.get(loc);
    }
    
    @Override
    public String toString() {
        return "[LiveLscCopy " + name + "]";
    }
}
