package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsBoolean extends Query {

	public IsBoolean() {
	}

	public static Query isBoolean() {
		return new IsBoolean();
	}

	@Override
	public IsBoolean process(Object item) {
		defaultProcess(item);
		satisfied = item instanceof Boolean;
		return this;
	}

	@Override
	public String toString() {
		return "[isBoolean]";
	}
	

}
