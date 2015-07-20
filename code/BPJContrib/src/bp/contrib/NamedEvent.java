package bp.contrib;

import bp.Event;

/**
 * An event with a name. It is equal to other {@code NamedEvent}s if they have the same name.
 * 
 * @author michaelbar-sinai
 */
public class NamedEvent extends Event {
	
	public NamedEvent( String aName ) {
		super();
		setName( aName );
	}

	@Override
	public String toString() {
		return "[NamedEvent name:" + getName() + "]";
	}
	
	@Override 
	public boolean equals( Object other ) {
		if ( other == null ) return false;
		if ( other instanceof NamedEvent ) {
			NamedEvent otherNe = (NamedEvent)other;
			String myName = getName();
			return (myName != null) ? myName.equals(otherNe.getName())
									: (otherNe.getName()==null);
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		String myName = getName();
		return (myName!=null) ? myName.hashCode() : 0;
	}

}
