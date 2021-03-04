/**************************************************************************
 *  QGRAPH - A Query system for graphs                                    *
 *                                                                        *
 *  Copyright 2018: Shayne Flint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          * 
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  QGRAPH implements a Query system enabling one to search a set of      *
 *  objects and return results if these objects match the queries. It has *
 *  been designed for graphs but some queries are more general and can    *
 *  apply to any kind of object.                                          * 
 **************************************************************************                                       
 *  This file is part of QGRAPH (A Query system for graphs).              *
 *                                                                        *
 *  QGRAPH is free software: you can redistribute it and/or modify        *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  QGRAPH is distributed in the hope that it will be useful,             *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with QGRAPH. If not, see <https://www.gnu.org/licenses/gpl.html>*
 *                                                                        *
 **************************************************************************/
package au.edu.anu.rscs.aot.old.queries.base;

import static au.edu.anu.rscs.aot.old.queries.CoreQueries.*;

import java.util.Collection;
import java.util.Iterator;
import au.edu.anu.rscs.aot.collections.FilteredList;
import au.edu.anu.rscs.aot.old.queries.Query;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * 
 * @author Shayne Flint - 28/3/2012
 *
 */
// Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
@Deprecated
public class SelectQuery extends Query {

	private Query theQuery = null;
	private boolean exclusive = false;
	private IntegerRange multiplicity = new IntegerRange(0, Integer.MAX_VALUE);
	private boolean returnMany = true;
	//private boolean searchSubArchetypes = false;

	// with no modifiers (see below), a SelectQuery returns the input list as is
	//
	public SelectQuery() {
	}

	// filters the input list according to the specified query
	//
	public SelectQuery query(Query q) {
		theQuery = q;
		return this;
	}

	// Modifiers
	//

	// tells the selectQuery to return a single element
	//
	public SelectQuery returnOne() {
		returnMany = false;
		return this;
	}

	// tells the selectQuery to return a list of elements
	//
	public SelectQuery returnMany() {
		returnMany = true;
		return this;
	}

	// ensures that the result has at least min elements
	//
	public SelectQuery min(int m) {
		multiplicity.setFirst(m);
		return this;
	}

	// ensures that the result has at most max elements
	//
	public SelectQuery max(int m) {
		multiplicity.setLast(m);
		return this;
	}

	// ensures that the result has a number elements matching the specified
	// multiplicity
	//
	public SelectQuery multiplicity(Multiplicity mult) {
		switch (mult) {
		case ZERO_ONE:
			min(0);
			max(1);
			break;
		case ZERO_MANY:
			min(0);
			max(Integer.MAX_VALUE);
			break;
		case ONE:
			min(1);
			max(1);
			break;
		case ONE_MANY:
			min(1);
			max(Integer.MAX_VALUE);
			break;
		default:
			break;
		}
		return this;
	}

	public SelectQuery multiplicity(IntegerRange range) {
		min(range.getFirst());
		max(range.getLast());
		return this;
	}


	// ensures that there are no other items in the input list which don't satisfy
	// the query
	//
	public SelectQuery exclusive() {
		this.exclusive = true;
		return this;
	}

	public SelectQuery nonExclusive() { // Default
		this.exclusive = false;
		return this;
	}

	// Helpers
	//


	/*
	 * A suggestion: But I guess these can't work with the specification archetype
	 We should change that because UML allows any +ve range
	 */
	
/*	public static SelectQuery selectBetween(int from, int to) {
		if (from == 1 && to == 1)
			return selectOne();
		if (from == 0 && to == 1)
			return selectZeroOrOne();
		return new SelectQuery().min(from).max(to).returnMany();
	}

	public static SelectQuery selectBetween(int from, int to, Query q) {
		return selectBetween(from, to).query(q);
	}
*/
	public static SelectQuery select(Multiplicity multiplicity) {
		return new SelectQuery().returnMany().multiplicity(multiplicity);
	}

	public static SelectQuery select(Multiplicity multiplicity, Query q) {
		return select(multiplicity).query(q);
	}

	public static SelectQuery select(IntegerRange multiplicity) {
		return new SelectQuery().returnMany().multiplicity(multiplicity);
	}

	public static SelectQuery select(IntegerRange multiplicity, Query q) {
		return select(multiplicity).query(q);
	}

	public static SelectQuery selectZeroOrOne() {
		return new SelectQuery().returnOne().multiplicity(Multiplicity.ZERO_ONE);
	}

	public static SelectQuery selectZeroOrOne(Query q) {
		return selectZeroOrOne().query(q);
	}

	public static SelectQuery selectZeroOrMany() {
		return new SelectQuery().returnMany().multiplicity(Multiplicity.ZERO_MANY);
	}

	public static SelectQuery selectZeroOrMany(Query q) {
		return selectZeroOrMany().query(q);
	}

	public static SelectQuery selectAny() {
		return new SelectQuery().returnOne().multiplicity(Multiplicity.ZERO_MANY);
	}

	public static SelectQuery selectAny(Query q) {
		return selectAny().query(q);
	}

	public static SelectQuery selectOne() {
		return new SelectQuery().returnOne().multiplicity(Multiplicity.ONE);
	}

	public static SelectQuery selectOne(Query q) {
		return selectOne().query(q);
	}

	public static SelectQuery selectOneOrMany() {
		return new SelectQuery().returnMany().multiplicity(Multiplicity.ONE_MANY);
	}

	public static SelectQuery selectOneOrMany(Query q) {
		return selectOneOrMany().query(q);
	}

	// Processing
	//

	@SuppressWarnings("unchecked")
	@Override
	public SelectQuery process(Object item) {
		defaultProcess(item);

		Collection<Object> localItem;
//		List<Object> localItem;
		// DynamicList<Object> localItem;

		// Log.debug("Constraint: " + constraint);

		Collection<Object> list = null;
//		List<Object> list = null;
		// DynamicList<Object> list = null;

		// if (item instanceof DynamicList)
		// list = (DynamicList<Object>)item;
		if (item instanceof Collection)
			list = (Collection<Object>) item;
//		if (item instanceof List)
//			list = (List<Object>) item;
		// else if (item instanceof DynamicList) {
		// DynamicList<Object> DynamicList = (DynamicList<Object>)item;
		// list = ((DynamicList) item);
		// }
		else
			failed("Expected Collection but found " + item.getClass().getSimpleName() + ".");

//		if (searchSubArchetypes && isAotNodeList(list))
//			searchSubArchetypes(list);

		int originalSize = list.size();

		if (theQuery == null)
			localItem = list;
		else
			localItem = new FilteredList<Object>(list, theQuery);

		int count = localItem.size();

		if (theQuery != null && exclusive)
			if (count != originalSize)
				failed("Expected only items that match " + theQuery + " but found "
						+ (originalSize - count) + " unexpected items.");
//				failed("SelectQuery did not expect items which do not match " + theQuery + " (found "
//						+ (originalSize - count) + " unexpected items)");

		if (!multiplicity.inRange(count))
			failed("Expected " + multiplicity + " item(s) matching " + theQuery + " but found " + count
					+ ".");

		if (returnMany) {
			result = localItem;
			satisfied = true;
			return this;
		} else {
			Iterator<Object> localItemIterator = localItem.iterator();
			if (localItemIterator.hasNext()) {
				result = localItemIterator.next();
				satisfied = true;
				return this;
			} else
				result = null;
		}

		satisfied = false;
		return this;
	}

//	@SuppressWarnings("unchecked")
//	private static void searchSubArchetypes(List<Object> list) {
//		Object obj = (Object) list;
//		List<AotNode> nodeList = (List<AotNode>) obj;
//		for (AotNode n : nodeList) {
//			List<AotNode> constraints = (List<AotNode>) get(n.getEdges(Direction.OUT), edgeListEndNodes(),
//					selectZeroOrMany(hasProperty(Archetype.Key_ClassName, Archetype.Value_CheckSubArchetype)));
//			for (AotNode constraint : constraints)
//				recurse(constraint, nodeList);
//		}
//	}

//	@SuppressWarnings("unchecked")
//	private static void recurse(AotNode n, List<AotNode> list) {
//		ObjectTable<Object> parameters = (ObjectTable<Object>) n.getPropertyValue(Archetype.Key_Parameters);
//		String aname = (String) parameters.getWithFlatIndex(2);
//
//		AotGraph g = openGraph(aname);
//		//It would be very handy to add the class to this graph.
//		String aclass = (String) parameters.getWithFlatIndex(1);
//		DynamicList<AotNode> hasNodes = (DynamicList<AotNode>) get(g.getRoot().getEdges(Direction.OUT), edgeListEndNodes(),
//				selectZeroOrMany(hasTheLabel(Archetype.Label_HasNode)));
//
//		list.addAll(hasNodes);
//
//		for (AotNode hasNode : hasNodes) {
//			List<AotNode> constraints = (List<AotNode>) get(hasNode.getEdges(Direction.OUT), edgeListEndNodes(),
//					selectZeroOrMany(hasProperty(Archetype.Key_ClassName, Archetype.Value_CheckSubArchetype)));
//			for (AotNode constraint : constraints)
//				recurse(constraint, list);
//		}
//
//	}
//
//	private static AotGraph openGraph(String aname) {
//		AotGraph archetype = new AotGraph();
//		AotReader archetypeReader = Resource.getReader(aname,
//				"fr.ens.biologie.threeWorlds.ui.configuration.archetype3w");
//		Tokenizer tokenizer = new Tokenizer(archetypeReader);
//		UniversalParser parser = new UniversalParser(tokenizer, archetype);
//		archetype.prepare();
//		return archetype;
//	}
//
//	private static boolean isAotNodeList(List<Object> list) {
//		if (list.isEmpty())
//			return true;// nothing else you can do
//		return (list.get(0) instanceof AotNode);
//	}

	public String toString() {
		String resultStr = "[Select";

		if (exclusive)
			resultStr = resultStr + " only";

		resultStr = resultStr + " " + multiplicity + " items";

		if (returnMany)
			resultStr = resultStr + " (return as list)";
		else
			resultStr = resultStr + " (return as item)";

		resultStr = resultStr + " which match " + theQuery + ", result=" + result + ", satisfied=" + satisfied + "]";
		return resultStr;
	}

	// TESTING
	//

	public static void main(String[] args) {
//		Logger log = LoggerFactory.getLogger(SelectQuery.class, "test main");
		DynamicList<String> twi = new DynamicList<String>("test", "bill", "john", "tim", "xxx");
		// List<String> twi = new DynamicList<String>(l);
		// Log.log(l.toString());
//		log.trace(twi.toString());

		try {
			SelectQuery c1 = selectZeroOrOne(startsWith("b"));
			System.out.println("query: " + c1);
			c1.check(twi);
			System.out.println("  result: " + c1);
			SelectQuery c2 = selectZeroOrOne(startsWith("t"));
			System.out.println("query: " + c2);
			c2.check(twi);
			System.out.println("  (bad) result: " + c2);
		} catch (Exception e) {
			System.out.println("  error : " + e);
		}

		try {
			SelectQuery c1 = selectZeroOrMany(startsWith("b"));
			System.out.println("query: " + c1);
			c1.check(twi);
			System.out.println("  result: " + c1);
			SelectQuery c2 = selectZeroOrMany(startsWith("Z"));
			System.out.println("query: " + c2);
			c2.check(twi);
			System.out.println("  result: " + c2);
		} catch (Exception e) {
			System.out.println("  error : " + e);
		}

		try {
			SelectQuery c1 = selectOne(startsWith("x"));
			System.out.println("query: " + c1);
			c1.check(twi);
			System.out.println("  result: " + c1);
			SelectQuery c2 = selectOne(startsWith("t"));
			System.out.println("query: " + c2);
			c2.check(twi);
			System.out.println("  (bad) result: " + c1);
		} catch (Exception e) {
			System.out.println("  error : " + e);
		}

		try {
			SelectQuery c1 = selectOneOrMany(startsWith("b"));
			System.out.println("query: " + c1);
			c1.check(twi);
			System.out.println("  result: " + c1);
			SelectQuery c2 = selectOneOrMany(startsWith("Z"));
			System.out.println("query: " + c2);
			c2.check(twi);
			System.out.println("  (bad) result: " + c1);
		} catch (Exception e) {
			System.out.println("  error : " + e);
		}

		// Test case to demonstrate exclusive() method. This test uses a SequenceQuery,
		// which tests if the length of a string is 4,
		// to select strings from a list. In the first test list all elements have
		// length = 4 and therefore passes the query. The
		// second list does not - it fails saying there are two elements which do not
		// pass
		//

		SelectQuery eq = selectZeroOrMany(new SequenceQuery(length(), hasCount(4))).exclusive();
		System.out.println("query: " + eq);

		DynamicList<String> strList1 = new DynamicList<String>("test", "bill", "john");
		eq.check(strList1);
		System.out.println("  result: " + eq);

		DynamicList<String> strList2 = new DynamicList<String>("test", "bill", "john", "tim", "xxx");
		// eq.check("hhh");
		eq.check(strList2);
		System.out.println("  result: " + eq);

	}

}
