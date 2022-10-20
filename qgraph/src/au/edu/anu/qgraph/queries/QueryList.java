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
package au.edu.anu.qgraph.queries;

import au.edu.anu.omhtk.collections.DynamicList;

/**
 * <p>An ancestor for compound queries grouping multiple queries.</p>
 * 
 * @author Shayne Flint - 26/3/2012 <br/>
 * refactored by Ian Davies - 23 Feb. 2021
 * 
 */
public abstract class QueryList extends QueryAdaptor {

	private DynamicList<Queryable> queryList = new DynamicList<Queryable>();

	/**
	 * 
	 * @param queries the queries grouped in this instance
	 */
	public QueryList(Queryable... queries) {
		for (Queryable q : queries)
			addQuery(q);
	}

	protected QueryList addQuery(Queryable... queries) {
		for (Queryable q : queries)
			queryList.add(q);
		return this;
	}

	protected DynamicList<Queryable> queryList() {
		return queryList;
	}

	@Override
	public String toString() {
		StringBuilder description = new StringBuilder().append("[");
		if (queryList.isEmpty())
			description.append("]");
		else
			for (Queryable q : queryList()) {
				description.append(q.toString());
			}
		return description.append("]").toString();
	}

}
