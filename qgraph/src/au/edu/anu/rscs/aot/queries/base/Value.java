package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class Value extends Query {

	private Object obj;
	
	public Value(Object obj) {
		this.obj = obj;
	}
	
	public static Query value(Object item) {
		return new Value(item);
	}

	@Override
	public Value process(Object item) {
		defaultProcess(item);
		result = obj;
		satisfied = true;
		return this;
	}

	public String toString() {
		return "[Value " + obj + "]";
	}

}
