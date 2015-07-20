package bp;

import static bp.eventSets.EventSetConstants.none;

import java.io.Serializable;

import bp.exceptions.BPJInterruptingEventException;
import bp.exceptions.BPJRequestableSetException;

/**
 * A thread that runs a scenario. The main reason for wrapping a BThread within
 * a separate thread is to allow pre and post processing.
 */
public class JavaThreadForBThread extends Thread implements Serializable {
	transient BThread bt;
	transient private Concurrency concurrency;

	/**
	 * Constructor.
	 * 
	 * 
	 */
	public JavaThreadForBThread(BThread sc) {
		super();
		this.bt = sc;
	}
	
	public JavaThreadForBThread(String threadName, BThread sc) {
		super(threadName);
		this.bt = sc;
	}

	/**
	 * @see java.lang.Thread#run()
	 */
	public void run() {
		try {
			concurrency = bt.getBProgram().concurrency;
			// Don't count this b-thread as running for concurrency purposes
			if (concurrency.control) {
				(bt.getBProgram()).debugPrint("Permits=" + concurrency.semaphore.availablePermits() + " " + bt + " Person1");
				concurrency.semaphore.acquire();
				(bt.getBProgram()).debugPrint("Acquired - starting  " + bt + "\n" + "Permits=" + concurrency.semaphore.availablePermits() + "  " + bt + " Person2");
			}

			// Run the code of the scenario
			try {
				bt.runBThread();
			} catch (BPJInterruptingEventException ex) {
			}

			// Clear interrupting set
			bt.interruptingEvents = none;

			// Process the next event (if needed) but don't wait for it
			bt.setThread(null);
			bt.getBProgram().bSync(none, none, none);

			// Don't count this b-thread as running for concurrency purposes
			if (concurrency.control) {
				(bt.getBProgram()).debugPrint("Permits=" + concurrency.semaphore.availablePermits() + " " + bt + " F1");
				concurrency.semaphore.release();
				(bt.getBProgram()).debugPrint("Released - finishing " + bt + "\n" + "Permits=" + concurrency.semaphore.availablePermits() + " " + bt + " F2");
			}

		} catch (BPJRequestableSetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

}
