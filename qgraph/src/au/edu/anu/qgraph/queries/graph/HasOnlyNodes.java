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
package au.edu.anu.qgraph.queries.graph;

import fr.cnrs.iees.omugi.graph.*;
import au.edu.anu.qgraph.queries.*;

/**
 * Query to test that all nodes of a graph satisfy the given {@code query}.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class HasOnlyNodes extends QueryAdaptor {

	private Queryable nodeQuery;

	/**
	 * Constructor with particular query.
	 * 
	 * @param nodeQuery The query to apply to all nodes of a graph.
	 */
	public HasOnlyNodes(Queryable nodeQuery) {
		this.nodeQuery = nodeQuery;
	}

	/**
	 * Factory method to instance a {@code hasOnlyNodes()} query.
	 * 
	 * @param nodeQuery The query to apply to all nodes of a graph.
	 * @return Instance of {@code HasOnlyNodes} query.
	 */
	public static HasOnlyNodes hasOnlyNodes(Queryable nodeQuery) {
		return new HasOnlyNodes(nodeQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Graph<Node, Edge> localItem = (Graph<Node, Edge>) input;
		for (Node n : localItem.nodes()) {
			if (!nodeQuery.submit(n).satisfied()) {
				errorMsg = nodeQuery.errorMsg();
				return this;
			}
		}
		return this;
	}

}
