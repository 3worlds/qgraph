package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class OrQuery extends QueryList {

	public OrQuery(Query...query) {
		super(query);
	}

	public static OrQuery orQuery(Query...query) {
		return new OrQuery(query);
	}

	
	// Processes
	//

	@Override
	public OrQuery process(Object item) {
		for (Query q : queryList()) {
			q.process(item);
			if (q.satisfied()) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;		
	}

	
	// Helpers
	//
	
	public static Object getResult(Object input, Query... queries) {
		OrQuery queryList = new OrQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
	
	public static boolean satisfied(Object input, Query... queries) {
		OrQuery queryList = new OrQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}
	
	public static void check(Object input, Query... queries) {
		OrQuery queryList = new OrQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}

	public String toString() {
		return "[OrQuery " + super.toString() + "]";
	}

}
