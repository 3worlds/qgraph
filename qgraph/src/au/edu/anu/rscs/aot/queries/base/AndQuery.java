package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

public class AndQuery extends QueryList {

	public AndQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		for (Queryable q : queryList()) {
			q.submit(input);
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
