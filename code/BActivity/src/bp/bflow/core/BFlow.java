package bp.bflow.core;

import static bp.eventSets.EventSetConstants.none;
import static bp.eventSets.BooleanComposableEventSet.*;

import java.util.Arrays;
import java.util.concurrent.Callable;

import bp.BProgram;
import bp.BThread;
import bp.Event;
import bp.bflow.core.dslhelpers.BFlowSpawner;
import bp.bflow.core.dslhelpers.Broadcaster;
import bp.bflow.core.dslhelpers.CondBFlow;
import bp.bflow.core.dslhelpers.EventWaiter;
import bp.bflow.core.dslhelpers.ParallelBFlow;
import bp.bflow.core.dslhelpers.SequentialBFlow;
import bp.bflow.core.dslhelpers.TryExecBFlow;
import bp.bflow.core.events.BStepSuccessEvent;
import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;

/**
 * A fragment of a bp-based activity. Provides all the DSL-like methods that allow for 
 * intuitive activity composition. Holds a reference to its parent (i.e. invoking {@code BFlow})
 * if any. This is used to allow {@link EventSetInterface}s to block its requested events, in case the system 
 * decides the top-level {@code BFlow} should be paused.<br />
 * A {@code BFlow} can split itself to sub-strands that can 
 * be executed by multiple {@link BThread}s.<br />
 * Each strand has a {@link #terminatedEvent}. This event must be blocked in each and every 
 * bSync call made by code directly in this strand (in particular, NOT by code in sub-strands spawned 
 * from this one). Normally this is managed by the sequence itself (in fact, normally there's no need to 
 * call bSync directly in sequences) but if the need does arise, make sure you block this event. It
 * can be obtained by {@link #getTerminatedEvent()}. 
 * 
 * @author michaelbar-sinai
 */
public abstract class BFlow extends BFlowUnit {
	
	
	/**
	 * The event our parent requested and we block as long as we run.
	 */
	private EventSetInterface terminatedEvent = none;
	
	
	/**
	 * If any of these events is fired, throw an Exception.
	 */
	private EventSetInterface exceptingEvents = none;
	
	/**
	 * The BProgram we run in.
	 */
	private BProgram bp;
	
	public BFlow( BProgram aBProgram ) {
		bp = aBProgram;
	}
	
	public BFlow() {}
	
	/**
	 * This method gets called by the sequence when it's time to do the actual job.
	 */
	public abstract void runBFlow() throws InterruptedException;
	
	/* ********************
	 * DSL methods
	 * ********************/
	/**
	 * @return creates an identity bstep.
	 * @see BStep
	 */
	public BStep bstep() {
		return new BStep( this );
	}
	
	/**
	 * Returns a BStep with the passed id.
	 * @param anId the id of the created BStep.
	 * @return a BStep.
	 */
	public BStep bstep( Object anId ) {
		return new BStep( this, anId );
	}
	
	public void bpExec( BFlowUnit... units ) throws InterruptedException {
		for ( BFlowUnit unit : units ) {
			unit.setParent( this );
			unit.acceptSequence( this );
		}
	}
	
	/**
	 * Runs the passed units in parallel threads (one for each unit). Does not wait
	 * for the execution to complete.
	 * @param units units to be run in parallel and not waited for completion.
	 * @throws InterruptedException 
	 * @see {@link #doInParallel(BFlowUnit...)}
	 */
	protected BFlow spawn( BFlowUnit... units ) {
		return spawn( Arrays.asList(units) );
	}
	
	/**
	 * @see #doSpawn(BFlowUnit...)
	 */
	protected BFlow spawn( Iterable<BFlowUnit> units ) {
		return new BFlowSpawner( getBProgram(), units );
	}
	
	/**
	 * Creates a new BACtivitySequence that, when run, executes all the 
	 * passed BActivityUnits in parallel.
	 * @param bactUnits
	 * @return A BACtivitySequence that executes the passed BActivityUnits in parallel.
	 */
	protected BFlow parallel( BFlowUnit... bactUnits ) {
		return new ParallelBFlow( Arrays.asList(bactUnits) );
	}
	
	/**
	 * @see #parallel(BFlowUnit...)
	 */
	protected BFlow parallel( Iterable<BFlowUnit> bactUnits ) {
		return new ParallelBFlow( bactUnits );
	}
	
	/**
	 * Creates a sequence that executes the passed {@link BActivityUnits}s one after the other.
	 * @param bactUnits the units to be run.
	 * @return A strand that will run the units.
	 */
	protected BFlow sequence( BFlowUnit... bactUnits ) {
		return sequence( Arrays.asList(bactUnits) );
	}
	
	/**
	 * @see #sequence(BFlowUnit...)
	 */
	protected BFlow sequence( Iterable<BFlowUnit> bactUnits ) {
		return new SequentialBFlow( bactUnits );
	}
	
	/**
	 * Broadcast an event. The returned BFlowUnit will wait until the event
	 * is indeed broadcasted. 
	 * @param evt the event to be broadcasted
	 * @return a bactivity unit that will broadcast the event, once run. 
	 */
	protected BFlow broadcast( RequestableInterface evt ) {
		return new Broadcaster( evt );
	}
	
	/**
	 * Perform the passed unit, but terminate if one of the excepting events
	 * is fired. The excepting events are specified later on using the returned
	 * builder.
	 * 
	 * @param someExceptingEvents Set of events that must make the unit execution stop
	 * @param units the units to be performed (in parallel)
	 * @return A builder that allows further specification of the excepting events.
	 *  
	 */
	protected TryExecBFlow.TryExecBFlowBuilder tryExec(BFlowUnit... units ) {
		return new TryExecBFlow.TryExecBFlowBuilder( new SequentialBFlow( units ) );
	}
	
	/**
	 * Pause the execution unit an event contained in {@code esi}
	 * is fired.
	 * 
	 * @param esi the event set for which we wait.
	 * @return a bflow unit that will wait for the event.
	 */
	protected EventWaiter waitFor( EventSetInterface esi ) {
		return new EventWaiter( esi );
	}
	
	/**
	 * Execute the passed condition, and select the next steps based on its result.
	 * @param condition the condition to execute.
	 * @return a dsl-style builder to further specify the run options.
	 */
	protected CondBFlow.CondBFlowMissingEq cond( Callable<?> condition ) {
		return new CondBFlow.CondBFlowMissingEq(condition);
	}
	
	/* ********
	 * Internal methods for running the DSL
	 * ********/
	
	/**
	 * Executes the passed {@link BStep} in the context of {@code this} BFlow.
	 * @param aStep the step to be executed.
	 */
	BStepCompletionRecord visitStep( BStep aStep ) throws InterruptedException {
		bp.bSync( aStep.getStartEvent(), none, getTerminatedEvent() );
		bp.bSync( none, 
				  theEventSet(aStep.getSuccessEvent()).or(getExceptingEvents()),
				  getTerminatedEvent() );
		
		Event last = bp.lastEvent;
		assertNoExceptingEvents(last);
		if ( last instanceof BStepSuccessEvent ) {
			return new BStepCompletionRecord( (BStepSuccessEvent)last );
		} else {
			// curious case, should not have gotten here,normally.
			return null;
		}
	}
	
	/**
	 * Executes the passed sequence in the context of {@code this} BFlow.
	 * @param sequence the sequence to be executed.
	 * @throws InterruptedException
	 */
	private void visitSequence( BFlow sequence ) throws InterruptedException {
		sequence.setBProgram(getBProgram());
		sequence.setTerminatedEvent(getTerminatedEvent());
		sequence.setExceptingEvents(getExceptingEvents());
		sequence.runBFlow();
	}
	
	/**
	 * Called by a sequence that wants to run {@code this} sequence. Part of the visitor pattern.
	 */
	@Override
	public void acceptSequence(BFlow bast)
			throws InterruptedException {
		bast.visitSequence( this );	
	}
	
	/* ******
	 * Utilities, Code-reuse methods, implementations, etc.
	 * ******/
	
	/**
	 * Requests the event, and waits for it to fire.
	 * @param anEvent the requested event
	 * @return the fired event
	 * @throws InterruptedException in case we were interrupted.
	 */
	protected Event requestAndWaitForEvent( RequestableInterface anEvent ) throws InterruptedException {
		bp.bSync( anEvent, getExceptingEvents(), getTerminatedEvent() );
		assertNoExceptingEvents( bp.lastEvent );
		return bp.lastEvent;
	}
	
	/**
	 * @throws ExceptingEventFiredException if the passed event is in {@link #exceptingEvents}
	 * @param anEvent
	 */
	protected void assertNoExceptingEvents( Event anEvent ) {
		if ( getExceptingEvents().contains(anEvent) ) {
			throw new ExceptingEventFiredException( anEvent );
		}
	}
	
	/* ********************
	 * Setters and Getters
	 * ********************/

	/**
	 * @return The event that must be blocked while this strands is alive.
	 */
	public EventSetInterface getTerminatedEvent() {
		return terminatedEvent;
	}

	public void setTerminatedEvent(EventSetInterface terminatedEvent) {
		this.terminatedEvent = (terminatedEvent!=null) ? terminatedEvent : none;
	}

	public EventSetInterface getExceptingEvents() {
		return exceptingEvents;
	}

	public void setExceptingEvents(EventSetInterface exceptionEvents) {
		this.exceptingEvents = (exceptionEvents!=null) ? exceptionEvents : none;
	}
	
	public BProgram getBProgram() {
		return bp;
	}
	
	protected void setBProgram( BProgram aBProgram ) {
		bp = aBProgram;
	}
	
}
