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

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IfThenQuery extends Query {

	private Query testQuery;
	private Query trueQuery;
	private Query falseQuery;
	
	public IfThenQuery(Query testQuery, Query trueQuery, Query falseQuery) {
		this.testQuery  = testQuery;
		this.trueQuery  = trueQuery;
		this.falseQuery = falseQuery;
	}

	public IfThenQuery(Query testQuery, Query trueQuery) {
		this.testQuery  = testQuery;
		this.trueQuery  = trueQuery;
		this.falseQuery = null;
	}

	public Query getTestQuery() {
		return testQuery;
	}
	
	public Query getTrueQuery() {
		return trueQuery;
	}
	
	public Query getFalseQuery() {
		return falseQuery;
	}
	
	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery, Query falseQuery) {
		return new IfThenQuery(testQuery, trueQuery, falseQuery);
	}
	
	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery) {
		return new IfThenQuery(testQuery, trueQuery);
	}
	

	public String toString() {
		String result = "[if " + testQuery + " then " + trueQuery;
		if (falseQuery != null)
			result = result + " else " + falseQuery;
		result = result + "]";
		return result;
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		testQuery.process(item);
		if (testQuery.satisfied()) {
			trueQuery.process(item);
			result = trueQuery.getResult();
			satisfied = trueQuery.satisfied();
		} else {
			falseQuery.process(item);
			result = falseQuery.getResult();
			satisfied = falseQuery.satisfied();
		}
		return this;
	}
	
}
