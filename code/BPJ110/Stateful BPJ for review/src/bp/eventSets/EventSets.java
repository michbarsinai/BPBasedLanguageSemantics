package bp.eventSets;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import bp.Event;
import bp.exceptions.BPJRequestableSetException;

/**
 * Class holding utility event sets. Normally these are classes where there's no point
 * in having more than a single instance laying around.
 * 
 * @author michael
 *
 */
public abstract class EventSets {
	
public interface RequestableEventSet extends EventSetInterface, RequestableInterface {};
    
	private final static RequestableEventSet EMPTY_SET = new RequestableEventSet() {
        @Override
		public boolean contains(Object o) {
			return false;
		}

        @Override
		public String toString() {
			return this.getClass().getSimpleName();
		}

        @Override
        public Iterator<RequestableInterface> iterator() {
            return Collections.<RequestableInterface>emptySet().iterator();
        }

        @Override
        public RequestableInterface get(int index) {
            throw new UnsupportedOperationException("Empty set does not have events.");
        }

        @Override
        public boolean isEvent() {
            return false;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public Event getEvent() throws BPJRequestableSetException {
            throw new UnsupportedOperationException("Empty set does not have events.");
        }

        @Override
        public List<Event> getEventList() {
            return Collections.<Event>emptyList();
        }

        @Override
        public void addEventsToList(ArrayList<Event> list) {
            // nothing to add.
        }
	}; 
	
	/**
	 * @author Bertrand Russell
	 */
	private final static EventSetInterface ALL_EVENTS = new EventSetInterface() {
		@Override
		public boolean contains(Object o) {
			return (o instanceof EventSetInterface)
					|| ( o instanceof RequestableInterface );
		}
        @Override
		public String toString() {
			return this.getClass().getSimpleName();
		}
	};
	
	public static RequestableEventSet none() {
		return EMPTY_SET;
	}
	
	public static EventSetInterface all() {
		return ALL_EVENTS;
	}
	
	public static EventSetInterface ofClass( Class<?>... classes ) {
		return new EventsOfClass(classes);
	}

	public static EventSetInterface ofClass( String name, Class<?>... classes ) {
		return new EventsOfClass(name, classes);
	}
}
