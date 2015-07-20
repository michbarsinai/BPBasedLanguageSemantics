package bp.contrib.bfs.environment;

import java.util.List;

import net.sourceforge.jFuzzyLogic.rule.Variable;

public interface EnvironmentInterface {

	/**
	 * @param variables
	 *            The list of variables to be initialized
	 */
	public void initialize(List<Variable> variables);

	/**
	 * @param outputVariables
	 *            The list of output variables with fresh values from the last
	 *            evaluation of the fuzzy rule block. These values are to be
	 *            copied into the environment.
	 */
	public void setOutputs(List<Variable> outputVariables);

	/**
	 * @param inputVariables
	 *            The list of input variables to write to.
	 * 
	 */
	public void getInputs(List<Variable> inputVariables);

}
