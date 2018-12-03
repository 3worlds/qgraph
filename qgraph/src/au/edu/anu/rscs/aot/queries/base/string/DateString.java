package au.edu.anu.rscs.aot.queries.base.string;

import java.text.DateFormat;
import java.util.Date;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class DateString extends Query {
	
	public static DateString isDate() {
		return new DateString();
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean satisfied(Object item) {
		String localItem = (String)item;
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			Date typedItem = df.parse(localItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public DateString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			Date typedItem = df.parse(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Date]";
	}

}
