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
package au.edu.anu.rscs.aot.old.queries.graph;

import fr.cnrs.iees.graph.Node;
import au.edu.anu.rscs.aot.old.queries.Query;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Graph;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
@Deprecated
public class HasOnlyNodes extends Query {

	private Query nodeQuery;

	public HasOnlyNodes(Query nodeQuery) {
		this.nodeQuery = nodeQuery;
	}


	public static HasOnlyNodes hasOnlyNodes(Query nodeQuery) {
		return new HasOnlyNodes(nodeQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Graph<Node,Edge> localItem = (Graph<Node,Edge>)item;
		for (Node n : localItem.nodes()) {
			nodeQuery.process(n);
			if (!nodeQuery.satisfied()) {
				satisfied = false;
				return this;
			}
		}
		satisfied = true;
		return this;
	}

	
	@Override
	public String toString() {
		return "[All nodes must satisfy " + nodeQuery + "]";
	}
	

	// TESTING
	//

//	public static void main(String[] args) {
//		TestGraph tg = new TestGraph();
//		NodeList  nl = tg.getNodeList();
//		HasOnlyNodes q = hasOnlyNodes(IsClass.isClass(TestNode.class, TestNode2.class));
//		//		HasNodes q = hasNodes(IsClass.isClass(TestNode.class));
//		q.check(nl);
//	}

}
