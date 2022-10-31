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
package au.edu.anu.qgraph.queries.base;

import java.util.Stack;

import au.edu.anu.qgraph.queries.QueryList;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>
 * Chains a sequence of queries, i.e. the {@code result()} of each query serves
 * as an input into {@code submit(...)} of the next query.
 * </p>
 *
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>type of the result of the last query in the sequence (if everything went
 * ok - {@code null} otherwise)</dd>
 * <dt>Fails if</dt>
 * <dd>any of the queries of the sequence fails</dd>
 * </dl>
 *
 * @author Shayne Flint - 28/3/2012
 * @author Ian Davies - 23 Feb. 2021
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries CoreQueries
 * 
 */
public class SequenceQuery extends QueryList {

	/**
	 * Construct a query comprising a list of queries to submit in sequential order.
	 * <p>
	 * Reset and Pop queries are special cases. They are used to clear and pop queries in the query stacknever submitted 
	 * @param queries Queries to process
	 */
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
		if (q instanceof PopQuery) {
			int popCount = ((PopQuery) q).getCount();
			for (int i = 0; i < popCount; i++)
				stack.pop();
			return q.satisfied();
		} else if (q instanceof ResetQuery) {
			stack.clear();
			stack.push(input);
			return q.satisfied();
		} else {
			q.submit(stack.peek());
			stack.push(q.result());
			errorMsg = q.errorMsg();
			return q.satisfied();
		}
	}

	/**
	 * Instead of get(...) return the sq and then interrogate: if errorMsg()!=null
	 * use the result. So I've change get(...) to apply(...)?
	 */
	/**
	 * <p>
	 * Applies a sequence of queries to an object and return the query list.
	 * </p>
	 * 
	 * @param input   the object on which to apply queries
	 * @param queries the sequence a queries to apply to the object
	 * @return the sequence of queries
	 */
	public static SequenceQuery apply(Object input, Queryable... queries) {
		SequenceQuery sq = new SequenceQuery(queries);
		sq.submit(input);
		return sq;
	}

	/**
	 * <p>
	 * Get the result of a sequence of queries applied on an object.
	 * </p>
	 * <p>
	 * This method is specially designed to work in interaction with
	 * {@link au.edu.anu.qgraph.queries.CoreQueries CoreQueries} static methods to query particular elements of a
	 * {@link fr.cnrs.iees.omugi.graph.Graph Graph}. Examples:
	 * </p>
	 * <p>
	 * return all the OUT edges of a node labelled 'blue':
	 * </p>
	 * 
	 * <pre>
	 * get(node, edges(Direction.OUT), edgeListEndNodes(), selectZeroOrMany(hasTheLabel("blue")));
	 * </pre>
	 * <p>
	 * return all the children of a (tree)node that have property color='red'
	 * </p>
	 * 
	 * <pre>
	 * get(node, children(), selectZeroOrMany(hasProperty("color", "red")));
	 * </pre>
	 * 
	 * @param input   the object on which to apply queries
	 * @param queries the sequence a queries to apply to the object
	 * @return the result of the last query
	 */
	public static Object get(Object input, Queryable... queries) {
		SequenceQuery sq = apply(input, queries);
		return sq.result();
	}

}
