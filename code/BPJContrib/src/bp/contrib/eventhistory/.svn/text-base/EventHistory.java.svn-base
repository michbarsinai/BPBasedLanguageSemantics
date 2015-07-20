package bp.contrib.eventhistory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import static bp.eventSets.EventSetConstants.*;
import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.contrib.BThreadPriorityManager;
import bp.contrib.eventhistory.defaultimpl.DefaultEventHistoryWriterFactory;

/**
 * A "bootstrap" class to allow client code access to an {@link EventHistorian} in an implementation-
 * independent manner. <em>Client code must call {@link #start(BProgram))} to start recording
 * the events.</em> To stop the thread (and allow the JVM to terminate) the client code also
 * needs to call {@link #shutdown(BProgram))}<br />
 * To install your own {@link EventHistoryWriter}, either:
 * <ul>
 * 	<li>Call {@link #setEventHistoryWriterFactory(EventHistoryWriterFactory)},
 * 		before calling the {@code start()} method, or </li>
 *  <li>Invoke the JVM with the environment parameter {@code bp.contrib.eventhistory.EventHistoryWriterFactory}
 *  	set to your fully qualified event writer class name. When using this method, make sure
 *  	your implementation has a no-args constructor.</li>
 * </ul>  
 * 
 * @author michaelbar-sinai
 */
public class EventHistory {
	
	private static Logger LOGGER = Logger.getLogger( EventHistory.class.getName() );

	private static ConcurrentHashMap<BProgram, EventHistoryWriter> historyWriters = new ConcurrentHashMap<BProgram, EventHistoryWriter>();
	
	private static Event SHUTDOWN = new Event("History Writer Shutdown");
	
	private static EventHistoryWriterFactory writerFactory;
	
	/**
	 * Starts writing the history for the passed BProgram.
	 * @param bp the BProgram whose history interests us.
	 */
	public static synchronized void start( final BProgram bp ) {
		
		EventHistoryWriter wrt = historyWriters.get(bp);
		
		if ( wrt == null ) {
			wrt = setupHistoryWriter();
			historyWriters.put( bp, wrt );
		}
		
		final EventHistoryWriter finalWrt = wrt;
		BThread historyWriterBt = new BThread(){

			@Override
			public void runBThread() throws InterruptedException {
				finalWrt.setup();
				while ( true ) {
					bp.bSync(none, all, none);
					Event lastEvent = bp.lastEvent;
					if ( lastEvent==SHUTDOWN ) {
						break;
					} else {
						finalWrt.recordEvent( lastEvent );
					}
				}
				finalWrt.shutdown();
		}};
		
		historyWriterBt.setName( "HistoryWriterBthread" );
		
		bp.add(historyWriterBt, BThreadPriorityManager.nextPriority());	
		historyWriterBt.startBThread();
	}
	
	/**
	 * Shuts down the historian for a given {@code BProgram}. Another option is to
	 * request the shutdown event from that {@code BProgram}, as in:
	 * <pre><code>
	 * 	bp.bsync( EventHistory.getShutdownEvent(), none, none );
	 * </code></pre>
	 * @param bp the BProgram we want to stop the history recording in.
	 * @see #getShutdownEvent()
	 */
	public static void shutdown( final BProgram bp ) {
		// Create a new BThread that will request the SHUTDOWN event.
		BThread shutdowner = new BThread() {
			@Override
			public void runBThread() throws InterruptedException {
				bp.bSync(SHUTDOWN, none, none);
			}
		};
		bp.add(shutdowner, BThreadPriorityManager.nextPriority());
		shutdowner.startBThread();
	}
	
	public static EventHistorian historian( BProgram bp ) {
		EventHistoryWriter wrt = historyWriters.get(bp);
		return (wrt!=null) ? wrt.getHistorian() : null;
	}
	
	/**
	 * Sets the class used to create instances of history writers.
	 * @param historyClass The class of the history writers.
	 */
	public static void setEventHistoryWriterFactory( EventHistoryWriterFactory aFactory ) {
		LOGGER.info( "Using history writer factory " + aFactory.toString() );
		writerFactory = aFactory;
	}
	
	private static EventHistoryWriter setupHistoryWriter() {		
		if ( writerFactory == null ) {
			String historyWriterClassname = System.getenv( EventHistoryWriterFactory.class.getCanonicalName() );
			if ( historyWriterClassname != null ) {
				try {
					@SuppressWarnings("unchecked")
					Class<? extends EventHistoryWriterFactory> aClass = 
							(Class<? extends EventHistoryWriterFactory>) Class.forName(historyWriterClassname);
						setEventHistoryWriterFactory( aClass.newInstance() );
				} catch (InstantiationException e) {
					LOGGER.log(Level.SEVERE, "Can't instantiate the history writer factory: ", e );
				} catch (IllegalAccessException e) {
					LOGGER.log(Level.SEVERE, "Illegal access while instantiating the history writer factory: ", e );
				} catch (ClassNotFoundException e) {
					LOGGER.log(Level.SEVERE, 
							"Can't create EventHistoryWriter class '" + historyWriterClassname +"': " + e.getMessage(), e);
				}
			} else {
				setEventHistoryWriterFactory( new DefaultEventHistoryWriterFactory() );
			}
		}
		
		EventHistoryWriter writer = null;
		writer = writerFactory.buildWriter();
		
		return writer;
		
	}
	
	public static Event getShutdownEvent() {
		return SHUTDOWN;
	}
}
