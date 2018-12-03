/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class PatternString extends Query {

	private String pattern;
	
	public PatternString(String pattern) {
		this.pattern = pattern;
	}
	
	public static PatternString matchesPattern(String pattern) {
		return new PatternString(pattern);
	}
	
	@Override
	public PatternString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(localItem);
		satisfied = m.matches();
		return this;
	}

	public String toString() {
		return "[String must match '" + pattern + "']";
	}

}
