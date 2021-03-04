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

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class ForAllQuery extends Query {
		
	private Query query;
	
	public ForAllQuery(Query query) {
		this.query = query;
	}

	public static Query forAll(Query query) {
		return new ForAllQuery(query);
	}

	public Query getQuery() {
		return query;
	}
	

	@SuppressWarnings("unchecked")
	@Override
	public boolean satisfied(Object item) {
		Iterable<Object> localItem = (Iterable<Object>)item;
		for (Object obj : localItem)
			if (!query.satisfied(obj))
				return false;
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ForAllQuery process(Object item) {
		defaultProcess(item);
		Iterable<Object> localItem = (Iterable<Object>)item;
		for (Object obj : localItem) {
			query.process(obj);
			satisfied = query.satisfied();
			if (!satisfied()) {
				return this;
			}
		}
		satisfied = true;
		return this;
	}
	
}
