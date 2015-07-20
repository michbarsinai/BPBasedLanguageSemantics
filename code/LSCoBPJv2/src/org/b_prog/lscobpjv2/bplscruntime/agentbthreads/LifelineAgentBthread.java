package org.b_prog.lscobpjv2.bplscruntime.agentbthreads;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.LocationEntry;
import org.b_prog.lscobpjv2.events.lsc.LscRuntimeEvent;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.Location;

import bp.exceptions.BPJRequestableSetException;
import static bp.eventSets.EventSets.*;
import static bp.eventSets.BooleanComposableEventSet.*;

/**
 * The bthread acting on behalf of a lifeline in a live copy of an LSC.
 * 
 * @author michael
 *
 */
public class LifelineAgentBthread extends AgentBThread {
	
	private final Lifeline lifeline;
	private final LiveLscCopy liveCopy;
	
	public LifelineAgentBthread( Lifeline aLifeline, LiveLscCopy aLiveCopy ) {
        super( aLiveCopy );
		lifeline = aLifeline;
		liveCopy = aLiveCopy;
	}
	
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
				
		for ( Location currentLocation : lifeline.getLocations() ) {
            System.out.println("-- " + lifeline.getId() + " requests Entry to: " + currentLocation );
			getBProgram().bSync(new LocationEntry(liveCopy, currentLocation),
								none(),
								ofClass(LscRuntimeEvent.class) );
            
            System.out.println("-- " + lifeline.getId() + " at: " + currentLocation );
			getBProgram().bSync( none(),
								 theEventSet(liveCopy.getEventFor(currentLocation))
                                    .or(liveCopy.getExitEvents()),
								 none() );
            
		}
		
        System.out.println( toString() + " done" );
	}
    
    @Override
    public String toString() {
        return "[LAB " + liveCopy + "/" + lifeline.getId() + "]";
    }
}
