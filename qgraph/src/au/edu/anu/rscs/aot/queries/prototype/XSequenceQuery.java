package au.edu.anu.rscs.aot.queries.prototype;

import java.util.Stack;

/**
 * @author ian
 *
 *AIM: Avoid co-opting the exception system.
 *Should we also avoid co-opting toString;
 */
public class XSequenceQuery extends XQueryList {

	public XSequenceQuery(XQuery... queries) {
		super(queries);
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input);
		Stack<Object> stack = new Stack<Object>();
		stack.push(input);
		// NB loop stops processing at first failed query
		for (XQuery q:queryList()) {
			if (!processAQuery(stack,input,q)) {
				satisfied = false;
				result = stack.peek();
				failMsg = q.failMsg;
				return this;
			};
		}
		result = stack.peek();
		return this;
	}

	private boolean processAQuery(Stack<Object> stack, Object input, XQuery q) {
		try{
			q.process(stack.peek());
			stack.push(q.getResult());
			satisfied = q.satisfied();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return satisfied;
	}
}
