package au.edu.anu.rscs.aot.queries.prototype.base;

import java.util.Stack;

import au.edu.anu.rscs.aot.queries.prototype.queries.Queryable;
import au.edu.anu.rscs.aot.queries.prototype.queries.XQueryList;

/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021 * 
 * 
 * AIM: To avoid co-opting exceptions and toString();
 * 
 * Based on work by Shayne Flint and Jacques Gignoux
 * 
 */
public class XSequenceQuery extends XQueryList {

	public XSequenceQuery(Queryable... queries) {
		super(queries);
	}

	@Override
	public Queryable query(Object input) {
		initProcess(input, Object.class);
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
			q.query(stack.peek());
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
	public static XSequenceQuery apply(Object input, Queryable... queries) {
		XSequenceQuery sq = new XSequenceQuery(queries);
		sq.query(input);
		return sq;
	}

}
