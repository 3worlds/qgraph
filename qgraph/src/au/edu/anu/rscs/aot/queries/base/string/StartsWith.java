package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;

/**
 * A query to check that a {@link String} starts with a given sequence of characters.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class StartsWith extends QueryAdaptor {

	private String str;

	/**
	 * 
	 * @param str the initial String inputs must start with
	 */
	public StartsWith(String str) {
		this.str = str;
	}

	/**
	 * Only {@link String} arguments will be checked.
	 */
	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
		String localItem = (String) input;
		boolean ok = localItem.startsWith(str);
		if (!ok)
			errorMsg = "String must start with '" + str + "'.";
		return this;
	}

}
