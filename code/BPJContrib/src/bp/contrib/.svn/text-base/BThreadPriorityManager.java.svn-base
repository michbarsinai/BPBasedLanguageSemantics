package bp.contrib;

import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;


/** 
 * Makes sure client code gets a unique priority every time (as long as everyone 
 * is using this class). Client code can specify which priority range it wants the
 * priority to be.
 * 
 * @author michaelbar-sinai
 */
public class BThreadPriorityManager {
	
	public enum Range { LOW, NORMAL, HIGH };
	
	private static final double DELTA = 0.0005;
	
	private static final Map<Range, PriorityCounter> lastPrios;
	
	static {
		lastPrios = new EnumMap<Range, PriorityCounter>(Range.class);
		lastPrios.put(Range.LOW, new PriorityCounter(-500, -DELTA) );
		lastPrios.put(Range.NORMAL, new PriorityCounter(-500, DELTA) );
		lastPrios.put(Range.HIGH, new PriorityCounter(10000, DELTA) );
	}
	
	/**
	 * Convenience call for {@code nextPriority(NORMAL)}.
	 * @return a new priority in the normal range.
	 */
	public static double nextPriority() {
		return nextPriority(Range.NORMAL);
	}
	
	public static double nextPriority( Range aRange ) {
		return lastPrios.get(aRange).next();
	}
	
	
	private static class PriorityCounter {
		private ReentrantLock lock = new ReentrantLock();
		private volatile double lastPriority;
		private double delta;
		
		PriorityCounter( double initialValue, double aDelta ) {
			lastPriority = initialValue;
			delta = aDelta;
		}
		
		public double next() {
			lock.lock();
			lastPriority += delta;
			lock.unlock();
			return lastPriority;
		}
	}
	
}
