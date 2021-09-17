/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>A query to get the length of a {@link String}.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code String}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link Integer}</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class StringLength extends QueryAdaptor {
	
	public StringLength() {
	}

//	@Override
//	public StringLength process(Object item) {
//		defaultProcess(item);
//		String localItem = (String)item;
//		result = localItem.length();
//		satisfied = true;
//		return this;
//	}
//
//	public String toString() {
//		return "[String length]";
//	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String localItem = (String)input;
		result = localItem.length();
		return this;
	}

}
