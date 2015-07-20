package bp.bflow.core.dslhelpers;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

import bp.bflow.core.BFlow;
import bp.bflow.core.BFlowUnit;

/**
 * Conditional BFlow. Has a {@link Callable}, which returns a result. based on which a sub BFlow is selected.
 * 
 * @author michaelbar-sinai
 */
public class CondBFlow extends BFlow {
	
	private Callable<?> valueCreator;
	private Map<Object, BFlowUnit> options = new HashMap<Object, BFlowUnit>();
	private BFlowUnit elseOption = null;
	
	@Override
	public void runBFlow() throws InterruptedException {
		try {
			Object value = valueCreator.call();
			
			if ( options.containsKey(value) ) {
				bpExec( options.get(value) );
			} else {
				if ( elseOption != null ) {
					bpExec( elseOption );
				}
			}
			
		} catch (Exception e) {
			getBProgram().bplog("Exception while evaluating condition: " + e.getMessage() );
			StringWriter sw = new StringWriter();
			PrintWriter ps = new PrintWriter( sw );
			e.printStackTrace( ps );
			getBProgram().bplog( sw.toString() );
			ps.close();
		}
	}

	public Callable<?> getValueCreator() {
		return valueCreator;
	}

	public void setValueCreator(Callable<?> valueCreator) {
		this.valueCreator = valueCreator;
	}

	public Map<Object, BFlowUnit> getOptions() {
		return options;
	}

	public void setOptions(Map<Object, BFlowUnit> options) {
		this.options = options;
	}

	public BFlowUnit getElseOption() {
		return elseOption;
	}

	public void setElseOption(BFlowUnit elseOption) {
		this.elseOption = elseOption;
	}
	
	public CondBFlow elseBpExec( BFlowUnit u ) {
		elseOption = u;
		return this;
	}
	
	public CondBFlowMissingBpExec eq( Object key ) {
		return new CondBFlowMissingBpExec( this, key);
	}
	
	public static class CondBFlowMissingEq {
		CondBFlow product;
		
		public CondBFlowMissingEq( Callable<?> valueCreator ) {
			product = new CondBFlow();
			product.valueCreator = valueCreator;
		}
		
		public CondBFlowMissingBpExec eq( Object key ) {
			return new CondBFlowMissingBpExec(product, key);
		}
	}
	
	public static class CondBFlowMissingBpExec {
		CondBFlow product;
		Object key;
		
		public CondBFlowMissingBpExec(CondBFlow product, Object key) {
			this.product = product;
			this.key = key;
		}

		public CondBFlow bpExec( BFlowUnit u ) {
			product.options.put(key, u);
			return product;
		}
	}
	
	@Override
	public String toString() {
		return "[Cond value:" + valueCreator + "]";
	}
}
