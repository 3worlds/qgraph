package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class PopQuery extends Query {
	
	private int count;
	
	public PopQuery(int count) {
	  this.count = count;
	}

	public static Query pop(int count) {
		return new PopQuery(count);
	}

	public static Query pop() {
		return new PopQuery(1);
	}
	
	public int getCount() {
		return count;
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		throw new QGraphException("Shouldn't be called");
	}
	
	public String toString() {
		return "[Pop " + count + "]";
	}

}
