package au.edu.anu.rscs.aot.queries.prototype.queries;

import au.edu.anu.rscs.aot.collections.DynamicList;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public abstract class XQueryList extends QueryAdaptor {

	private DynamicList<Queryable> queryList = new DynamicList<Queryable>();

	public XQueryList(Queryable...queries ) {
		for (Queryable q:queries)
			addQuery(q);
	}
	public XQueryList addQuery(Queryable...queries ) {
		for (Queryable q:queries)
			queryList.add(q);
		return this;
	}
	
	public DynamicList<Queryable> queryList() {
		return queryList;
	}
}
