package bp.interop;

import bp.BProgram;
import bp.contrib.BThreadPriorityManager;

/**
 * Utility class that helps executing long-running tasks in the background 
 * in a normal java thread, and then telling the BProgram about the results.
 * @param <T> the type of the result.
 * @author michaelbar-sinai
 */
public abstract class BPJWorker<T> implements Runnable {
	
	public enum WorkerState {
		PENDING,
		STARTED,
		DONE;
	}
	
	private WorkerState state = WorkerState.PENDING;
	private BProgram bp;
	private String name = this.toString();
	
	public BPJWorker(BProgram bp) {
		this.bp = bp;
	}

	@Override
	public void run() {
		setState( WorkerState.STARTED );
		T res = null;
		Throwable err = null;
		try {
			res = doInBackground();
		} catch ( Exception e ) {
			err = e;
		}
		
		try {
		bp.add( name, BThreadPriorityManager.nextPriority() );
			if ( err != null ) {
				failed( err );
			} else {
				done( res );
			}
		
		} catch (InterruptedException e) {
			bp.bplog( "While informing the BProgram about the results of the background task:" );
			bp.bplog( e.getMessage() );
			
		} finally {
			bp.remove();
		}
		
		setState( WorkerState.DONE );
	}
	
	public void execute() {
		// LATER submit to an executor service?
		Thread t = new Thread( this );
		t.setName( "ExecutorThread for " + this.toString() );
		
		t.start();
	}
	
	/**
	 * Do the long-running work here
	 * @return the result of the long running work
	 * @throws Exception
	 */
	public abstract T doInBackground() throws Exception;
	
	/**
	 * Called after the thread joined the BProgram, and no exception was
	 * thrown from {@link #doInBackground()}.
	 * @param res the result from {@link #doInBackground()}
	 */
	public abstract void done(T res) throws InterruptedException;
	
	/**
	 * Called after the thread joined the BProgram, and {@link #doInBackground()}
	 * threw an exception.
	 * @param t the exception thrown from {@link #doInBackground()}.
	 * @throws InterruptedException 
	 */
	public abstract void failed(Throwable t) throws InterruptedException;

	/////////
	// Getters/Setters
	
	
	public WorkerState getState() {
		return state;
	}

	protected void setState(WorkerState state) {
		this.state = state;
	}

	public BProgram getBProgram() {
		return bp;
	}

	public void setBProgram(BProgram bp) {
		this.bp = bp;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
