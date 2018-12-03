package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class NotQuery extends QueryList {

	public NotQuery(Query...query) {
		super(query);
	}

	public static NotQuery notQuery(Query...query) {
		return new NotQuery(query);
	}
	
	// Processes
	//

	@Override
	public NotQuery process(Object item) {
		for (Query q : queryList()) {
			q.process(item);
			if (q.satisfied()) {
				satisfied = false;
				return this;
			}
		}
		satisfied = true;
		return this;		
	}

	// Helpers
	//
	
	public static Object getResult(Object input, Query... queries) {
		NotQuery queryList = new NotQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
	
	public static boolean satisfied(Object input, Query... queries) {
		NotQuery queryList = new NotQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}
	
	public static void check(Object input, Query... queries) {
		NotQuery queryList = new NotQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}

	public String toString() {
		return "[NotQuery " + super.toString() + "]";
	}

}
