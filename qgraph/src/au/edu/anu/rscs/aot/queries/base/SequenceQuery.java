/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.base;


import java.util.Stack;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 28/3/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
public class SequenceQuery extends QueryList  {

	public SequenceQuery(Query...query) {
		super(query);
	}


	public String toString() {
		return "[SequenceQuery " + super.toString() + "]";
	}



	// Helpers
	//

	public static Object get(Object input, Query... queries) {
		SequenceQuery queryList = new SequenceQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
//	JG 3/12/2018
//	public static AotNode getNode(Object input, Query... queries) {
//		return (AotNode)get(input, queries);
//	}
//
//	public static NodeList getNodeList(Object input, Query... queries) {
//		return (NodeList)get(input, queries);
//	}
//
//	public static AotEdge getEdge(Object input, Query... queries) {
//		return (AotEdge)get(input, queries);
//	}

	public static boolean satisfied(Object input, Query... queries) {
		SequenceQuery queryList = new SequenceQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}

	public static void check(Object input, Query... queries) {
		SequenceQuery queryList = new SequenceQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}


	// Processes
	//


	public QueryList process(Object input) {
		defaultProcess(input);
		Stack<Object> stack = new Stack<Object>();
		stack.push(input);
//		Log.debug("QueryList.process(" + input + ")");
		for (Query query : queryList()) {
//			Log.debug("  processing query " + query);
			boolean ok = processTerm(stack, input, query);
//			Log.debug("    ok=" + ok);
			if (!ok) {
				satisfied = false;
				result = stack.peek();
				return this;
			}
		}
		result = stack.peek();
		return this;
	}


	@SuppressWarnings("unused")
	private boolean processTerm(Stack<Object> stack, Object obj, Query query) {
		if (query == null) {
			satisfied = false;
		} else {
			satisfied = true;
			try {
				if (query instanceof PopQuery) {
					int popCount = ((PopQuery)query).getCount();
//					Log.debug("  Popping " + popCount + " times");
					for (int i=0; i<popCount; i++) {
						Object item = stack.pop();
//						Log.debug("    Popped " + item);
					}

				} else if (query instanceof ResetQuery) {
					stack.clear();
					stack.push(obj);
//					Log.debug("  Pushed " + obj + " [reset]");

				} else if (query instanceof Query) {
					query.process(stack.peek());
					stack.push(query.getResult());
//					Log.debug("  Query " + query + " pushed " + query.getResult());
					satisfied = query.satisfied();

					//				} else if (query instanceof IfThenQuery) {
					//					IfThenAction action = (IfThenAction)query;
					//					if (action.testQuery().satisfied(stack.peek())) 
					//						processTerm(stack, obj, action.trueQuery());
					//					else
					//						processTerm(stack, obj, action.falseQuery());

				} else {
//					Log.debug("  Unknown query type");
					satisfied = false;
					throw new QGraphException("Don't know how to handle QueryTerm of type " + query.getClass().getName());
				}
			} catch (QGraphException e) {
				satisfied = false;
//				while (!stack.isEmpty()) {
//					Log.debug("Error:" + query + " = " + stack.pop().toString());
//				}
				throw e;
			}
		}
		return satisfied;
	}


	// TESTING
	//

//	public static void main(String[] args) {
//		Logger log = LoggerFactory.getLogger(SequenceQuery.class, "test main");
//
//		log.debug("Starting...");
//
//		check("Hello There", 
//				startsWith("He"), 
//				startsWith("Hell"), 
//				endsWith("ere"), 
//				containsSubstring("ello"), 
//				length(), integerInRange(1, 20),
//				pop(2),
//				startsWith("H"));
//
//		log.debug("Check one");
//
//		SequenceQuery cl = new SequenceQuery(
//				startsWith("He"), 
//				length(), integerInRange(1, 200), 
//				value("10.0.0.1"), isInetAddress());
//
//		check("Hello", cl);
//
//		log.debug("Check two");
//
//		log.debug((get("Hello There", startsWith("He"), startsWith("Hell"), length())).toString());
//
//		System.out.println("GET:" + get("Hello There", startsWith("He"), startsWith("Hell"), length(), integerInRange(1, 40)));
//
//		Integer a = (Integer)get("Hello There", startsWith("He"), startsWith("Hell"), length(), integerInRange(1, 40));
//
//		log.debug("Result = " + a);
//
//		Integer b = (Integer)get("Hello There", startsWith("He"), startsWith("Hell"), length(), integerInRange(1, 40));
//
//		log.debug("Result = " + b);
//	}

}
