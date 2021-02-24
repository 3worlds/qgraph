package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.XQueryList;

public class XXorQuery extends XQueryList {

	public XXorQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable query(Object input) {
		initQuery(input, Object.class);
		int count = 0;
		for (Queryable q : queryList()) {
			q.query(input);
			if (q.satisfied()) {
				count++;
				if (count > 1) {
					errorMsg = this + "expected only ONE query to be TRUE.";
					return this;
				}
			}
		}
		if (count != 1)
			errorMsg = this + "expected only ONE query to be TRUE.";
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + " " + super.toString() + "]";
	}

}
