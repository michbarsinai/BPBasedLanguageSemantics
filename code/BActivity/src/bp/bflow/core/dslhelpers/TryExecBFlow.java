package bp.bflow.core.dslhelpers;

import java.util.LinkedList;
import java.util.List;

import bp.Event;
import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;
import bp.bflow.core.ExceptingEventFiredException;
import bp.eventSets.BooleanComposableEventSet;
import bp.eventSets.EventSetInterface;
import static bp.eventSets.BooleanComposableEventSet.*;

public class TryExecBFlow extends BFlow {
	
	public static class TryExecBFlowBuilder {
		TryExecBFlow product;
		
		public TryExecBFlowBuilder( BFlowUnit aUnitToTry ) {
			product = new TryExecBFlow();
			product.unitToTry = aUnitToTry;
		}
		
		public TryExecBFlowLackingBpExecBlock on( EventSetInterface esi ) {
			TryExecBFlowLackingBpExecBlock res = new TryExecBFlowLackingBpExecBlock();
			res.product = product;
			res.esi = esi;
			
			return res;
		}
		
	}
	
	public static class TryExecBFlowLackingBpExecBlock {
		TryExecBFlow product;
		EventSetInterface esi;
		
		public TryExecBFlow bpExec( BFlowUnit... handlerBody ) {
			product.addHandler(esi, new SequentialBFlow(handlerBody) );
			
			return product;
		}
	}
	
	public TryExecBFlowLackingBpExecBlock on( EventSetInterface esi ) {
		TryExecBFlowLackingBpExecBlock res = new TryExecBFlowLackingBpExecBlock();
		res.product = this;
		res.esi = esi;
		
		return res;
	}
	
	public static class EventHandler {
		EventSetInterface eventSet;
		BFlowUnit handler;
		
		public EventHandler(EventSetInterface eventSet, BFlowUnit handler) {
			this.eventSet = eventSet;
			this.handler = handler;
		}

		public EventSetInterface getEventSet() {
			return eventSet;
		}

		public BFlowUnit getHandler() {
			return handler;
		}
		
	}
	
	private BFlowUnit unitToTry;
	private List<EventHandler> handlers = new LinkedList<EventHandler>();
	
	@Override
	public void runBFlow() throws InterruptedException {

		EventSetInterface originalExceptingEvents = getExceptingEvents();
		
		// compose the new excepting event set
		
		BooleanComposableEventSet flowExceptingEvents = theEventSet( originalExceptingEvents );
		for ( EventHandler eh : handlers ) {
			flowExceptingEvents = flowExceptingEvents.or( eh.getEventSet() ); 
		}
		
		try { 
			
			setExceptingEvents( flowExceptingEvents );
			unitToTry.acceptSequence(this);
			setExceptingEvents( originalExceptingEvents );
			
		} catch ( ExceptingEventFiredException eefe ) {
			Event exceptingEvent = eefe.getFiredEvent();
			
			boolean eventConsumed = false;
			
			for ( EventHandler eh : handlers ) {
				if ( eh.getEventSet().contains(exceptingEvent) ) {
					setExceptingEvents( originalExceptingEvents );
					eh.getHandler().acceptSequence( this );
					eventConsumed = true;
				}
			}
			
			if ( ! eventConsumed ) {
				throw new ExceptingEventFiredException( eefe, exceptingEvent );
			}
		}
		
		
	}
	
	public void addHandler( EventSetInterface esi, BFlowUnit handlerBody ) {
		handlers.add( new EventHandler(esi, handlerBody) );
	}

	public BFlowUnit getUnitToTry() {
		return unitToTry;
	}

	public List<EventHandler> getHandlers() {
		return handlers;
	}
	
}
