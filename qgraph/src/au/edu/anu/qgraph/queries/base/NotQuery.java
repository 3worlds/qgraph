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

import au.edu.anu.qgraph.queries.QueryList;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>Query testing if all its sub-queries are not satisfied. Will stop checking at the first 
 * sub-query success.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><em>any</em> of the queries passed to the constructor is satisfied when applied to input</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#notQuery(Queryable...) CoreQueries.notQuery(...)
 *
 */
public class NotQuery extends QueryList {
	
	public NotQuery(Queryable... queries) {
		super(queries);
	}

	/**
	 * Argument can be of any class.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		for (Queryable q : queryList()) {
			q.submit(input);
			if (q.satisfied()) {
				// ahh - this will produce a mess
				errorMsg = "Expected " + q + " to be FALSE but found TRUE";
				return this;
			}
		}
		return this;
	}

	@Override
	public String toString() {
		return "[" + this.getClass().getSimpleName() + " " + super.toString() + "]";
	}
}
