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

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>Test query. If this Query (called <em>testQuery</em>) is satisfied, applies a 
 * query (called <em>trueQuery</em>), and if not, applies another one (called <em>falseQuery</em>)
 * to the {@code submit()} argument.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class compatible with the constructor query arguments;</dd>
 * <dt>Type of result</dt>
 * <dd>the result of <em>trueQuery</em> or <em>falseQuery</em>, depending on the outcome of the test query</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li><em>testQuery</em> is satisfied and <em>trueQuery</em> is not</li>
 * <li><em>testQuery</em> is not satisfied and <em>falseQuery</em> is not</li>
 * </ol></dd> 
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 * @see au.edu.anu.qgraph.queries.CoreQueries#ifThenQuery(Queryable, Queryable) CoreQueries.ifThenQuery(Queryable,Queryable)
 * @see au.edu.anu.qgraph.queries.CoreQueries#ifThenQuery(Queryable, Queryable, Queryable) CoreQueries.ifThenQuery(Queryable,Queryable,Queryable)
 */
public class IfThenQuery extends QueryAdaptor {

	private Queryable testQuery;
	private Queryable trueQuery;
	private Queryable falseQuery;

	/**
	 * @param testQuery Test query: if satisfied, test the <em>trueQuery</em> else test the <em>falseQuery</em>.
	 * @param trueQuery The query to test if <em>testQuery</em> is satisfied.
	 * @param falseQuery The query to test if <em>testQuery</em> is not satisfied.
	 */
	public IfThenQuery(Queryable testQuery, Queryable trueQuery, Queryable falseQuery) {
		this.testQuery = testQuery;
		this.trueQuery = trueQuery;
		this.falseQuery = falseQuery;
	}

	/**
	 * @param testQuery Test query: if satisfied, test the <em>trueQuery</em>.
	 * @param trueQuery The query to test if testQuery is satisfied.
	 */
	public IfThenQuery(Queryable testQuery, Queryable trueQuery) {
		this.testQuery = testQuery;
		this.trueQuery = trueQuery;
		this.falseQuery = null;
	}

	/**
	 * Test query determining the outcome
	 * @return the test query
	 */
	public Queryable getTestQuery() {
		return testQuery;
	}

	/**
	 * The query applied when the test query is satisfied
	 * @return the <em>trueQuery</em>
	 */
	public Queryable getTrueQuery() {
		return trueQuery;
	}

	/**
	 * The query applied when the test query is not satisfied
	 * @return the falseQuery</em>
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
