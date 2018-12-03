/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.string;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.Version;


public class VersionString extends Query {

	public static VersionString asVersion() {
		return new VersionString();
	}
	
	public static VersionString isVersion() {
		return new VersionString();
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Version typedItem = new Version(localItem);
				satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Version number]";
	}

}
