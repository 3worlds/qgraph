package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class ResetQuery extends Query {
		
	public ResetQuery() {
	}

	public static Query reset() {
		return new ResetQuery();
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		throw new QGraphException("Shouldn't be called");
	}
	
	public String toString() {
		return "[reset]";
	}

}
