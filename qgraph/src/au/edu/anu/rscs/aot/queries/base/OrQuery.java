package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * <p>Query testing if at least one its sub-queries is satisfied. Will stop checking at the first 
 * sub-query success.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>none of the queries passed to the constructor is satisfied when applied to input</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#orQuery(Queryable...) CoreQueries.orQuery(...)
 *
 */
public class OrQuery extends QueryList {

	public OrQuery(Queryable... queries) {
		super(queries);
	}

	/**
	 * Argument can be of any class.
	 */
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
