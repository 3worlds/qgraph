package au.edu.anu.rscs.aot.queries.prototype.queries;

import au.edu.anu.rscs.aot.collections.DynamicList;

public abstract class XQueryList extends XQuery {

	private DynamicList<XQuery> queryList = new DynamicList<XQuery>();

	public XQueryList(XQuery...queries ) {
		for (XQuery q:queries)
			addQuery(q);
	}
	public XQueryList addQuery(XQuery...queries ) {
		for (XQuery q:queries)
			queryList.add(q);
		return this;
	}
	
	public DynamicList<XQuery> queryList() {
		return queryList;
	}
}
