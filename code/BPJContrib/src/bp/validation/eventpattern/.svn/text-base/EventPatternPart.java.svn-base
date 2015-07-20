package bp.validation.eventpattern;

import bp.Event;
import bp.eventSets.EventSetInterface;

public class EventPatternPart  {
	
	private EventSetInterface eventSet;
	private int minRepeats;
	private Integer maxRepeats;
	

	public EventPatternPart(EventSetInterface eventSet, int minRepeats,
			Integer maxRepeats) {
		super();
		this.eventSet = eventSet;
		this.minRepeats = minRepeats;
		this.maxRepeats = maxRepeats;
	}

	public boolean matches( Event e ) {
		return eventSet.contains(e);
	}
	
	
	public int getMinRepeats() {
		return minRepeats;
	}

	public void setMinRepeats(int minRepeats) {
		this.minRepeats = minRepeats;
	}

	public Integer getMaxRepeats() {
		return maxRepeats;
	}

	public void setMaxRepeats(Integer maxRepeats) {
		this.maxRepeats = maxRepeats;
	}

	public EventSetInterface getEventSet() {
		return eventSet;
	}
	
	public boolean isMultiChar() {
		return ( minRepeats!=1 || (maxRepeats!=null && maxRepeats!=1) );
	}

	@Override
	public String toString() {
		return "EventPatternPart [eventSet=" + eventSet + ", minRepeats="
				+ minRepeats + ", maxRepeats=" + maxRepeats + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((eventSet == null) ? 0 : eventSet.hashCode());
		result = prime * result
				+ ((maxRepeats == null) ? 0 : maxRepeats.hashCode());
		result = prime * result + minRepeats;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventPatternPart other = (EventPatternPart) obj;
		if (eventSet == null) {
			if (other.eventSet != null)
				return false;
		} else if (!eventSet.equals(other.eventSet))
			return false;
		if (maxRepeats == null) {
			if (other.maxRepeats != null)
				return false;
		} else if (!maxRepeats.equals(other.maxRepeats))
			return false;
		if (minRepeats != other.minRepeats)
			return false;
		return true;
	}
	
}
