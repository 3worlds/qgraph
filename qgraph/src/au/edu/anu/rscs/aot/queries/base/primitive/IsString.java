package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.queries.Query;
import static au.edu.anu.rscs.aot.util.NumberRange.*;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsString extends Query {

	private int minLength;
	private int maxLength;

	public IsString(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public static Query stringOfLength(int minLength, int maxLength) {
		return new IsString(minLength, maxLength);
	}

	public static Query isString() {
		return new IsString(0, Integer.MAX_VALUE);
	}

	public String toString() {
		if (minLength == 0 && maxLength == Integer.MAX_VALUE)
			return "[isString]";
		else
			return "[isString with length in " + range(minLength, maxLength) + "]";
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		if (item instanceof String) {
			String localItem = (String)item;
			satisfied = (localItem.length() >= minLength && localItem.length() <= maxLength);
		} else
			satisfied = false;
		return this;
	}

}
