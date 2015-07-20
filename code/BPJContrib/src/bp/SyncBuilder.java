package bp;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import bp.eventSets.EventSet;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableEventSet;
import bp.eventSets.RequestableInterface;
import static bp.eventSets.EventSetConstants.*;
/**
 * Wraps calls to {@link BProgram#bSync(bp.eventSets.RequestableInterface, bp.eventSets.EventSetInterface, bp.eventSets.EventSetInterface)}
 * in some utility methods, making heavy usage easier.
 * 
 * @author michaelbar-sinai
 */
public class SyncBuilder {
	
	private enum Mode {SUSTAIN, ONE_TIME, DEFAULT}
	
	private Map<Mode,Set<RequestableInterface>> requested = new EnumMap<SyncBuilder.Mode, Set<RequestableInterface>>(Mode.class); 
	private Map<Mode,Set<EventSetInterface>> watched = new EnumMap<SyncBuilder.Mode, Set<EventSetInterface>>(Mode.class);
	private Map<Mode,Set<EventSetInterface>> blocked = new EnumMap<SyncBuilder.Mode, Set<EventSetInterface>>(Mode.class);
	
	private BProgram bProgram;
	private Event lastEvent;

	public SyncBuilder(BProgram bProgram) {
		this.bProgram = bProgram;
		for ( Mode m : Mode.values() ) {
			requested.put(m, new HashSet<RequestableInterface>());
			watched.put(m, new HashSet<EventSetInterface>());
			blocked.put(m, new HashSet<EventSetInterface>());
		}
		setDefaultWatch( all );
		setDefaultBlock( none );
		setDefaultRequest( none );
	}
	
	/**
	 * Include {@code r} in the requested events for the next sync only.
	 * @param r the event to be requested
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainRequest(RequestableInterface)
	 */
	public SyncBuilder request( RequestableInterface r ) {
		requested.get(Mode.ONE_TIME).add(r);
		return this;
	}
	
	/**
	 * Include {@code r} in the requested events for the next syncs, until explicitly told to stop
	 * by calling {@link #releaseRequest(RequestableInterface)}.
	 * @param r the event to be requested
	 * @return {@code this}, to allow call chaining.
	 * @see #releaseRequest(RequestableInterface)
	 */
	public SyncBuilder sustainRequest( RequestableInterface r ) {
		requested.get(Mode.SUSTAIN).add(r);
		return this;
	}
	
	/**
	 * Stop including {@code r} in the requested events.
	 * 
	 * @param r the event to not be requested
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainRequest(RequestableInterface)
	 */
	public SyncBuilder releaseRequest( RequestableInterface r ) {
		requested.get(Mode.SUSTAIN).remove(r);
		return this;
	}
	
	/**
	 * Include {@code r} in the watched event sets for the next sync only.
	 * @param r the event set to be watched for
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainWatch(EventSetInterface)
	 */
	public SyncBuilder watch( EventSetInterface r ) {
		watched.get(Mode.ONE_TIME).add(r);
		return this;
	}
	
	/**
	 * Include {@code r} in the requested event sets for the next syncs, until explicitly told to stop
	 * by calling {@link #releaseWatch(EventSetInterface)}.
	 * @param r the event set to be watched for
	 * @return {@code this}, to allow call chaining.
	 * @see #releaseWatch(EventSetInterface)
	 */
	public SyncBuilder sustainWatch( EventSetInterface r ) {
		watched.get(Mode.SUSTAIN).add(r);
		return this;
	}
	
	/**
	 * Stop including {@code r} in the requested events.
	 * 
	 * @param r the event to not be watched for
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainRequest(EventSetInterface)
	 */
	public SyncBuilder releaseWatch( EventSetInterface r ) {
		watched.get(Mode.SUSTAIN).remove(r);
		return this;
	}
	
	/**
	 * Include {@code r} in the blocked event set for the next sync only.
	 * @param r the event set to be blocked
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainBlock(EventSetInterface)
	 */
	public SyncBuilder block( EventSetInterface r ) {
		blocked.get(Mode.ONE_TIME).add(r);
		return this;
	}
	
	/**
	 * Include {@code r} in the blocked event set for the next syncs, until explicitly told to stop
	 * by calling {@link #releaseBlock(EventSetInterface)}.
	 * @param r the event set to be blocked
	 * @return {@code this}, to allow call chaining.
	 * @see #releaseWatch(EventSetInterface)
	 */
	public SyncBuilder sustainBlock( EventSetInterface r ) {
		blocked.get(Mode.SUSTAIN).add(r);
		return this;
	}
	
	/**
	 * Stop including {@code r} in the blocked events.
	 * 
	 * @param r the event to not be blocked
	 * @return {@code this}, to allow call chaining.
	 * @see #sustainRequest(EventSetInterface)
	 */
	public SyncBuilder releaseBlock( EventSetInterface r ) {
		blocked.get(Mode.SUSTAIN).remove(r);
		return this;
	}
	
	public SyncBuilder setDefaultRequest( RequestableInterface r ) {
		requested.get( Mode.DEFAULT ).clear();
		requested.get( Mode.DEFAULT ).add( r );
		return this;
	}
	
	public SyncBuilder setDefaultWatch( EventSetInterface r ) {
		watched.get( Mode.DEFAULT ).clear();
		watched.get( Mode.DEFAULT ).add( r );
		return this;
	}
	
	public SyncBuilder setDefaultBlock( EventSetInterface r ) {
		blocked.get( Mode.DEFAULT ).clear();
		blocked.get( Mode.DEFAULT ).add( r );
		return this;
	}
	
	/**
	 * Calls the {@link BProgram}'s {@code bSync} method with all the event/sets.
	 * Then clears the one-time requests.
	 * @return The fired Event
	 * @throws InterruptedException if the thread was interrupted while blocked.
	 */
	public Event bSync() throws InterruptedException {
		RequestableEventSet effectiveRequested = new RequestableEventSet();
		EventSet effectiveWatched = new EventSet();
		EventSet effectiveBlocked = new EventSet();
		buildEventSets(effectiveRequested, effectiveWatched, effectiveBlocked);
		
		bProgram.bSync(effectiveRequested, effectiveWatched, effectiveBlocked);
		
		requested.get(Mode.ONE_TIME).clear();
		watched.get(Mode.ONE_TIME).clear();
		blocked.get(Mode.ONE_TIME).clear();
		lastEvent = bProgram.lastEvent;
		return lastEvent;
		
	}
	
	public void dump( String prefix ) {
		RequestableEventSet effectiveRequested = new RequestableEventSet();
		EventSet effectiveWatched = new EventSet();
		EventSet effectiveBlocked = new EventSet();
		buildEventSets(effectiveRequested, effectiveWatched, effectiveBlocked);
		
		StringBuilder sb = new StringBuilder();
		for ( RequestableInterface e : effectiveRequested ) {
			sb.append( e.toString() ).append(" ");
		}
		System.out.println( prefix + " Requested:\t" + sb.toString() );
		
		sb = new StringBuilder();
		for ( EventSetInterface e : effectiveWatched ) {
			sb.append( e.toString() ).append(" ");
		}
		System.out.println( prefix + " Watched:\t" + sb.toString() );
		sb = new StringBuilder();
		for ( EventSetInterface e : effectiveBlocked ) {
			sb.append( e.toString() ).append(" ");
		}
		System.out.println( prefix + " Blocked:\t" + sb.toString() );
	}

	private void buildEventSets(RequestableEventSet requestedSet,
			EventSet watchedSet, EventSet blockSet) {
		
		
		for ( Mode m : Arrays.asList( Mode.ONE_TIME, Mode.SUSTAIN ) ) {
			requestedSet.addAll( requested.get(m) );
			watchedSet.addAll( watched.get(m) );
			blockSet.addAll( blocked.get(m) );
		}
		// if we requested something, we watch it, unless we explicitly watch for something else.
		if ( watchedSet.isEmpty() ) {
			if ( requestedSet.isEmpty() ) {
				watchedSet.addAll( watched.get(Mode.DEFAULT) );
			} else {
				for ( RequestableInterface ri : requestedSet ) {
					watchedSet.addAll( ri.getEventList() );
				}
			}
		}
		
		if ( requestedSet.isEmpty() ) requestedSet.addAll( requested.get(Mode.DEFAULT) );
		
		if ( blockSet.isEmpty() ) blockSet.addAll( blocked.get(Mode.DEFAULT) );
	}
	
	
	
	public BProgram getBProgram() {
		return bProgram;
	}

	public void setBProgram(BProgram aBProgram) {
		this.bProgram = aBProgram;
	}
	
	public Event getLastEvent() {
		return lastEvent;
	}
	
}
