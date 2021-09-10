package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class StartsWith extends QueryAdaptor {

	private String str;

	public StartsWith(String str) {
		this.str = str;
	}

	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
		String localItem = (String) input;
		boolean ok = localItem.startsWith(str);
		if (!ok)
			errorMsg = "String must start with '" + str + "'.";
		return this;
	}


	@Override
	public String toString() {
		return "[String must start with '" + str + "']";
	}

}
