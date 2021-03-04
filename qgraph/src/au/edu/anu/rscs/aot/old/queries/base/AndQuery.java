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
import au.edu.anu.rscs.aot.old.queries.QueryList;

/**
 * 
 * @author Shayne Flint - 28/3/2012
 *
 */
@Deprecated
public class AndQuery extends QueryList {

	public AndQuery(Query...query) {
		super(query);
	}

	public static AndQuery andQuery(Query...query) {
		return new AndQuery(query);
	}

	// Processes
	//

	@Override
	public AndQuery process(Object item) {
		defaultProcess(item);
		for (Query q : queryList()) {
			q.process(item);
			satisfied = q.satisfied();
			if (!satisfied()) {
				return this;
			}
		}
		satisfied = true;
		return this;
	}

	
	// Helpers
	//
	
	public static Object getResult(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		return queryList.getResult();
	}
	
	public static boolean satisfied(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		return queryList.satisfied();
	}
	
	public static void check(Object input, Query... queries) {
		AndQuery queryList = new AndQuery(queries);
		queryList.process(input);
		if (!queryList.satisfied())
			queryList.failed(input);
	}


	public String toString() {
		return "[AndQuery " + super.toString() + "]";
	}
	

}
