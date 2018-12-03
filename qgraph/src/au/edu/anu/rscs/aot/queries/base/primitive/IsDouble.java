/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsDouble extends Query {

	private double min;
	private double max;

	public IsDouble(double min, double max) {
		this.min = min;
		this.max = max;
	}

	public static Query doubleInRange(double min, double max) {
		return new IsDouble(min, max);
	}

	public static Query isDouble() {
		return new IsDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
	}

	public String toString() {
		return "[Must be Double in " + min + ".." + max + "]";
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		if (item instanceof Double) {
			Double localItem = (Double)item;
			satisfied = (localItem <= max && localItem >= min);
		} else
			satisfied = false;
		return this;
	}

}
