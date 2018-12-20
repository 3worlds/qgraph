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
package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
public class NodeListEdges extends Query {

	private Direction direction;

	public NodeListEdges(Direction direction) {
		this.direction = direction;
	}

	public static Query inEdges() {
		return new NodeListEdges(Direction.IN);
	}

	public static Query outEdges() {
		return new NodeListEdges(Direction.OUT);
	}

//	public static Query edges() {
//		return new NodeListEdges(EdgeDirection.IN_OUT);
//	}

	public static Query edges(Direction dir) {
		return new NodeListEdges(dir);
	}


	@Override
	public String toString() {
		return "[" + direction + " edges from list of nodes]";
	}
	


	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Iterable<Node> localItem = (Iterable<Node>)item;
//		NodeList localItem = (NodeList)item;
		DynamicList<Edge> tempResult = new DynamicList<Edge>();
		for (Node n : localItem)
			tempResult.addAllUnique((Iterable<Edge>) n.getEdges(direction));
		satisfied = true;
		result = new DynamicList<Edge>(tempResult);
		return this;
	}

}
