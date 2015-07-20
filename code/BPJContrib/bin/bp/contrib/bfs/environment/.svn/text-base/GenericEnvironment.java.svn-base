package bp.contrib.bfs.environment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import bp.contrib.bfs.NamingConventions;

import net.sourceforge.jFuzzyLogic.rule.Variable;

/**
 * This is an easy-to-use generic environment template that uses Java reflection
 * and can be extended to an application specific environment. The user only
 * needs to fill in the input/output variables and their update methods.
 * 
 */
public class GenericEnvironment implements EnvironmentInterface {

	/*
	 * physics constants
	 */
	@SuppressWarnings("unused")
	private static final double const1 = 3.14159;
	@SuppressWarnings("unused")
	private static final double const2 = 2.71828183;

	private static final double INITIAL_VALUE = 0.0;

	/*
	 * Variables
	 */

	/*
	 * input variables
	 */
	public double input1;
	public double input2;

	/*
	 * output variables
	 */
	public double output1;
	public double output2;

	/*
	 * Initialize linguistic variables of the BFS
	 */
	@Override
	public void initialize(List<Variable> variables) {
		for (Variable var : variables) {
			var.setValue(INITIAL_VALUE);
		}
	}

	/*
	 * Update output variables using Java reflection.
	 * 
	 * MUST define a local field for every OUTPUT linguistic variable defined in
	 * the .fcl file of corresponding BFS since defuzzified values of output
	 * variables are also kept by the environment.
	 */
	@Override
	public void setOutputs(List<Variable> outputVariables) {
		String varName;
		String varFieldName;

		for (Variable var : outputVariables) {

			varName = var.getName();
			varFieldName = NamingConventions.toFieldName(varName);

			try {
				Field fld = this.getClass().getField(varFieldName);
				fld.setDouble(this, var.getLatestDefuzzifiedValue());

			} catch (Exception e) {
				System.out.println("Cannot find field for varible " + varName
						+ ": physics engine should have field " + varFieldName);
			}
		}
	}

	/*
	 * update input variables using Java reflection.
	 * 
	 * MUST implement a set method for every INPUT variable in the .fcl file
	 * (see below).
	 */

	@Override
	public void getInputs(List<Variable> inputVariables) {
		String varName;

		for (Variable var : inputVariables) {
			varName = var.getName();

			Method updateMethod;
			String updateMethodName = NamingConventions
					.updateMethodName(varName);
			try {
				updateMethod = this.getClass().getMethod(updateMethodName,
						Variable.class);
				updateMethod.invoke(this, var);
			} catch (Exception e) {
				System.err.println("Error invoking update method for variable "
						+ varName + ": method name should be \""
						+ updateMethodName + "\".");
			}
		}
	}

	/*
	 * Update methods for input variables
	 */

	@SuppressWarnings("unused")
	private void updateInput1(Variable Input1Var) {
		input1 = Input1Var.getValue() + output1; // compute input1 from previous value
	}

	@SuppressWarnings("unused")
	private void updateInput2(Variable Input2Var) {
		input2 = Input2Var.getValue() + output2; // compute input2 from previous value
	}

	/*
	 * get current value of a variable using Java reflection
	 */
	public double getValue(String varName) {
		double displayValue;

		try {
			Field fld = this.getClass().getField(varName);
			displayValue = (Double) fld.get(this);
			return displayValue;
		} catch (Exception e) {
			System.err.println("Error getting value of variable " + varName
					+ ". Check fields of physics engine");
			return Double.NaN;
		}
	}

}