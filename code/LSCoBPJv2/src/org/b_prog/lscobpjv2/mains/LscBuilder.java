package org.b_prog.lscobpjv2.mains;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.b_prog.lscobpjv2.lsclang.syntax.LSC;
import org.b_prog.lscobpjv2.lsclang.syntax.Lifeline;
import org.b_prog.lscobpjv2.lsclang.syntax.Message;
import org.b_prog.lscobpjv2.lsclang.syntax.Sync;

/**
 * Utility class that builds an {@link LSC}. This is not essentially the DSL we will
 * be creating, just a utility class to make building charts for the test suite easier.
 * @author michael
 *
 */
public class LscBuilder {
	
	private final List<Lifeline> lifelines;
	private final Set<Lifeline> lifelineSet;
	private final LSC product = new LSC();
	
	public LscBuilder( Lifeline... someLifelines ) {
		lifelines = Arrays.asList(someLifelines);
		
		// assert that there are no duplicate lifelines
		lifelineSet = new HashSet<>();
		lifelineSet.addAll(lifelines);
		if ( lifelineSet.size() != lifelines.size() ) {
			throw new RuntimeException("Duplicate liflines passed to constructor");
		}
		
		for ( Lifeline l : lifelines ) {
			product.addLifeline(l);
		}
	}
	
	public LscBuilder name( String aName ) {
		product.setName(aName);
		return this;
	}
	
	public LscBuilder sync( Lifeline... someLifelines ) {
		StringBuilder sb = new StringBuilder();
		sb.append("SYNC <");
		for ( Lifeline l : someLifelines ) {
			sb.append( l.getId() ) .append(" ");
		}
		sb.setCharAt(sb.length()-1, '>');
		return sync( sb.toString(), someLifelines );
	}
	
	public LscBuilder sync( String syncTitle, Lifeline... someLifelines ) {
		Sync sync = new Sync(syncTitle);
		for ( Lifeline l : someLifelines ) {
			if ( ! lifelineSet.contains(l) ) {
				throw new RuntimeException("lifeline " + l + " not in chart!");
			}
			l.append(sync);
		}
		
		return this;
	}
	
	public interface MessagePassBuilderFrom {
		public MessagePassBuilderTo from( Lifeline l );
	}
	
	public interface MessagePassBuilderTo {
		public LscBuilder to( Lifeline l );
	}
	
	public MessagePassBuilderFrom send( final Message m ){
		return new MessagePassBuilderFrom(){

			@Override
			public MessagePassBuilderTo from(final Lifeline src) {
				src.appendSend(m);
				return new MessagePassBuilderTo(){

					@Override
					public LscBuilder to(Lifeline dst) {
						dst.appendReceive(m);
						return LscBuilder.this;
					}};
			}};
	}
	
	public LSC get() {
		return product;
	}
	
	public List<Lifeline> getLifelineOrder() {
		return lifelines;
	}
	
}
