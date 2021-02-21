package au.edu.anu.rscs.aot.queries.prototype.queries;

import au.edu.anu.rscs.aot.QGraphException;

public abstract class XQuery {
	protected Object result;
	protected String errorMsg;

	public abstract XQuery process(Object input);

	protected XQuery initProcess(Object input, Class<?> expected) {
		if (!expected.isInstance(input)) {
			throw new QGraphException("'" + this.getClass().getSimpleName() + "' expected '" + expected.getSimpleName()
					+ "' as input but '" + input.getClass().getSimpleName() + "' was found.");
		}
		result = input;
		errorMsg = null;
		return this;
	}

	public String errorMsg() {
		return errorMsg;
	}

	public Object result() {
		return result;
	}

}
