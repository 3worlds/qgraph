package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class StartsWith extends Query {
	
	private String str;
	
	public StartsWith(String str) {
		this.str = str;
	}

	public static Query startsWith(String str) {
		return new StartsWith(str);
	}

	public String toString() {
		return "[String must start with '" + str + "']";
	}
	
	@Override
	public StartsWith process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = localItem.startsWith(str);
		return this;
	}
	
}
