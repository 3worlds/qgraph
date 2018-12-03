package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class LongString extends Query {

	public static LongString isLong() {
		return new LongString();
	}
	
	@SuppressWarnings("unused")
	@Override
	public LongString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Long typedItem = Long.valueOf(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Long]";
	}

}
