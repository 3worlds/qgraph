package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * <p>Query testing if all its sub-queries are satisfied. Will stop checking at the first 
 * sub-query failure.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 28/3/2012<br/>
 * refactored by Ian Davies - Feb. 2021
 *
 */
public class AndQuery extends QueryList {

	public AndQuery(Queryable... queries) {
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
