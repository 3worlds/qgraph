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
package au.edu.anu.rscs.aot.old.queries.graph.node;

import fr.cnrs.iees.graph.TreeNode;

import static au.edu.anu.rscs.aot.old.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.old.queries.base.SequenceQuery.get;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
@Deprecated
public class HasParent extends Query {

	private Query query;

	public HasParent(Query query) {
		this.query = query;
	}

	public static HasParent hasParent(Query query) {
		return new HasParent(query);
	}

	public static HasParent hasParent() {
		return new HasParent(null);
	}



	@Override
	public String toString() {
		String result = "[HasParent";
		if (query != null)
			result = result + " matching " + query;
		return result + "]";
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		TreeNode localItem = (TreeNode)item;

		if (query == null) {
			satisfied = (localItem.getParent() != null);
			return this;
		} else {
			TreeNode parent = (TreeNode) get(localItem, parent(query));
//			AotNode parent = localItem.getParent(query);
			satisfied = (parent != null);
			return this;
		}

	}


}
