package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class FloatString extends Query {

	public static FloatString isFloat() {
		return new FloatString();
	}
	
	@SuppressWarnings("unused")
	@Override
	public FloatString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Float typedItem = Float.valueOf(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Float]";
	}

}
