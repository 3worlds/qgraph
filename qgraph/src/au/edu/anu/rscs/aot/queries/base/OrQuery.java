package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

public class OrQuery extends QueryList {

	public OrQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		for (Queryable q : queryList()) {
			q.submit(input);
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
