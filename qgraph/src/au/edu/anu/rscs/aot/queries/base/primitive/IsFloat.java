package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsFloat extends Query {

	private float min;
	private float max;

	public IsFloat(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public static Query floatInRange(float min, float max) {
		return new IsFloat(min, max);
	}

	public static Query isDouble() {
		return new IsFloat(-Float.MAX_VALUE, Float.MAX_VALUE);
	}

	public String toString() {
		return "[Must be Float in " + min + ".." + max + "]";
	}

	@Override
	public IsFloat process(Object item) {
		defaultProcess(item);
		if (item instanceof Float) {
			Float localItem = (Float)item;
			satisfied = (localItem <= max && localItem >= min);
		} else
			satisfied = false;
		return this;
	}

}