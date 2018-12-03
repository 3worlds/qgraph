package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IntegerString extends Query {

	public static IntegerString isInteger() {
		return new IntegerString();
	}
	
	@SuppressWarnings("unused")
	@Override
	public IntegerString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Integer typedItem = Integer.valueOf(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be an Integer]";
	}

}
