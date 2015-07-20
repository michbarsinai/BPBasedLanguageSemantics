package bp.contrib.bfs.events;

import java.text.DecimalFormat;

import bp.Event;

public class LingVarEvent extends Event {

	double value; // crisp value
	double mu; // membership value

	public LingVarEvent(double value, double mu) {
		super();
		this.value = value;
		this.mu = mu;
	}

	@Override
	public String toString() {
		DecimalFormat df = new DecimalFormat("0.0#");
		return this.getClass().getSimpleName() + '(' + df.format(this.value)
				+ ")=" + df.format(this.mu);
	}

	public double getMembership() {
		return this.mu;
	}

	public double getValue() {
		return this.value;
	}
}
