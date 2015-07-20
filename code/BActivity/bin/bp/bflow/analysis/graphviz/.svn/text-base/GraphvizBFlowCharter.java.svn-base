package bp.bflow.analysis.graphviz;

import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import bp.bflow.analysis.utils.matchmaker.HandlerOf;
import bp.bflow.analysis.utils.matchmaker.MatchMaker;
import bp.bflow.core.BFlowBatch;
import bp.bflow.core.BFlowUnit;
import bp.bflow.core.BStep;
import bp.bflow.core.dslhelpers.BFlowSpawner;
import bp.bflow.core.dslhelpers.Broadcaster;
import bp.bflow.core.dslhelpers.CondBFlow;
import bp.bflow.core.dslhelpers.EventWaiter;
import bp.bflow.core.dslhelpers.ParallelBFlow;
import bp.bflow.core.dslhelpers.SequentialBFlow;
import bp.bflow.core.dslhelpers.TryExecBFlow;

/**
 * Takes a BFlow unit, makes a graphviz output.
 * 
 * @author michaelbar-sinai
 */
public class GraphvizBFlowCharter {
	
	private PrintStream out;
	
	MatchMaker<BFlowUnit, BFlowUnitPrinter<BFlowUnit>> matchMaker = 
								new MatchMaker<BFlowUnit, BFlowUnitPrinter<BFlowUnit>>();
	
	
	
	public GraphvizBFlowCharter(PrintStream out) {
		this.out = out;
		matchMaker.registerHandler(BFlowUnit.class, new DefaultBFlowUnitPrinter() );
		matchMaker.registerHandler(BStep.class, new BStepPrinter());
		matchMaker.registerHandler(BFlowBatch.class, new BFlowBatchPrinter());
		matchMaker.registerHandler(BFlowSpawner.class, new BFlowSpawnerPrinter());
		matchMaker.registerHandler(Broadcaster.class, new BroadcasterPrinter());
		matchMaker.registerHandler(CondBFlow.class, new CondBFlowPrinter());
		matchMaker.registerHandler(EventWaiter.class, new EventWaiterPrinter());
		matchMaker.registerHandler(ParallelBFlow.class, new ParallelBFlowPrinter());
		matchMaker.registerHandler(SequentialBFlow.class, new SequentialBFlowPrinter());
		matchMaker.registerHandler(TryExecBFlow.class, new TryExecBFlowPrinter());
	}
	
	public void emitHeader() {
		out.println("digraph G { ");
	}
	
	public void emitFooter() {
		out.println("}");
	}
	
	public List<String> chart( BFlowUnit unit ) {
		return matchMaker.match(unit).handle(unit, out, this);
	}
	
	public PrintStream getOut() {
		return out;
	}

	public void setOut(PrintStream out) {
		this.out = out;
	}
	
}

// TODO use this
class PlotResult {
	Set<String> startNodes;
	Set<String> endNodes;
	Set<String> allNodes; 
}

abstract class BFlowUnitPrinter<E extends BFlowUnit> implements HandlerOf<E>  {
	public List<String> handle( E unit, PrintStream out, GraphvizBFlowCharter charter  ) {
		out.println( clean(unit) + " [shape=parallelogram]" );
		return Arrays.asList(clean(unit));
	}
	
	/**
	 * Cleans a string to be a graphviz identifier.
	 * @param in the string to clean
	 * @return gv identifier based on in
	 */
	protected String clean( String in ) {
		in = in.replaceAll("\"", "\\\"" );
		return "\""+in+"\"";
	}
	
	protected String clean( BFlowUnit u ) {
		return clean( u.toString() );
	}
}
class DefaultBFlowUnitPrinter extends BFlowUnitPrinter<BFlowUnit>{}

class BStepPrinter extends BFlowUnitPrinter<BStep> {
	@Override
	public List<String> handle( BStep unit, PrintStream out, GraphvizBFlowCharter charter ) {
		out.println( clean(unit) + "[shape=egg, color=black]" );
		return Arrays.asList(clean(unit));
	}
}

class BFlowBatchPrinter extends BFlowUnitPrinter<BFlowBatch> {
	@Override
	public List<String> handle( BFlowBatch unit, PrintStream out, GraphvizBFlowCharter charter ) {
		out.println( clean(unit) + "[shape=box3d]" );
		return Arrays.asList(clean(unit));
	}
}


class BroadcasterPrinter extends BFlowUnitPrinter<Broadcaster> {
	@Override
	public List<String> handle( Broadcaster unit, PrintStream out, GraphvizBFlowCharter charter ) {
		out.println( clean(unit) + "[shape=rect]" );
		out.println( clean(unit.getBroadcastee().toString()) + "[shape=tripleoctagon color=green]" );
		out.println( clean(unit) + "->" + clean(unit.getBroadcastee().toString()) + "[style=dotted, color=green]" );
		return Arrays.asList(clean(unit));
	}
}

class EventWaiterPrinter extends BFlowUnitPrinter<EventWaiter> {
	@Override
	public List<String> handle( EventWaiter unit, PrintStream out, GraphvizBFlowCharter charter ) {
		out.println( clean(unit) + "[shape=invtriangle]" );
		out.println( clean(unit+"source") + "[shape=none]" );
		out.println( clean(unit+"source") + "->" + clean(unit) + "[style=dotted, color=red]" );
		return Arrays.asList(clean(unit));
	}
}


class BFlowSpawnerPrinter extends BFlowUnitPrinter<BFlowSpawner> {
	@Override
	public List<String> handle( BFlowSpawner unit, PrintStream out, GraphvizBFlowCharter charter ) {
		out.println( clean(unit) + "[shape=rect]" );
		for ( BFlowUnit subUnit : unit.getUnits() ) {
			out.println( clean(subUnit) );
			out.println( clean(unit) + "->" + clean(subUnit) + "[style=dashed]" );
			charter.chart(subUnit);
		}
		return Arrays.asList(clean(unit));
	}
}

class SequentialBFlowPrinter extends BFlowUnitPrinter<SequentialBFlow> {
	@Override
	public List<String> handle( SequentialBFlow unit, PrintStream out, GraphvizBFlowCharter charter ) {
		String startNode = clean(unit+"_S");
		String endNode = clean(unit+"_E");
		out.println( startNode + " [shape=trapezium, color=blue]" );
		out.println( endNode +   " [shape=invtrapezium, color=blue]" );
		String lastNode = startNode;
		for ( BFlowUnit subUnit : unit.getUnits() ) {
			List<String> subUnitNodes = charter.chart(subUnit); 
			
			out.println( lastNode + "->" + subUnitNodes.get(0) );
			lastNode = subUnitNodes.get(subUnitNodes.size()-1);
			
		}
		out.println( lastNode + "->" + endNode );
		return Arrays.asList(startNode, endNode);
	}
}

class ParallelBFlowPrinter extends BFlowUnitPrinter<ParallelBFlow> {
	@Override
	public List<String> handle( ParallelBFlow unit, PrintStream out, GraphvizBFlowCharter charter ) {
		String startNode = clean(unit+"_S");
		String endNode =  clean(unit+"_E");
		out.println( startNode + "[shape=parallelogram, color=blue]" );
		out.println( endNode + "[shape=parallelogram, color=blue]" );
		
		for ( BFlowUnit subUnit : unit.getSubs() ) {
			List<String> res = charter.chart(subUnit); 
			out.println( startNode + "->" + res.get(0) );
			out.println( res.get(res.size()-1) + "->" + endNode );
		}			
		
		return Arrays.asList(startNode, endNode);
	}
}

class TryExecBFlowPrinter extends BFlowUnitPrinter<TryExecBFlow> {
	@Override
	public List<String> handle( TryExecBFlow unit, PrintStream out, GraphvizBFlowCharter charter ) {
		// FIXEME needs to be in a subgraph, exceptions from its edge.
		String startNode = clean(unit) + "_S";
		String endNode = clean(unit) + "_E";
		
		out.println( startNode + " [shape=octagon]" );
		List<String> res = charter.chart(unit.getUnitToTry());
		out.println( startNode + "->" + res.get(0) );
		out.println( res.get(res.size()-1) + "->" + endNode );
		
		for ( TryExecBFlow.EventHandler handler : unit.getHandlers() ) {
			res = charter.chart(handler.getHandler());
			out.println( startNode + "->" + res.get(0) + " [style=dotted, color=orange]");
		}
		return Arrays.asList(startNode, endNode);
	}
}

class CondBFlowPrinter extends BFlowUnitPrinter<CondBFlow> {
	@Override
	public List<String> handle( CondBFlow unit, PrintStream out, GraphvizBFlowCharter charter ) {
		String startNode = clean(unit +"_S");
		String endNode = clean(unit +"_E");
		out.println( startNode + "[shape=diamond ]" );
		out.println( endNode + "[shape=diamond]" );
		
		List<String> res;
		for ( Map.Entry<Object, BFlowUnit> option : unit.getOptions().entrySet() ) {
			res = charter.chart( option.getValue() );
			
			out.println( startNode + "->" + res.get(0) 
						+ "[label=" + clean(option.getKey().toString()) + "]" );
			out.println( res.get( res.size()-1 ) + "->" + endNode );
			
		}
		if ( unit.getElseOption() != null ) {
			res = charter.chart(unit.getElseOption());
			
			out.println( startNode + "->" + res.get(0)
					+ "[label=\"else\"]" );
			out.println( res.get( res.size()-1 ) + "->" + endNode );
		}
		return Arrays.asList( startNode, endNode );
	}
}