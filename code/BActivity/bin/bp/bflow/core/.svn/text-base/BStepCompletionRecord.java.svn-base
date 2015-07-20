package bp.bflow.core;

import bp.bflow.core.events.BStepSuccessEvent;

/**
 * A record of a {@link BStep} completion. Holds everything there is to know about 
 * the completed BStep. 
 * 
 * @author michaelbar-sinai
 */
public class BStepCompletionRecord {
	
	private BStepSuccessEvent endEvent;
	private long completionTime = System.currentTimeMillis();
	
	public BStepCompletionRecord( BStepSuccessEvent see ) {
		endEvent = see;
	}
	
	public BStep getBStep() {
		return endEvent.getBStep();
	}
	
	public Object getValue() {
		return endEvent.getValue();
	}
	
	/**
	 * <em>Low-level call!</em> Allows client code to access the underlying event.
	 * @return the actual event that heralded the completion of the step.
	 */
	public BStepSuccessEvent getEndEvent() {
		return endEvent;
	}
	
	public long getTime() {
		return completionTime;
	}
	
	@Override
	public String toString() {
		return "BStepCompletionRecord [endEvent=" + endEvent
				+ ", completionTime=" + completionTime + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ (int) (completionTime ^ (completionTime >>> 32));
		result = prime * result
				+ ((endEvent == null) ? 0 : endEvent.hashCode());
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
		BStepCompletionRecord other = (BStepCompletionRecord) obj;
		if (completionTime != other.completionTime)
			return false;
		if (endEvent == null) {
			if (other.endEvent != null)
				return false;
		} else if (!endEvent.equals(other.endEvent))
			return false;
		return true;
	}
	
	
	
	
	
}
