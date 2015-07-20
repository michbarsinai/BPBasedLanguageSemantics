package bp.eventSets;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * A set containing all event instances from a class list.
 */

@SuppressWarnings("serial")
public class EventsOfClass extends HashSet<Class<?>> implements EventSetInterface {
    
    private Set<Class<?>> eventClasses;
	private String name;

	/**
	 * Constructor.
     * @param aName Name for the set. If {@code null}, an automatic name will be given.
     * @param clsList The classes whose events we want to include in the set.
	 */
	public EventsOfClass(String aName, Class<?>... clsList) {
		StringBuilder sb = new StringBuilder();
		
        eventClasses = new HashSet<>(Arrays.asList(clsList));
        if ( aName == null ) {
            Iterator<Class<?>> classes = eventClasses.iterator();
            sb.append( classes.next().getSimpleName() );
            while ( classes.hasNext() ) {
                sb.append("+").append(classes.next().getSimpleName());
            }
            name = sb.toString();
        }

	}

	public EventsOfClass(Class<?>... clsList) {
		this(null, clsList);
	}

	/**
	 * Contains
	 * 
	 * Overrides 'contains' inherited from ArrayList
	 */
    @Override
	public boolean contains(Object o) {
		for ( Class<?> cls : eventClasses ) {
			if (cls.isInstance(o) && testFields(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Tests whether the fields of the object match the required definition. By
	 * default, we don't pose any requirements for the fields (always return
	 * true).
	 * 
	 * @param o
	 *            Object to test (always of the expected class).
	 * @return true if the fields of the given object math the criterion
	 *         represented by this filter.
	 */
	public boolean testFields(Object o) {
		return true;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return name;
	}
}
