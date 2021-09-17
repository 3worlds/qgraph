/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.string;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class PatternString extends QueryAdaptor {

	private String pattern;
	
	public PatternString(String pattern) {
		this.pattern = pattern;
	}
	
//	public static Queryable matchesPattern(String pattern) {
//		return new PatternString(pattern);
//	}
	
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher((String)input);
		if (!m.matches())
			errorMsg = "Expected '"+input+"' to match pattern "+pattern;
		return null;
	}

}
