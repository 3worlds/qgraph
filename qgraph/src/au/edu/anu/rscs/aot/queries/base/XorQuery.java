package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

public class XorQuery extends QueryList {

	public XorQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		int count = 0;
		for (Queryable q : queryList()) {
			q.submit(input);
			if (q.satisfied()) {
				count++;
				if (count > 1) {
					errorMsg = this + "expected only ONE query to be TRUE.";
					return this;
				}
			}
		}
		if (count == 0)
			errorMsg = this + "expected ONE query to be TRUE.";
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + " " + super.toString() + "]";
	}

}
