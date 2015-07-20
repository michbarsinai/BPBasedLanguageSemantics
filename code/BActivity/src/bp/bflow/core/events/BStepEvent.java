package bp.bflow.core.events;

import bp.Event;
import bp.bflow.core.BFlowUnit;
import bp.bflow.core.BStep;
import bp.eventSets.EventSetInterface;

/**
 * Base class for events that have to do with {@code BStep}s. A {@code BStepEvent}
 * event {@link #contains(Object)} other BStep event if they both refer to the same step.
 * Subclasses will probably want to refine this method (e.g. to check that the other event
 * is a start event).
 * <br />
 * In order for event A to contain event B, A's parent should be the same as B's parent,
 * or {@code null}. In effect, null parents server as wildcards.
 * @author michaelbar-sinai
 */
public class BStepEvent extends Event {
	
	public static final EventSetInterface ALL_BSTEP_EVENTS = new EventSetInterface(){

		@Override
		public boolean contains(Object o) {
			return (o instanceof BStepEvent);
		}};
	
	/** The step this event belongs to. */
	private BStep step;
	protected Object value;
	
	protected BStepEvent( BStep aStep ) {
		this( aStep, null );
	}
	
	protected BStepEvent( BStep aStep, Object aValue ) {
		super();
		step = aStep;
		value = aValue;
	}
	
	@Override
	public boolean contains(Object o) {
		if ( o instanceof BStepEvent ) {
			BFlowUnit otherStep = ((BStepEvent)o).getBStep();
			Object otherStepId = otherStep.getId();
			Object myStepId = getBStep().getId();
			
			boolean sameStepId = (myStepId!=null) ? myStepId.equals(otherStepId) : (otherStepId==null);
			if ( sameStepId ) {
				if (step.getParent()==null) {
					return true; // null parents serve as a wild card.
				} else {
					return step.getParent().equals( otherStep.getParent() );
				}
								
			} else {
				return false;
			}
		} 
		
		return false;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((step == null) ? 0 : step.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		BStepEvent other = (BStepEvent) obj;
		if (step == null) {
			if (other.step != null)
				return false;
		} else if (!step.equals(other.step))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	public BStep getBStep() {
		return step;
	}

	@Override
	public String toString() {
		return "[" + getClass().getName() + " step:" + getBStep() + " value:" + getValue() +"]";
	}

	public Object getValue() {
		return value;
	}
	
	

}