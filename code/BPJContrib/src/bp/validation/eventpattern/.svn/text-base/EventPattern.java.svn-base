package bp.validation.eventpattern;

import java.util.ArrayList;
import java.util.List;

import bp.Event;
import bp.eventSets.EventSetInterface;

/**
 * Matches a list of events against a glob-like pattern.
 * @see {@linkplain http://en.wikipedia.org/wiki/Glob_(programming)}
 * *
 * ?
 * literals
 * 
 * @author michaelbar-sinai
 */
public class EventPattern {
	
	/** Must be an array list, as we may do lots of random access. */
	private ArrayList<EventPatternPart> glob = new ArrayList<EventPatternPart>();
	
	public EventPattern(){}
	
	public boolean matches( List<? extends Event> eventList ) {
		return matches( eventList, 0 );
	}
	
	private boolean matches( List<? extends Event> eventList, int globIdx) {
		// See if we're at the end of the glob or the list.
		if ( globIdx==glob.size() ) { 
			return eventList.isEmpty();
		}
		if ( eventList.isEmpty() ) {
			// see that the end of the glob may match an empty list.
			for ( ; globIdx < glob.size(); globIdx++ ) {
				if ( glob.get(globIdx).getMinRepeats() != 0 ) return false;
			}
			return true;
		}
		
		EventPatternPart curPart = glob.get(globIdx);
		if ( curPart.isMultiChar() ) {
			for ( int groupSize=curPart.getMinRepeats()
					; (groupSize<=eventList.size())
					  && (curPart.getMaxRepeats()==null || groupSize < curPart.getMaxRepeats() ) 
					; groupSize++ ) {
				if ( matches(eventList.subList(groupSize, eventList.size()), globIdx+1 ) ) {
					return true;
				}
			}
			return false;
			
		} else {
			if ( curPart.matches(eventList.get(0)) ) {
				return matches( eventList.subList(1, eventList.size()), globIdx+1);
			} else {
				return false;
			}
		}
		
	}
	
	/**
	 * Appends a single occurrence of {@code esi} to the pattern.
	 * 
	 * Convenience call to {@code append(esi,1,1)}.
	 * @param esi the EventSetInterface that should occur.
	 * @return {@code this}, to allow call chaining.
	 * @see #append(EventSetInterface, int, Integer)
	 */
	public EventPattern append( EventSetInterface esi ) {
		return append( esi, 1, 1);
	}
	
	/**
	 * Appends a zero-or-more occurrence of {@code esi} to the pattern.
	 * (Kleene's star).
	 * 
	 * Convenience call to {@code append(esi,0,null)}.
	 * @param esi the EventSetInterface that should occur.
	 * @return {@code this}, to allow call chaining.
	 * @see #append(EventSetInterface, int, Integer)
	 */
	public EventPattern appendStar( EventSetInterface esi ) {
		return append( esi, 0, null );
	}
	
	/**
	 * Appends possible occurrence(s) of {@code esi} to the pattern.
	 * 
	 * @param minRepeats minimum number of repeats.
	 * @param maxRepeats maximum number of repeats, or {@code null} if unlimited.
	 * @param esi the EventSetInterface that should occur.
	 * @return {@code this}, to allow call chaining.
	 */
	public EventPattern append( EventSetInterface esi, int minRepeats, Integer maxRepeats ) {
		glob.add( new EventPatternPart( esi, minRepeats, maxRepeats) );
		return this;
	}
	
	/**
	 * Clears the pattern.
	 */
	public void clear() {
		glob.clear();
	}
	
	public List<EventPatternPart> getPatternParts() {
		return glob;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("EventPattern [");
		for ( EventPatternPart gpp : glob ) {
			sb.append(gpp.getEventSet().toString());
			if ( gpp.getMaxRepeats() == null ) {
				 if ( gpp.getMinRepeats()==0 ) {
					 sb.append("*");
				 } else {
					 sb.append("{" + gpp.getMinRepeats() + ",*}");
				 }
			} else {
				if ( gpp.getMinRepeats() != 1 || gpp.getMaxRepeats() != 1 ) {
					sb.append("{" + gpp.getMinRepeats() + ","+gpp.getMaxRepeats()+"}");
				}
			}
			sb.append(" ");
		}
		sb.append("]");
		return sb.toString();
	
	}
	
}
