/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.primitive;

import java.text.DateFormat;
import java.util.Date;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsDate extends QueryAdaptor {

	private Date min;
	private Date max;

	public IsDate(Date min, Date max) {
		this.min = min;
		this.max = max;
	}

	public static Queryable dateInRange(Date min, Date max) {
		return new IsDate(min, max);
	}

	public static Queryable isDate() {
		return new IsDate(null, null);
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Date)) {
			errorMsg = "Expected 'Date' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Date localItem = (Date) input;
		if (min != null && max != null) {
			if (localItem.after(max) && localItem.before(min)) {
				DateFormat df = DateFormat.getDateInstance();
				errorMsg = "Expected Date to be in range ']" + df.format(min) + ".." + df.format(max) + "[' but found '"
						+ df.format(localItem) + "'";
				return this;
			}
		}

		return this;
	}

}
