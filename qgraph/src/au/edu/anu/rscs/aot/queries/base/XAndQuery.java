package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.XQueryList;

public class XAndQuery extends XQueryList {

	public XAndQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable query(Object input) {
		initQuery(input, Object.class);
		for (Queryable q : queryList()) {
			q.query(input);
			errorMsg = q.errorMsg();
			if (!satisfied())
				return this;
		}
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + " " + super.toString() + "]";
	}

}
