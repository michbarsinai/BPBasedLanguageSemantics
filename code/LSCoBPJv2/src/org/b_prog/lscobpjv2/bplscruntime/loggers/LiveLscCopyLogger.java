package org.b_prog.lscobpjv2.bplscruntime.loggers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.b_prog.lscobpjv2.bplscruntime.LiveLscCopy;
import org.b_prog.lscobpjv2.events.LocationEntry;
import org.b_prog.lscobpjv2.events.LscOverBpjEvent;
import org.b_prog.lscobpjv2.events.lsc.HiddenLscEvent;
import org.b_prog.lscobpjv2.events.lsc.MessagePassedEvent;
import org.b_prog.lscobpjv2.events.lsc.VisibleLscEvent;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;

import bp.BThread;
import bp.Event;
import bp.eventSets.EventSetInterface;
import bp.exceptions.BPJRequestableSetException;

import static bp.eventSets.EventSets.*;
import static bp.eventSets.BooleanComposableEventSet.*;

/**
 * A logger thread logging the LSC-level events of a single live copy.
 * 
 * @author michael
 *
 */
public class LiveLscCopyLogger extends BThread {
	
	private final LiveLscCopy liveCopy;
	private final boolean logVisibleEvents;
	private final List<LscOverBpjEvent> eventLog = new LinkedList<>();

	/**  order in which lifelines are listed in the cut */
	private List<String> lifelineIds;
	
	public LiveLscCopyLogger( LiveLscCopy aLiveCopy, boolean logVisible ) {
		liveCopy = aLiveCopy;
		logVisibleEvents = logVisible;
	}
	
	@Override
	public void runBThread() throws InterruptedException,
			BPJRequestableSetException {
		
		final EventSetInterface eventFilter = new EventSetInterface() {
			
			@Override
			public boolean contains(Object o) {
				if ( ! (o instanceof LscOverBpjEvent) ) return false;
				if ( o instanceof LocationEntry ) return ((LocationEntry)o).getLiveLscCopy().equals(liveCopy);
				if ( o instanceof HiddenLscEvent ) return ((HiddenLscEvent)o).getLiveLscCopy().equals(liveCopy);
				if ( logVisibleEvents && (o instanceof VisibleLscEvent) ) return true;
				return false;
			}
		};
		
		
		boolean go = true;
		while ( go ) {
			getBProgram().bSync( none(), theEventSet(eventFilter).or(liveCopy.getExitEvents()), none() );
			Event lastEvent = getBProgram().lastEvent;
			eventLog.add( (LscOverBpjEvent) lastEvent );
			go = ! liveCopy.getExitEvents().contains(lastEvent);
		}
		
		System.out.println( String.format("LSC trace for %s", liveCopy.getLsc().getName()) );
		for ( String s : format(eventLog) ) {
			System.out.println(s);
		}
	}
	
	private List<String> format( List<LscOverBpjEvent> events ) {
		List<String> res = new ArrayList<>(events.size());
		
		Map<String, AtomicInteger> places = new HashMap<>(); 
		for ( String id : liveCopy.getLsc().lifelineIds() ) {
			places.put(id, new AtomicInteger());
		}
		
		if ( lifelineIds == null ) {
			lifelineIds = new ArrayList<>( liveCopy.getLsc().lifelineIds() );
			Collections.sort(lifelineIds);
		}
		
		for ( LscOverBpjEvent e : events ) {
			if ( e instanceof LocationEntry ) {
				places.get( ((LocationEntry)e).getLocation().getLifeline().getId() ).incrementAndGet();
			} else if ( e instanceof MessagePassedEvent ) {
				MessagePassedEvent mpe = (MessagePassedEvent) e;
				Message m = mpe.getMessage();
				res.add( String.format("%s | Message passed: %s -> %s: %s",
						 	formatCut(places), m.getSender().getId(), m.getReceiver().getId(), m.getMethodName()) );
			} else {
				res.add( String.format("%s | %s", formatCut(places), e) );
			}
		}
		return res;
	}
	
	private String formatCut( Map<String, AtomicInteger> cutMap ) {
		StringBuilder sb = new StringBuilder();
		
		for ( String id : lifelineIds ) {
			sb.append(id).append(":").append( cutMap.get(id).intValue() );
			sb.append(", ");
		}
		sb.setLength(sb.length()-2);
		
		return sb.toString();
	}
	
	public void setLifelineIdOrder( List<String> someLifelineIds ) {
		lifelineIds = someLifelineIds;
	}
}
