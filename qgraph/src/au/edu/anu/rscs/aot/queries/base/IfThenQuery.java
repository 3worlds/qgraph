package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IfThenQuery extends Query {

	private Query testQuery;
	private Query trueQuery;
	private Query falseQuery;
	
	public IfThenQuery(Query testQuery, Query trueQuery, Query falseQuery) {
		this.testQuery  = testQuery;
		this.trueQuery  = trueQuery;
		this.falseQuery = falseQuery;
	}

	public IfThenQuery(Query testQuery, Query trueQuery) {
		this.testQuery  = testQuery;
		this.trueQuery  = trueQuery;
		this.falseQuery = null;
	}

	public Query getTestQuery() {
		return testQuery;
	}
	
	public Query getTrueQuery() {
		return trueQuery;
	}
	
	public Query getFalseQuery() {
		return falseQuery;
	}
	
	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery, Query falseQuery) {
		return new IfThenQuery(testQuery, trueQuery, falseQuery);
	}
	
	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery) {
		return new IfThenQuery(testQuery, trueQuery);
	}
	

	public String toString() {
		String result = "[if " + testQuery + " then " + trueQuery;
		if (falseQuery != null)
			result = result + " else " + falseQuery;
		result = result + "]";
		return result;
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		testQuery.process(item);
		if (testQuery.satisfied()) {
			trueQuery.process(item);
			result = trueQuery.getResult();
			satisfied = trueQuery.satisfied();
		} else {
			falseQuery.process(item);
			result = falseQuery.getResult();
			satisfied = falseQuery.satisfied();
		}
		return this;
	}
	
}
