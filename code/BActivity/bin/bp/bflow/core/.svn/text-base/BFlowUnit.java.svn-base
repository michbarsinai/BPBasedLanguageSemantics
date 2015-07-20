package bp.bflow.core;

/**
 * The base of the hierarchy for objects that compose {@link BFlowBatch}s. Implements the basic
 * functionality of having a parent (which must be at least a {@link BFlow}, but may as well
 * be a full {@link BFlowBatch}), and having an id. <br />
 * The id enables different {@link BThreads} to listen or block its start/end events
 * without the need to share the instance, as long as the id object is known in advance.<br />
 * For instance, a step server may know that it waits for {@link BStep} whose id would be the
 * string "PrintJob". When a {@code BFlowUnit} is created using the no-args constructor, it
 * creates its own ID, which means it is only identical to itself.  
 * 
 * 
 * @author michaelbar-sinai
 */
public abstract class BFlowUnit {

	/** The BFlowBatch this BStep is part of. */
	private BFlow parent;

	/** Identifies the unit.  */
	private Object id;
	
	public BFlowUnit() {
		this( new Object() );
	}
	
	public BFlowUnit( Object anId ) {
		this( anId, null );
	}
	
	public BFlowUnit( Object anId, BFlow aParent ) {
		id = anId;
		parent = aParent;
	}
	
	public BFlow getParent() {
		return parent;
	}

	public void setParent(BFlow parent) {
		this.parent = parent;
	}
	
	public abstract void acceptSequence( BFlow bast ) throws InterruptedException;

	public Object getId() {
		return id;
	}
	
}
