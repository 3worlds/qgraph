package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * Query testing if exactly one its sub-queries is satisfied.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class XorQuery extends QueryList {

	public XorQuery(Queryable... queries) {
		super(queries);
	}

	/**
	 * Argument can be of any class.
	 */
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
