package au.edu.anu.rscs.aot.queries;

import au.edu.anu.rscs.aot.QGraphException;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public abstract class QueryAdaptor implements Queryable {
	protected Object result;
	protected String errorMsg;

	protected Queryable initQuery(Object input, Class<?> expected) {
		if (!expected.isInstance(input))
			throw new QGraphException("'" + this.getClass().getSimpleName() + "' expected '" + expected.getSimpleName()
					+ "' as input but '" + input.getClass().getSimpleName() + "' was found.");

		result = input;
		// NB: default is satisfied is true;
		errorMsg = null;
		return this;
	}

	@Override
	public boolean satisfied() {
		return errorMsg == null;
	}

	@Override
	public String errorMsg() {
		return errorMsg;
	}

	@Override
	public Object result() {
		return result;
	}

}
