package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import fr.ens.biologie.generic.Sizeable;

/**
 * Query testing if the size of a {@link Sizeable} object is within a specified range.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class SizeQuery extends QueryAdaptor {
	private int min;
	private int max;

	/**
	 * Default constructor with the range [0;+∞[
	 */
	public SizeQuery() {
		this.min = 0;
		this.max = Integer.MAX_VALUE;
	}

	/**
	 * Only {@link Sizeable} arguments will be checked.
	 */
	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
		Sizeable localItem = (Sizeable) result;
		long size = localItem.size();
		boolean ok = size >= min && size <= max;
		if (!ok)
			errorMsg = "Size of '" + localItem.getClass().getSimpleName() + "' is '" + localItem.size() + "' but "
					+ this;
		return this;
	}

	@Override
	public String toString() {
		return "[Size must be within " + min + ".." + max + " inclusive.]";
	}

	/**
	 * Set the range minimum value (default = 0)
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery min(int m) {
		min = m;
		return this;
	}
	
	/**
	 * Set the range maximum value (default = {@code Integer.MAX_VALUE})
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery max(int m) {
		max = m;
		return this;
	}
	
}
