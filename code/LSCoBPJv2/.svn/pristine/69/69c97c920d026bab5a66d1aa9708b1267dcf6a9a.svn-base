package org.b_prog.lscobpjv2.events;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.lsclang.syntax.locations.Location;

/**
 * The event of a {@line Lifeline} entering a {@link Location} in a {@link LiveLscCopy}.
 * @author michael
 */
public class LocationEntry extends LscOverBpjEvent {
    
    private final Location location;
    private final LiveLscCopy liveLscCopy;
    
    public LocationEntry(LiveLscCopy aLiveLscCopy, Location aLocation) {
        super( "[LocationEntry " + aLiveLscCopy +"/" + aLocation + "]");
        location = aLocation;
        liveLscCopy = aLiveLscCopy;
    }

    @Override
    public boolean contains(Object o) {
    	if ( ! (o instanceof LocationEntry) ) return false;
    	LocationEntry other = (LocationEntry)o;
        return other.getLocation().equals(getLocation())
        		&& other.getLiveLscCopy().equals(getLiveLscCopy());
                
    }
    
    public Location getLocation() {
        return location;
    }
    
    public LiveLscCopy getLiveLscCopy() {
    	return liveLscCopy;
    }
    
}
