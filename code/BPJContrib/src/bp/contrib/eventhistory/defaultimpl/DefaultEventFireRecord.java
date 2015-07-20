package bp.contrib.eventhistory.defaultimpl;

import java.io.Serializable;

import bp.contrib.eventhistory.EventOccurrence;
import bp.eventSets.RequestableInterface;

/**
 * Default implementation of the {@link EventOccurrence} interface.
 * 
 * @author michaelbar-sinai
 */
public class DefaultEventFireRecord implements EventOccurrence, Serializable {
	
	private final long time;
	private final RequestableInterface event;
	
	public DefaultEventFireRecord(long time, RequestableInterface event) {
		super();
		this.time = time;
		this.event = event;
	}

	@Override
	public long getTime() {
		return time;
	}

	@Override
	public RequestableInterface getEvent() {
		return event;
	}

	@Override
	public String toString() {
		return "DefaultEventFireRecord [time:" + getTime()
				+ ", event:" + getEvent() + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((event == null) ? 0 : event.hashCode());
		result = prime * result + (int) (time ^ (time >>> 32));
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
		DefaultEventFireRecord other = (DefaultEventFireRecord) obj;
		if (event == null) {
			if (other.event != null)
				return false;
		} else if (!event.equals(other.event))
			return false;
		if (time != other.time)
			return false;
		return true;
	}
	
}
