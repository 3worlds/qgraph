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
public class StringLength extends Query {
	
	public StringLength() {
	}

	public static StringLength length() {
		return new StringLength();
	}

	@Override
	public StringLength process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		result = localItem.length();
		satisfied = true;
		return this;
	}

	public String toString() {
		return "[String length]";
	}

}
