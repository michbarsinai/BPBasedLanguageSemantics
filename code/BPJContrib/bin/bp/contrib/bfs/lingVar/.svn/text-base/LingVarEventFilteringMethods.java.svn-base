package bp.contrib.bfs.lingVar;

import java.util.Set;

import net.sourceforge.jFuzzyLogic.rule.Variable;

public class LingVarEventFilteringMethods {

	private final static double epsilon = 0.09;

	/*
	 * Filtering methods for linguistic events
	 */

	/**
	 * @param mu
	 *            the membership degree
	 * @return true if and only if the membership degree is non negligible
	 */
	public static boolean isPositive(double mu) {
		return (mu > epsilon);
	}

	/**
	 * @param var
	 *            A linguistic variable
	 * @param mu
	 *            The membership degree of a linguistic term of var
	 * @return true if var has no linguistic value with higher membership
	 */
	public static boolean isMaximal(Variable var, double mu) {

		double maxValue = 0;
		Set<String> lingTerms = var.getLinguisticTerms().keySet();

		for (String term : lingTerms) {
			maxValue = Math.max(var.getMembership(term), maxValue);
		}

		return (maxValue == mu);

	}
}
