package au.edu.anu.rscs.aot.queries;


/**
 * <p>
 * The ancestor class for the Query system.
 * </p>
 * <h3>Usage:</h3>
 * <p>
 * All Queries work in the same way:
 * </p>
 * <ul>
 * <li>An implementation of {@code Queryable}, matching the particular condition to fulfill, is
 * constructed with some parameters needed to perform the query;</li>
 * <li>Later on, a call to {@code Query.satisfied()} will check the
 * condition and return true if the Query was satisfied;</li>
 * <li>Alternatively, a call to {@code Query.check(Object)} can be made, that
 * will return an Exception if the query is not satisfied.</li>
 * </ul>
 * <p>
 * Queries in the base package apply to non-graph objects.
 * </p>
 * <p>
 * The {@code submit(...)} method parameter type is generic ({@link Object}), but
 * for every particular Query a specific type is required (cf. the javadoc for
 * each specific Query type).
 * </p>
 * <p>
 * Some queries compute a result, accessible through the {@code result()}
 * method. The default return type is {@code Object}, but the actual return type
 * has to be known (cf. the javadoc for each specific Query type). A typical use
 * case is returning a filtered list when the input to {@code submit(...)} is a
 * list of items.
 * </p>
 * 
 * @author Shayne Flint - 15/3/2012
 * @author refactored by Ian Davies - 23 Feb. 2021
 */
public interface Queryable {
	
	/**
	 * Checks this query on the argument
	 * @param input the object to check
	 * @return this instance for agile programming
	 */
	public Queryable submit(Object input);
	
	/**
	 * Get the query output
	 * @return {@code true} if the query is satisfied, {@code false} otherwise
	 */
	public boolean satisfied();
	
	/**
	 * Get explanations why the query failed
	 * @return an error message
	 */
	public String errorMsg();
	
	/**
	 * Get explanations on what to correct to pass the query
	 * @return an action message
	 */
	public String actionMsg();
	
	/**
	 * Get whatever was computed as a side-effect by the query
	 * @return the result of the query - by default, this is the last object passed as an argument
	 * to {@code submit(...)}
	 */
	public Object result();

}
