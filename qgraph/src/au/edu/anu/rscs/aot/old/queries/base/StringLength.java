/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.old.queries.base;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class StringLength extends QueryAdaptor {
	
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
