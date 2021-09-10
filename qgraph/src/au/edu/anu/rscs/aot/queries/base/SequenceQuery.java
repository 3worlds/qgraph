package au.edu.anu.rscs.aot.queries.base;

import java.util.Stack;

import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * <p>Chains a sequence of queries, i.e. the {@code result()} of each query serves as an
 * input into {@code submit(...)} of the next query.</p>
 *
 * @author Shayne Flint - 28/3/2012
 * @author Ian Davies - 23 Feb. 2021
 * 
 */
public class SequenceQuery extends QueryList {

	public SequenceQuery(Queryable... queries) {
		super(queries);
	}

	/**
	 * Argument can be of any class.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Stack<Object> stack = new Stack<Object>();
		stack.push(input);
		// NB loop stops processing at first failed query
		for (Queryable q : queryList()) {
			if (!processAQuery(stack, input, q)) {
				result = stack.peek();
				errorMsg = q.errorMsg();
				return this;
			}
		}
		result = stack.peek();
		return this;
	}

	private boolean processAQuery(Stack<Object> stack, Object input, Queryable q) {
		try {
			q.submit(stack.peek());
			stack.push(q.result());
			errorMsg = q.errorMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMsg == null;
	}

	/**
	 * Instead of get(...) return the sq and then interrogate: if errorMsg()!=null
	 * use the result. So I've change get(...) to apply(...)?
	 */
	/**
	 * <p>Applies a sequence of queries to an object and return the query list.</p>
	 * 
	 * @param input the object on which to apply queries
	 * @param queries the sequence a queries to apply to the object
	 * @return the sequence of queries
	 */
	public static SequenceQuery apply(Object input, Queryable... queries) {
		SequenceQuery sq = new SequenceQuery(queries);
		sq.submit(input);
		return sq;
	}
	
	/**
	 * <p>Get the result of a sequence of queries applied on an object.</p>
	 * <p>This method is specially designed to work in interaction with {@link CoreQueries} static
	 * methods to query particular elements of a {@link Graph}. Examples:</p>
	 * <p>return all the OUT edges of a node labelled 'blue':</p>
	 * <pre>
	 * get(node,
	 * 	edges(Direction.OUT), 
	 * 	edgeListEndNodes(),
	 * 	selectZeroOrMany(hasTheLabel("blue")));
	 * </pre>
	 * 
	 * @param input the object on which to apply queries
	 * @param queries the sequence a queries to apply to the object
	 * @return the result of the last query
	 */
	public static Object get(Object input, Queryable...queries) {
		SequenceQuery sq = apply(input,queries);
		return sq.result();
	}

}
