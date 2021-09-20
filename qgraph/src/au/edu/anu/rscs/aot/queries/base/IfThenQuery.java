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
package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Test query. If this Query is satisfied, applies a query (called <em>trueQuery</em>), 
 * and if not, applies another one (called <em>falseQuery</em>)
 * to the {@code submit()} argument.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class compatible with the constructor query arguments;</dd>
 * <dt>Type of result</dt>
 * <dd>the result of <em>trueQuery</em> or <em>falseQuery</em>, depending on the outcome of the test query</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IfThenQuery extends QueryAdaptor {

	private Queryable testQuery;
	private Queryable trueQuery;
	private Queryable falseQuery;

	public IfThenQuery(Queryable testQuery, Queryable trueQuery, Queryable falseQuery) {
		this.testQuery = testQuery;
		this.trueQuery = trueQuery;
		this.falseQuery = falseQuery;
	}

	public IfThenQuery(Queryable testQuery, Queryable trueQuery) {
		this.testQuery = testQuery;
		this.trueQuery = trueQuery;
		this.falseQuery = null;
	}

	/**
	 * Getter for the test query determining the outcome
	 * @return the test query
	 */
	public Queryable getTestQuery() {
		return testQuery;
	}

	/**
	 * Getter for the query applied when the test query is satisfied
	 * @return the <em>trueQuery</em>
	 */
	public Queryable getTrueQuery() {
		return trueQuery;
	}

	/**
	 * Getter for the query applied when the test query is not satisfied
	 * @return the <em>falseQuery</em>
	 */
	public Queryable getFalseQuery() {
		return falseQuery;
	}

	public String toString() {
		String result = "[if " + testQuery + " then " + trueQuery;
		if (falseQuery != null)
			result = result + " else " + falseQuery;
		result = result + "]";
		return result;
	}

//	@Override
//	public Query process(Object item) {
//		defaultProcess(item);
//		testQuery.process(item);
//		if (testQuery.satisfied()) {
//			trueQuery.process(item);
//			result = trueQuery.getResult();
//			satisfied = trueQuery.satisfied();
//		} else {
//			falseQuery.process(item);
//			result = falseQuery.getResult();
//			satisfied = falseQuery.satisfied();
//		}
//		return this;
//	}

	// TODO Untested 
	@Override
	public Queryable submit(Object input) {
		initInput(input);	
		if (testQuery.submit(input).satisfied()) {
			if (trueQuery.submit(input).satisfied()) 
				result = trueQuery.result();
			else
				errorMsg = trueQuery.errorMsg();
		} else if (falseQuery!=null){
			if (falseQuery.submit(input).satisfied()) 
				result = falseQuery.result();
			else
				errorMsg = falseQuery.errorMsg();
		}
		return this;
	}

}
