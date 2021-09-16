/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class EndsWith extends QueryAdaptor {
	
	private String str;
	
	public EndsWith(String str) {
		this.str = str;
	}

	public static Queryable endsWith(String str) {
		return new EndsWith(str);
	}


	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String localItem = (String)input;
		if (!localItem.endsWith(str))
			errorMsg = "Expected '"+localItem+"' to end with '"+str+"'.";
		return this;
	}

}
