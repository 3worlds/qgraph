package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsIntegerRange extends Query {

	public IsIntegerRange() {
	}

	public static Query isIntegerRange() {
		return new IsIntegerRange();
	}

	public String toString() {
		return "[Must be a valid IntegerRange]";
	}

	@Override
	public IsIntegerRange process(Object item) {
		defaultProcess(item);
		if (item instanceof IntegerRange) {
			satisfied = true;
		} else
			satisfied = false;
		return this;
	}

}