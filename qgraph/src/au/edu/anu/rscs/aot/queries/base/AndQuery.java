package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 28/3/2012
 *
 */
public class AndQuery extends QueryList {

	public AndQuery(Query...query) {
		super(query);
	}

	public static AndQuery andQuery(Query...query) {
		return new AndQuery(query);
	}

	// Processes
	//

	@Override
	public AndQuery process(Object item) {
		defaultProcess(item);
		for (Query q : queryList()) {
			q.process(item);
			satisfied = q.satisfied();
			if (!satisfied()) {
				return this;
			}
		}
		satisfied = true;
		return this;
	}

	
	// Helpers
	//
	
	public static Object getResult(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
	
	public static boolean satisfied(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}
	
	public static void check(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}


	public String toString() {
		return "[AndQuery " + super.toString() + "]";
	}
	

}
