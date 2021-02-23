package au.edu.anu.rscs.aot.queries.prototype.queries;

import au.edu.anu.rscs.aot.QGraphException;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public abstract class QueryAdaptor implements Queryable{
	protected Object result;
	protected String errorMsg;

	protected Queryable initProcess(Object input, Class<?> expected) {
		if (!expected.isInstance(input)) {
			throw new QGraphException("'" + this.getClass().getSimpleName() + "' expected '" + expected.getSimpleName()
					+ "' as input but '" + input.getClass().getSimpleName() + "' was found.");
		}
		result = input;
		errorMsg = null;
		return this;
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
