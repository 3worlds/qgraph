/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.primitive;

import java.text.DateFormat;
import java.util.Date;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsDate extends Query {

	private Date min;
	private Date max;

	public IsDate(Date min, Date max) {
		this.min = min;
		this.max = max;
	}

	public static Query dateInRange(Date min, Date max) {
		return new IsDate(min, max);
	}

	public static Query isDate() {
		return new IsDate(null, null);
	}

	public String toString() {
		DateFormat df = DateFormat.getDateInstance();
		return "[Must be Date in " + df.format(min) + ".." + df.format(max) + "]";
	}

	@Override
	public boolean satisfied(Object item) {
		if (item instanceof Date) {
			Date localItem = (Date)item;
			if (min!=null && max!=null)
				return (localItem.before(max) && localItem.after(min));
			else
				return true;
		} else
			return false;
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		if (item instanceof Date) {
			Date localItem = (Date)item;
			if (min!=null && max!=null)
				satisfied = (localItem.before(max) && localItem.after(min));
			else
				satisfied = true;
		} else
			satisfied = false;
		return this;
	}

}
