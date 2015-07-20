package bp.bflow.core.events;

import bp.Event;

/**
 * An event that only contains itself.
 * 
 * @author michaelbar-sinai
 */
public class IdentityEvent extends Event {
	
	public IdentityEvent( String aName ) {
		setName(aName);
	}
	
	public IdentityEvent(){}
	
	@Override
	public boolean contains(Object o) {
		return ( o == this );
	}
	
}
