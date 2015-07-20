package bp.contrib.bfs;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;
import bp.contrib.bfs.environment.EnvironmentInterface;
import bp.contrib.bfs.events.LingVarEvent;
import bp.contrib.bfs.lingVar.LingVarEventFilteringMethods;
import bp.eventSets.RequestableEventSet;

public class BFuzzySystem {

	/*
	 * Environment for this BFS
	 */
	private EnvironmentInterface env;

	/*
	 * jfuzzylogic system
	 */
	private FIS fis;
	private FunctionBlock functionBlock;
	private RuleBlock ruleBlock;

	/*
	 * Variables
	 */
	private List<Variable> variables = new ArrayList<Variable>();
	private List<Variable> inputVariables = new ArrayList<Variable>();
	private List<Variable> outputVariables = new ArrayList<Variable>();
	private List<Variable> monitoredVariables = new ArrayList<Variable>();

	/*
	 * Parameters
	 */
	public enum filteringMethod {
		POSITIVE, MAXIMAL
	}

	/*
	 * Loads a new .fcl file into this BFS
	 * 
	 * We currently don't support multiple function blocks; it is not really
	 * necessary when used together with BP since a new .fcl can be loaded to
	 * this BFS.
	 */
	public void load(String fclFile, EnvironmentInterface env) {

		this.env = env;

		this.fis = FIS.load(fclFile, true);
		assert fis != null : "Error loading file: '" + fclFile + "'";

		this.functionBlock = fis.getFunctionBlock(null);
		assert functionBlock != null : "Error loading default function block";

		this.ruleBlock = functionBlock.getFuzzyRuleBlock(null);
		assert ruleBlock != null : "Error loading default rule block";

		/*
		 * initialize variables
		 */

		variables.clear();
		inputVariables.clear();
		outputVariables.clear();
		monitoredVariables.clear();

		for (Variable var : functionBlock.getVariables().values()) {
			variables.add(var);

			if (var.isOutputVarable()) {
				outputVariables.add(var);
			} else {
				inputVariables.add(var);
			}
		}

		env.initialize(variables);
	}

	/*
	 * Perform a system step:
	 * 
	 * evaluate fuzzy rule block to compute outputs, defuzzify outputs and
	 * update inputs as specified by the environment.
	 */
	public void evaluate() {

		assert env != null : "Error evaluating system step: environment is null";
		assert ruleBlock != null : "Error evaluating system step: rule Block is null";

		/*
		 * update output variables based on the fuzzy rules
		 */

		ruleBlock.reset();

		ruleBlock.evaluate();

		for (Variable var : outputVariables) {
			var.defuzzify();
		}

		env.setOutputs(outputVariables);

		/*
		 * update input variables based on latest outputs
		 */
		env.getInputs(inputVariables);
	}

	/*
	 * Returns a requestable event set containing all linguistic events of
	 * currently monitored linguistic variables
	 */
	public RequestableEventSet getAllMonitoredEvents(filteringMethod filter) {
		RequestableEventSet monitoredEvents = new RequestableEventSet();

		if (monitoredVariables.isEmpty()) {
			return monitoredEvents;
		}

		Iterator<Variable> itr = monitoredVariables.iterator();
		while (itr.hasNext()) {
			monitoredEvents.addAll(getEventsForLingVar(itr.next(), filter));
		}
		return monitoredEvents;
	}

	/*
	 * Returns a requestable event set containing the current linguistic events
	 * for the given linguistic variable. Current events are determined by an
	 * event filtering method.
	 */

	private RequestableEventSet getEventsForLingVar(Variable var,
			filteringMethod filter) {

		RequestableEventSet varEvents = new RequestableEventSet();

		if (var == null) {
			System.err
					.println("Error generating variable events: variable is null");
			return varEvents;
		}
		String varName = var.getName();

		// get all linguistic terms of the variable from the fuzzy system
		Set<String> lingTerms = var.getLinguisticTerms().keySet();

		double currentValue = var.getValue();
		double mu = 0;

		for (String term : lingTerms) {

			mu = var.getMembership(term); // fuzzify 'term' at current value

			// Check filtering method
			switch (filter) {
			case MAXIMAL:
				if (!LingVarEventFilteringMethods.isMaximal(var, mu)) {
					continue; // skip term if filtering fails
				}
			case POSITIVE:
				if (!LingVarEventFilteringMethods.isPositive(mu)) {
					continue;
				}
			default:
				break;
			}

			/*
			 * filtering was successful, generate an event for this linguistic
			 * term using reflection and add it to the returned event set
			 */

			try {
				Class<?> cls = Class.forName(NamingConventions.pkgName(varName)
						+ NamingConventions.toClassName(varName) + term);
				Constructor<?> ctor = cls.getConstructor(new Class[] {
						Double.TYPE, Double.TYPE });
				varEvents
						.add((LingVarEvent) ctor.newInstance(currentValue, mu));
			} catch (Exception e) {
				System.err
						.println("Error generating an event for "
								+ varName
								+ term
								+ ". Make sure that the class name and the linguistic term are identical.");
			}

		}
		
		return varEvents;
	}

	/*
	 * change active rule block of this bfs
	 */
	public void setRuleBlock(String ruleBlockName) {
		ruleBlock = functionBlock.getFuzzyRuleBlock(ruleBlockName);

		if (ruleBlock == null) {
			System.err.println("Error setting rule block to " + ruleBlockName
					+ ". No such ruleblock");
			return;
		}

		ruleBlock.setName(ruleBlockName);

		System.out.println(ruleBlock);
	}

	/*
	 * API for monitored variables
	 */
	public void addMonitoredVar(String varName) {
		Variable var = functionBlock.getVariable(varName);
		if (var == null) {
			System.err.println("Error getting variable " + varName
					+ ": name should be EXACTLY as in .fcl file (check case)");
			return;
		}

		monitoredVariables.add(var);
	}

	public void removeMonitoredVar(String varName) {
		monitoredVariables.remove(varName);
	}

	public void setMonitoredVar(String varName) {
		monitoredVariables.clear();

		Variable var = functionBlock.getVariable(varName);
		if (var == null) {
			System.err.println("Error getting variable " + varName
					+ ": name should be EXACTLY as in .fcl file (check case)");
			return;
		}

		monitoredVariables.add(var);
	}

	public void clearMonitoredVars() {
		monitoredVariables.clear();
	}
}