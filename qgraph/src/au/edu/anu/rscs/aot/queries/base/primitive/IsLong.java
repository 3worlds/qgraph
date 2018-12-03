package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsLong extends Query {

	private long min;
	private long max;

	public IsLong(long min, long max) {
		this.min = min;
		this.max = max;
	}

	public static Query longInRange(long min, long max) {
		return new IsLong(min, max);
	}

	public static Query isLong() {
		return new IsLong(Long.MIN_VALUE, Long.MAX_VALUE);
	}

	public String toString() {
		return "[Must be Long in " + min + ".." + max + "]";
	}

	@Override
	public IsLong process(Object item) {
		defaultProcess(item);
		if (item instanceof Long) {
			Long localItem = (Long)item;
			satisfied = (localItem <= max && localItem >= min);
		} else
			satisfied = false;
		return this;
	}

}