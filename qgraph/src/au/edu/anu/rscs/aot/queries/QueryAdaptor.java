package au.edu.anu.rscs.aot.queries;

/**
 * <p>An ancestor implementation for queries with default behaviour for all methods of 
 * {@link Queryable} but {@code submit(...)}.</p>
 * 
 * <p>Rules for writing descendants:</p>
 * <ul>
 * <li>call {@code initInput(input)} in {@code submit(...)} to reset field values to default (
 * {@code satisfied=true, errorMsg=null, actionMsg=null}).</li>
 * <li>only test what this query is meant to check. e.g., if {@code input==null} the query
 * should return satisfied unless it is an {@code isNull} query. </li>
 * </ul>
 * 
 * @author Ian Davies - 23 Feb. 2021
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
