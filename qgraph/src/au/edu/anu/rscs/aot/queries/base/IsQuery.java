package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * <p>Checks that an object is a Query.</p>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsQuery extends Query {

	public static Query isQuery() {
		return new IsQuery();
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		satisfied = item instanceof Query;
		return this;
	}

	public String toString() {
		return "[must be a query]";

	}

}
