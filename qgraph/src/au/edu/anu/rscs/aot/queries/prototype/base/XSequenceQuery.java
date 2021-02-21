package au.edu.anu.rscs.aot.queries.prototype.base;

import java.util.Stack;

import au.edu.anu.rscs.aot.queries.prototype.queries.XQuery;
import au.edu.anu.rscs.aot.queries.prototype.queries.XQueryList;

/**
 * @author ian
 *
 *         AIM: To avoid co-opting the exception system and co-opting
 *         toString();
 */
public class XSequenceQuery extends XQueryList {

	public XSequenceQuery(XQuery... queries) {
		super(queries);
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input, Object.class);

		Stack<Object> stack = new Stack<Object>();
		stack.push(input);
		// NB loop stops processing at first failed query
		for (XQuery q : queryList()) {
			if (!processAQuery(stack, input, q)) {
//				satisfied = false;
				result = stack.peek();
				errorMsg = q.errorMsg();
				return this;
			}
			;
		}
		result = stack.peek();
		return this;
	}

	private boolean processAQuery(Stack<Object> stack, Object input, XQuery q) {
		try {
			q.process(stack.peek());
			stack.push(q.result());
			errorMsg = q.errorMsg();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMsg == null;
	}

	public static void main(String[] args) {
	}
}
