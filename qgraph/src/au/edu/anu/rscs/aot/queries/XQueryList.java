package au.edu.anu.rscs.aot.queries;

import au.edu.anu.rscs.aot.collections.DynamicList;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public abstract class XQueryList extends QueryAdaptor {

	private DynamicList<Queryable> queryList = new DynamicList<Queryable>();

	public XQueryList(Queryable... queries) {
		for (Queryable q : queries)
			addQuery(q);
	}

	private XQueryList addQuery(Queryable... queries) {
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
