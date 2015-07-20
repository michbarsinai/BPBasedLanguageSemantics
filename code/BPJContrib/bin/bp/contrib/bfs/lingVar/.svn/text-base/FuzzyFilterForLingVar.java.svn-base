package bp.contrib.bfs.lingVar;

import bp.contrib.bfs.events.LingVarEvent;
import bp.eventSets.EventSetInterface;

/*
 * An event filter for linguistic variables. Filtering is by degree of membership.
 */

public class FuzzyFilterForLingVar implements EventSetInterface {

	public enum Direction {
		ABOVE, BELOW
	};

	// public static final int ABOVE = 0;
	// public static final int BELOW = 1;

	private Class<?> cls;
	private double threshold;
	private Direction direction;

	private static final Direction DEFAULT_DIRECTION = Direction.ABOVE;
	private static final double DEFAULT_THRESHOLD = 0.9;

	// Constructor (default)
	public FuzzyFilterForLingVar(Class<? extends LingVarEvent> cls) {
		super();

		this.cls = cls;
		this.threshold = DEFAULT_THRESHOLD;
		this.direction = DEFAULT_DIRECTION;
	}

	// Constructor with threshold only
	public FuzzyFilterForLingVar(Class<? extends LingVarEvent> cls,
			double threshold) {
		super();

		assert (threshold > 0 && threshold < 1) : "threshold must be in [0,1]";
		this.cls = cls;
		this.threshold = threshold;
		this.direction = DEFAULT_DIRECTION;
	}

	// Constructor with cross direction and threshold
	public FuzzyFilterForLingVar(Class<? extends LingVarEvent> cls,
			double threshold, Direction crossDir) {
		super();

		assert (threshold > 0 && threshold < 1) : "threshold must be in [0,1]";
		this.cls = cls;
		this.threshold = threshold;
		this.direction = crossDir;
	}

	@Override
	public boolean contains(Object o) {
		return (cls.isInstance(o) && testFields(o));
	}

	public boolean testFields(Object o) {
		switch (direction) {

		// filter out events with membership higher or equal than threshold
		case BELOW:
			return (((LingVarEvent) o).getMembership() < threshold);

			// filter out events with membership lower than threshold
		default:
			return (((LingVarEvent) o).getMembership() >= threshold);
		}

	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
