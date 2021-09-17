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
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsDouble extends QueryAdaptor {

	private double min;
	private double max;

	public IsDouble(double min, double max) {
		this.min = min;
		this.max = max;
	}

//	public static Queryable doubleInRange(double min, double max) {
//		return new IsDouble(min, max);
//	}
//
//	public static Queryable isDouble() {
//		return new IsDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
//	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Double)) {
			errorMsg = "Expected 'Double' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Double localItem = (Double) input;
		if (localItem > max || localItem < min) 
			errorMsg = "Expected Double to be in range ']" + min + ".." + max + "[' but found '" + localItem + "'";
		return this;
	}

}
