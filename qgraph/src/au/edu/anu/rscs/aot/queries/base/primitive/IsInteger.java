package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsInteger extends Query {

	private int min;
	private int max;

	public IsInteger(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public static Query integerInRange(int min, int max) {
		return new IsInteger(min, max);
	}

	public static Query isDouble() {
		return new IsInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public String toString() {
		return "[Must be Integer in " + min + ".." + max + "]";
	}

	@Override
	public IsInteger process(Object item) {
		defaultProcess(item);
		if (item instanceof Integer) {
			Integer localItem = (Integer)item;
			satisfied = (localItem <= max && localItem >= min);
		} else
			satisfied = false;
		return this;
	}

}