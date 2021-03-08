package au.edu.anu.rscs.aot.queries;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public abstract class QueryAdaptor implements Queryable {
	protected Object result;
	protected String errorMsg;
	protected String actionMsg;

	protected Queryable initInput(Object input/* , Class<?> expected */) {
		result = input;
		// NB: default is satisfied() == true;
		errorMsg = null;
		actionMsg = null;
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
	public String actionMsg() {
		return actionMsg;
	}

	@Override
	public Object result() {
		return result;
	}

	@Override
	public String toString() {
		if (satisfied())
			return "[" + this.getClass().getSimpleName() + " is TRUE]";
		else
			return "[" + this.getClass().getSimpleName() + " is FALSE [" + errorMsg + "]]";
	}

}
