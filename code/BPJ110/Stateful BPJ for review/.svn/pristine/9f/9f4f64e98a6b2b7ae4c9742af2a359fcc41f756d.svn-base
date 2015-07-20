package bp;

//import gov.nasa.jpf.jvm.Verify;

import static bp.eventSets.EventSetConstants.none;

import java.io.Serializable;

import bp.eventSets.EventSetInterface;
import bp.eventSets.RequestableInterface;
import bp.exceptions.BPJInterruptingEventException;
import bp.exceptions.BPJRequestableSetException;
import bp.exceptions.BPJUnregisteredBThreadException;

/**
 * A base class for behavior thread
 */
public abstract class BThread implements Serializable {
	transient Double priority;

	/* transient */private String name = this.getClass().getSimpleName();

	transient protected BProgram bp = null;
	transient private boolean monitorOnly = false;

	/**
	 * Temporary storage for bpSync parameters
	 */
	public transient RequestableInterface requestedEvents;
	public transient EventSetInterface watchedEvents;
	public transient EventSetInterface blockedEvents;

	/**
	 * The set of events that will interrupt this scenario.
	 */
	transient protected EventSetInterface interruptingEvents = none;

	/**
	 * The thread that executes this scenario
	 */
	transient Thread thread;

	public BThread() {
		requestedEvents = none;
		watchedEvents = none;
		blockedEvents = none;
	}

	public BThread(String name) {
		this();
		this.setName(name);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName()
	{
		return name;
	}

	/**
	 * The function that implements the BThread. Subclasses of BThread should
	 * override this method.
	 * 
	 * @throws BPJRequestableSetException
	 */

	public abstract void runBThread() throws InterruptedException, BPJRequestableSetException;

	/**
	 * @see java.lang.Thread#start()
	 */

	public void startBThread() {
		if (!getThread().isAlive())
			getThread().start();
	}

	public boolean isRequested(Event event) {
		return (requestedEvents.contains(event));
	}

	public String toString() {
		return name;
	}

	public BProgram getBProgram() {
		return bp;
	}

	public void setBProgram(BProgram bp) {
		this.bp = bp;
	}

	public void bWait() throws BPJInterruptingEventException {
		try {
			wait();
		} catch (Exception e) {
			throw new BPJInterruptingEventException();

		}
	}

	public void setMonitorOnly(boolean flag) {

		if (bp == null)
			throw new BPJUnregisteredBThreadException();
		synchronized (bp.getAllBThreads()) {
			monitorOnly = flag;
		}

	}

	public boolean getMonitorOnly() {
		return monitorOnly;
	}

	public void setThread(Thread thread) {
		this.thread = thread;
	}

	public Thread getThread() {
		return thread;
	}

	// The code below makes sure that we get the same hashCode and equals for
	// copies that come from serialization and then deserialization of the same
	// object.

	static int numerator = 0;
	int hash = numerator++;

	@Override
	public int hashCode() {
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BThread other = (BThread) obj;
		if (hash != other.hash)
			return false;
		return true;
	}

}
