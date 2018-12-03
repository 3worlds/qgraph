/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries;

import java.util.Iterator;

import au.edu.anu.rscs.aot.collections.DynamicList;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public abstract class QueryList extends Query {

	public QueryList(Query... query) {
		for (Query c : query)
			addQuery(c);
	}

	
	private DynamicList<Query> queryList = new DynamicList<Query>();

	public DynamicList<Query> queryList() {
		return queryList;
	}

	public QueryList addQuery(Query... queries) {
		for (Query q : queries)
			queryList.add(q);
		return this;
	}

	public QueryList removeQuery(Query... queries) {
		for (Query q : queries)
			queryList.remove(q);
		return this;
	}

	public QueryList clear() {
		queryList.clear();
		return this;
	}

	public int size() {
		return queryList.size();
	}

	public void log(String label) {
		// TODO Auto-generated method stub	
	}


	public String toString() {
		String description;
		if (queryList.size() == 0)
			description = "[]";
		else {
			Iterator<Query> iter = queryList().iterator();
			description = "[" + iter.next().toString();
			while (iter.hasNext())
				description = description + ", " + iter.next().toString();
		}
		return description + "]";
	}

}
