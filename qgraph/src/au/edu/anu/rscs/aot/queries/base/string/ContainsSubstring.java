package au.edu.anu.rscs.aot.queries.base.string;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
import au.edu.anu.rscs.aot.queries.Query;

public class ContainsSubstring extends Query {
	
	private String str;
	
	public ContainsSubstring(String str) {
		this.str = str;
	}

	public static ContainsSubstring containsSubstring(String str) {
		return new ContainsSubstring(str);
	}

	@Override
	public ContainsSubstring process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = localItem.indexOf(str) >= 0;
		return this;
	}
	
	public String toString() {
		return "[String must contain '" + str + "']";
	}

}
