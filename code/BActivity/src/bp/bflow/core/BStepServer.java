package bp.bflow.core;

import bp.Event;
import bp.bflow.core.events.BStepEvent;
import bp.bflow.core.events.BStepSuccessEvent;
import bp.eventSets.EventSetInterface;
import bp.interop.BPJWorker;

/**
 * Base class for step servers. BStep servers wait for start event of their step, execute the step in a background
 * thread (<em>not</em> in BPj) and then report the completion via {@link BStepSuccessEvent}, back in BPj. Then they
 * run again (unless {@link #isRunAgain()} is overridden).
 * <br />
 * BStepServers terminate when the public static event {@link #TERMINATE_SERVERS} is fired.
 * <br />
 * Client code should normally only implement {@link #serveStep(Event)}.
 * 
 * @param <T> the type of the result
 * @author michaelbar-sinai
 */
public abstract class BStepServer<T> extends TriggeredBFlow {
	
	public static final Event TERMINATE_SERVERS = new Event("BStepServer#TERMINATE_SERVERS");
	
	private BStep step;
	private BStep currentStep;
	
	public BStepServer(BStep step) {
		super();
		this.step = step;
	}

	@Override
	public EventSetInterface getTerminationEventSet() {
		return TERMINATE_SERVERS;
	}

	@Override
	public EventSetInterface getTriggerEventSet() {
		return step.getStartEvent();
	}

	@Override
	public void sequenceTriggered(final Event evt) throws InterruptedException {
		if ( evt instanceof BStepEvent ) {
			currentStep = ((BStepEvent)evt).getBStep();
			
		} else {
			System.out.println("[BStepServer] " + this.toString() + " got triggered for none bstep event " + evt);
		}
		BPJWorker<T> joe = new BPJWorker<T>( getBProgram() ) {

			@Override
			public T doInBackground() throws Exception {
				return serveStep( evt );
			}

			@Override
			public void done(T res) throws InterruptedException {
				bpExec( broadcast(getCurrentStep().getSuccessEvent(res)) );
			}

			@Override
			public void failed(Throwable t) throws InterruptedException {
				t.printStackTrace();
				bpExec(broadcast( getCurrentStep().getFailureEvent(t)) );
			}
		};
		
		joe.execute();
	}

	@Override
	protected boolean isRunAgain() {
		return true;
	}
	
	/**
	 * Override this method to actually serve the step. This is done OUTSIDE of a BProgram.
	 * @param evt The event that triggered the step.
	 * @throws Exception when something goes wrong.
	 */
	protected abstract T serveStep( Event evt ) throws Exception;
	

	public BStep getStep() {
		return step;
	}

	public void setStep(BStep step) {
		this.step = step;
	}
	
	public BStep getCurrentStep() {
		return currentStep;
	}
	
}
