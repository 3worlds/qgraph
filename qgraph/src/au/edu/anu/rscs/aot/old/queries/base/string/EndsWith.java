/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.old.queries.base.string;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class EndsWith extends Query {
	
	private String str;
	
	public EndsWith(String str) {
		this.str = str;
	}

	public static EndsWith endsWith(String str) {
		return new EndsWith(str);
	}

	@Override
	public EndsWith process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = localItem.endsWith(str);
		return this;
	}
	
	public String toString() {
		return "[String must end with '" + str + "']";
	}

}
