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
 * A query to remove any number of queries from the query stack.
 * <p>
 * This query can be used based on some conditional such as {@link IfThenQuery}.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class PopQuery extends QueryAdaptor {

	private int count;

	private PopQuery(int count) {
		this.count = count;
	}

	/**
	 * Static factory for the pop query.
	 * 
	 * @param count Number of queries to pop from the stack.
	 * @return Instance of a popQuery
	 */
	public static Queryable pop(int count) {
		return new PopQuery(count);
	}

	/**
	 * Static factory to create a popQuery to pop one query.
	 * 
	 * @return PopQuery with count = 1.
	 */
	public static Queryable pop() {
		return new PopQuery(1);
	}

	/**
	 * @return the number of queries to pop from the stack.
	 */
	public int getCount() {
		return count;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		throw new UnsupportedOperationException("Submitting " + this.getClass().getSimpleName() + " is not supported.");
	}

}
