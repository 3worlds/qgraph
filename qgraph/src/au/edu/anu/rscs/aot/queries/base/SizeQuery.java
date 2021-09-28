package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import fr.ens.biologie.generic.Sizeable;

/**
 * Query testing if the size of a {@link Sizeable} object is within a specified
 * range.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Sizeable}</dd>
 * <dt>Type of result</dt>
 * <dd>Sizeable ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input size is not within the (integer) range set with the {@link #min(int)} and
 * {@link #max(int)} methods.</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#inRange(int, int) CoreQueries.inRange(int,int)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#hasMin(int) CoreQueries.hasMin(...)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#hasMax(int) CoreQueries.hasMax(...)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#inRange(au.edu.anu.rscs.aot.util.IntegerRange) CoreQueries.inRange(IntegerRange)
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
		boolean ok = (size >= min) && (size <= max);
		if (!ok)
			errorMsg = "Expected size of '" + localItem.getClass().getSimpleName() + "' to be within the range [" + min
					+ ".." + max + "] but found size of '" + localItem.size() + "'.";
		return this;
	}

	/**
	 * Set the range minimum value (default = 0)
	 * 
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery min(int m) {
		min = m;
		return this;
	}

	/**
	 * Set the range maximum value (default = {@code Integer.MAX_VALUE})
	 * 
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery max(int m) {
		max = m;
		return this;
	}

}
