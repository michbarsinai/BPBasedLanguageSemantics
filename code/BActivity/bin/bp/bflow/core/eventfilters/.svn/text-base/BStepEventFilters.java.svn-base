package bp.bflow.core.eventfilters;

import bp.bflow.core.BStep;
import bp.bflow.core.events.BStepEvent;
import bp.bflow.core.events.BStepFailureEvent;
import bp.bflow.core.events.BStepOverEvent;
import bp.bflow.core.events.BStepStartEvent;
import bp.eventSets.EventSetInterface;

/**
 * An aggregator / factory class for common BStepEvent filters. The methods
 * are static, to allow static import. 
 *
 * @author michaelbar-sinai
 */
public class BStepEventFilters {
	
	public static EventSetInterface ANY_BSTEP_EVENT = new EventSetInterface(){
		@Override
		public boolean contains(Object o) {
			return (o instanceof BStepEvent);
		}};
	
	public static EventSetInterface ANY_START = new EventSetInterface(){
		@Override
		public boolean contains(Object o) {
			return (o instanceof BStepStartEvent);
		}};
		
	public static EventSetInterface ANY_END = new EventSetInterface(){
		@Override
		public boolean contains(Object o) {
			return (o instanceof BStepOverEvent);
		}};
		
	public static EventSetInterface ANY_FAILURE = new EventSetInterface(){
		@Override
		public boolean contains(Object o) {
			return (o instanceof BStepFailureEvent);
		}};

	public static EventSetInterface anyBStepEventOf( final BStep b ) {
		return new EventSetInterface() {

			@Override
			public boolean contains(Object o) {
				if ( o instanceof BStepEvent ) {
					return ((BStepEvent)o).getBStep().getId().equals( b.getId() );
				}
				return false;
			}
			
		};
	}
}
