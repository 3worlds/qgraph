package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * 
 * @author Shayne Flint - 27/8/2012
 *
 */
public class CountQuery extends Query {

	private int min;
	private int max;

	public CountQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}


	public static CountQuery hasMinCount(int min) {
		return new CountQuery(min, Integer.MAX_VALUE);		
	}

	public static CountQuery hasMaxCount(int max) {
		return new CountQuery(Integer.MIN_VALUE, max);		
	}

	public static CountQuery countInRange(int min, int max) {
		return new CountQuery(min, max);		
	}

	public static CountQuery countInRange(IntegerRange range) {
		return new CountQuery(range.getFirst(), range.getLast());		
	}

	public static CountQuery hasCount(int size) {
		return new CountQuery(size, size);		
	}

	@Override
	public CountQuery process(Object item) {
		defaultProcess(item);
		Integer localItem = (Integer)item;
		satisfied = (localItem >= min && localItem <= max);
		return this;
	}

	
	public String toString() {
		return "[Count must be in " + min + ".." + max + "]";
	}

}
