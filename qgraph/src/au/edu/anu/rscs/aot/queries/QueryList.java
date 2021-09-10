package au.edu.anu.rscs.aot.queries;

import au.edu.anu.rscs.aot.collections.DynamicList;

/**
 * <p>An ancestor for compound queries grouping multiple queries.</p>
 * 
 * @author Shayne Flint - 26/3/2012 <br/>
 * refactored by Ian Davies - 23 Feb. 2021
 * 
 */
public abstract class QueryList extends QueryAdaptor {

	private DynamicList<Queryable> queryList = new DynamicList<Queryable>();

	/**
	 * 
	 * @param queries the queries grouped in this instance
	 */
	public QueryList(Queryable... queries) {
		for (Queryable q : queries)
			addQuery(q);
	}

	private QueryList addQuery(Queryable... queries) {
		for (Queryable q : queries)
			queryList.add(q);
		return this;
	}

	protected DynamicList<Queryable> queryList() {
		return queryList;
	}

	@Override
	public String toString() {
		StringBuilder description = new StringBuilder().append("[");
		if (queryList.isEmpty())
			description.append("]");
		else
			for (Queryable q : queryList()) {
				description.append(q.toString());
			}
		return description.append("]").toString();
	}

}
