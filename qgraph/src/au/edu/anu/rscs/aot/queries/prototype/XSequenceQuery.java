package au.edu.anu.rscs.aot.queries.prototype;

import java.util.Stack;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.base.SequenceQuery;
import au.edu.anu.rscs.aot.queries.base.SizeQuery;

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
//				satisfied = false;
				result = stack.peek();
				errorMsg = q.errorMsg;
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
//			satisfied = q.satisfied();
			errorMsg = q.errorMsg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return errorMsg==null;
	}
	
	public static Object get(Object input, XQuery... queries) {
		XSequenceQuery q = new XSequenceQuery(queries);
		q.process(input);
		return q.getResult();
	}
	
	public static void main(String[] args) {
		DynamicList<Integer> sizeable = new DynamicList<>();
		sizeable.add(1);
		System.out.println(SequenceQuery.get(sizeable, new SizeQuery(2,3),new SizeQuery(2,9)).getClass());
		Object result = XSequenceQuery.get(sizeable, new XIntRangeQuery(2, 3), new XIntRangeQuery(2, 9));
		System.out.println(result.getClass());
//		XSequenceQuery sq = new XSequenceQuery(new XIntRangeQuery(1, 3), new XIntRangeQuery(1, 9));
//		sq.process(sizeable);
//		System.out.println(sq.failMsg);
	}

}
