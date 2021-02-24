package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.XQueryList;

public class XOrQuery extends XQueryList {

	public XOrQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable query(Object input) {
		initQuery(input, Object.class);
		for (Queryable q : queryList()) {
			q.query(input);
			if (q.satisfied())
				return this;
		}
		errorMsg = this + " expected at least one query to be TRUE";
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + " " + super.toString() + "]";
	}

}
