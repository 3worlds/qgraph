/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Check if an object is a {@link double}, or if it is within a given range.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Object}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input is not a {@code Double}</li>
 * <li>input value is not the within the range passed in the constructor</li>
 * </ol></dd> 
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#isDouble() CoreQueries.isDouble()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#doubleInRange(double, double) CoreQueries.doubleInRange(...)
 *
 */
public class IsDouble extends QueryAdaptor {

	private double min;
	private double max;

	/**
	 * Constructor with a range
	 * @param min the lower end of the range
	 * @param max  the upper end of the range
	 */
	public IsDouble(double min, double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Double)) {
			errorMsg = "Expected '"+Double.class.getName()+"' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Double localItem = (Double) input;
		if (localItem > max || localItem < min) 
			errorMsg = "Expected Double to be in range ']" + min + ".." + max + "[' but found '" + localItem + "'";
		return this;
	}

}
