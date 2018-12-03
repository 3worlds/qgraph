package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class ForAllQuery extends Query {
		
	private Query query;
	
	public ForAllQuery(Query query) {
		this.query = query;
	}

	public static Query forAll(Query query) {
		return new ForAllQuery(query);
	}

	public Query getQuery() {
		return query;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean satisfied(Object item) {
		Iterable<Object> localItem = (Iterable<Object>)item;
		for (Object obj : localItem)
			if (!query.satisfied(obj))
				return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ForAllQuery process(Object item) {
		defaultProcess(item);
		Iterable<Object> localItem = (Iterable<Object>)item;
		for (Object obj : localItem) {
			query.process(obj);
			satisfied = query.satisfied();
			if (!satisfied()) {
				return this;
			}
		}
		satisfied = true;
		return this;
	}
	
}
