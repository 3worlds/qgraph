package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class XorQuery extends QueryList {

	public XorQuery(Query...query) {
		super(query);
	}

	public static XorQuery xorQuery(Query...query) {
		return new XorQuery(query);
	}

	// Processes
	//

	@Override
	public XorQuery process(Object item) {
		int count = 0;
		for (Query q : queryList()) {
			q.process(item);
			if (q.satisfied()) {
				count++;
				if (count > 1) {
					satisfied = false;
					return this;
				}
			}
		}
		satisfied = (count == 1);
		return this;		
	}

	// Helpers
	//
	
	public static Object getResult(Object input, Query... queries) {
		XorQuery queryList = new XorQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
	
	public static boolean satisfied(Object input, Query... queries) {
		XorQuery queryList = new XorQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}
	
	public static void check(Object input, Query... queries) {
		XorQuery queryList = new XorQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}

	public String toString() {
		return "[XorQuery " + super.toString() + "]";
	}

}
