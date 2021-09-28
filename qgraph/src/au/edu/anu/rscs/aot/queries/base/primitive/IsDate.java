/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.primitive;

import java.text.SimpleDateFormat;
import java.util.Date;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Check if an object is a {@link Date}, or if it is within a given date range.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Object}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input is not a {@code Date}</li>
 * <li>input value is not the within the range passed in the constructor</li>
 * </ol></dd> 
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#isDate() CoreQueries.isDate()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#dateInRange(Date, Date) CoreQueries.dateInRange(...)
 *
 */
public class IsDate extends QueryAdaptor {

	private Date min;
	private Date max;

	/**
	 * Constructor with a date range.
	 * @param min the lower end of the date range
	 * @param max the upper end of the date range
	 */
	public IsDate(Date min, Date max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Date)) {
			errorMsg = "Expected '"+Date.class.getName()+"' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Date localItem = (Date) input;
		if (min != null && max != null) {
			if (localItem.after(max) || localItem.before(min)) {
				SimpleDateFormat df = new SimpleDateFormat("MMM dd,yyyy HH:mm:ss.SSS");
				errorMsg = "Expected Date to be in range ']" + df.format(min) + ".." + df.format(max) + "[' but found '"
						+ df.format(localItem) + "'";
				return this;
			}
		}

		return this;
	}

}
