package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsValidName extends Query {

	private String pattern = "[a-zA-Z]+[a-zA-Z0-9_]"; 
	private PatternString query = new PatternString(pattern);

	public static IsValidName isValidName() {
		return new IsValidName();
	}
	
	@Override
	public IsValidName process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = query.satisfied(localItem);
		return this;
	}

	public String toString() {
		return "[String must be a valid name]";
	}

}
