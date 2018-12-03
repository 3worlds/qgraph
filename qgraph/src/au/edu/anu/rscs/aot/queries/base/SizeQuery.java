package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.IntegerRange;
import fr.ens.biologie.generic.Sizeable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class SizeQuery extends Query {

	private int min;
	private int max;

	public SizeQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}


	public static SizeQuery hasMin(int min) {
		return new SizeQuery(min, Integer.MAX_VALUE);		
	}

	public static SizeQuery hasMax(int max) {
		return new SizeQuery(Integer.MIN_VALUE, max);		
	}

	public static SizeQuery inRange(int min, int max) {
		return new SizeQuery(min, max);		
	}

	public static SizeQuery inRange(IntegerRange range) {
		return new SizeQuery(range.getFirst(), range.getLast());		
	}

	public static SizeQuery hasSize(int size) {
		return new SizeQuery(size, size);		
	}

	public static SizeQuery hasOne() {
		return new SizeQuery(1, 1);		
	}

	public static SizeQuery hasTwo() {
		return new SizeQuery(2, 2);		
	}

	public static SizeQuery hasZeroOrOne() {
		return new SizeQuery(0, 1);		
	}

	public static SizeQuery hasZero() {
		return new SizeQuery(0, 0);		
	}

	public static SizeQuery hasOneOrMany() {
		return new SizeQuery(1, Integer.MAX_VALUE);		
	}

	public static SizeQuery hasZeroOrMany() {
		return new SizeQuery(0, Integer.MAX_VALUE);		
	}


	@Override
	public SizeQuery process(Object item) {
		defaultProcess(item);
		Sizeable localItem = (Sizeable)item;
		long count = localItem.size();
		satisfied  = count >= min && count <= max;
		return this;
	}

	
	public String toString() {
		return "[Size must be in " + min + ".." + max + "]";
	}

}
