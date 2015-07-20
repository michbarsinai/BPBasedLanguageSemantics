package bp.eventSets;

import java.util.Arrays;


/**
 * A wrapper class for {@link EventSetInterfaces} that allows natural style logical
 * compositions of event set interfaces. Unary operators are static methods to be statically 
 * imported, and binary operators are methods of the class. <br />
 * So, assuming that A,B,C and D are {@code EventSetInterface}s, one could write:
 * <pre><code>
 * 
 * import static bp.contrib.eventsetbooleanops.EventSetBooleanOp.*;
 * ...
 * is(A).and( not( is(B).or(C) ).xor( is(A).nand(c) ) ).contains( evt );
 * anyOf( A, B, is(C).and(not(d)) );
 * etc etc.	
 * 
 * </code></pre>
 * 
 * @author michaelbar-sinai
 */
public abstract class BooleanComposableEventSet implements EventSetInterface {
	
	public static BooleanComposableEventSet theEventSet( final EventSetInterface ifce ) {
		if ( ifce instanceof BooleanComposableEventSet ) {
			return (BooleanComposableEventSet) ifce;
		} else {
			return new BooleanComposableEventSet() {
				@Override
				public boolean contains(Object o) {
					return ifce.contains(o);
				}

				@Override
				public String toString() {
					return "theEventSet(" + ifce.toString() +")";
				}};
		}
	}
	
	public static BooleanComposableEventSet not( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return ! ifce.contains(o);
			}
			@Override
			public String toString() {
				return "not(" + ifce.toString() +")";
			}};
	}
	
	public static BooleanComposableEventSet anyOf( final EventSetInterface... ifces) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				for ( EventSetInterface ifce : ifces ) {
					if ( ifce.contains(o) ) return true;
				}
				return false;
			}
			@Override
			public String toString() {
				return "anyOf(" + Arrays.asList(ifces).toString() +")";
			}};
	}
	
	public static BooleanComposableEventSet allOf( final EventSetInterface... ifces) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				for ( EventSetInterface ifce : ifces ) {
					if ( ! ifce.contains(o) ) return false;
				}
				return true;
			}
			@Override
			public String toString() {
				return "allOf(" + Arrays.asList(ifces).toString() +")";
			}};
	}
	
	public BooleanComposableEventSet and( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return ifce.contains(o) && BooleanComposableEventSet.this.contains(o);
			}
			
			@Override
			public String toString() {
				return "(" + ifce.toString() +") and (" + BooleanComposableEventSet.this.toString() +")";
			}
		
		};
	}
	
	public BooleanComposableEventSet or( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return ifce.contains(o) || BooleanComposableEventSet.this.contains(o);
			}
			
			@Override
			public String toString() {
				return "(" + ifce.toString() +") or (" + BooleanComposableEventSet.this.toString() +")";
			}};
	}
	
	public BooleanComposableEventSet xor( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return ifce.contains(o) ^ BooleanComposableEventSet.this.contains(o);
			}
			
			@Override
			public String toString() {
				return "(" + ifce.toString() +") xor (" + BooleanComposableEventSet.this.toString() +")";
			}};
	}
	
	public BooleanComposableEventSet nor( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return !(ifce.contains(o) || BooleanComposableEventSet.this.contains(o));
			}
			
			@Override
			public String toString() {
				return "(" + ifce.toString() +") nor (" + BooleanComposableEventSet.this.toString() +")";
			}};
	}
	
	public BooleanComposableEventSet nand( final EventSetInterface ifce ) {
		return new BooleanComposableEventSet() {
			@Override
			public boolean contains(Object o) {
				return !(ifce.contains(o) && BooleanComposableEventSet.this.contains(o));
			}
			
			@Override
			public String toString() {
				return "(" + ifce.toString() +") nand (" + BooleanComposableEventSet.this.toString() +")";
			}};
	}

}
