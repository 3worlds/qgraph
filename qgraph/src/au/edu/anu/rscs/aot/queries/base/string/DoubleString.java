package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class DoubleString extends Query {

	public static DoubleString isDouble() {
		return new DoubleString();
	}

	@SuppressWarnings("unused")
	@Override
	public DoubleString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Double typedItem = Double.valueOf(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Double]";
	}

}
