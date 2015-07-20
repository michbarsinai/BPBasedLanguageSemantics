package bp.bflow.core.events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bp.bflow.core.BStep;

/**
 * An event signaling that the work on its {@link BStep} is over (no one working on it now). 
 * This event does not suggest whether the work was successful or not, just that it's over.
 * 
 * @author michaelbar-sinai
 */
public class BStepOverEvent extends BStepEvent {
	
	private List<Object> warnings;
	
	protected BStepOverEvent(BStep aStep) {
		super(aStep);
	}
	
	protected BStepOverEvent(BStep aStep, Object aValue) {
		super(aStep, aValue);
	}
	
	public List<Object> getWarnings() {
		return (warnings!=null) ? warnings : Collections.emptyList();
	}
	
	/**
	 * Adds a warning to the list.
	 * @param aWarning the warning to add.
	 * @return {@code this} object, to allow method chaining.
	 */
	public BStepOverEvent addWarning( Object aWarning ) {
		if ( warnings == null ) {
			warnings = new ArrayList<Object>();
		}
		warnings.add( aWarning );
		
		return this;
	}

	@Override
	public boolean contains(Object o) {
		if ( o instanceof BStepOverEvent) {
			return super.contains(o);
		} else {
			return false;
		}
	}

	@Override
	public String toString() {
		return "[BStepOverEvent step:" + getBStep() + "]";
	}
	
	
}
