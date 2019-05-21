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

import au.edu.anu.rscs.aot.collections.FilteredList;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.get;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class HasEdges extends Query {

	private Direction edgeDirection;
	private Query nodeQuery;
	private Query edgeQuery = null;
	private Multiplicity multiplicity;
	private String label = null;

	public HasEdges(Direction edgeDirection, Query nodeQuery, Query edgeQuery, Multiplicity multiplicity, String label) {
		this.edgeDirection = edgeDirection;
		this.nodeQuery = nodeQuery;
		this.edgeQuery = edgeQuery;
		this.multiplicity = multiplicity;
		this.label = label;
	}


	public static HasEdges hasEdges(Direction edgeDirection, Query nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(edgeDirection, nodeQuery, null, multiplicity, null);
	}

	public static HasEdges hasInEdges(Query nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.IN, nodeQuery, null, multiplicity, null);
	}

	public static HasEdges hasOutEdges(Query nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.OUT, nodeQuery, null, multiplicity, null);
	}



	public HasEdges withEdgeQuery(Query edgeQuery) {
		this.edgeQuery = edgeQuery;
		return this;
	}

	public HasEdges withLabel(String label) {
		this.label = label;
		return this;
	}

	@Override
	public String toString() {
		String result = "[" + stateString() + "node must have " + multiplicity + " edges";
		if (edgeQuery != null)
			result = result + " matching " + edgeQuery;
		if (label != null)
			result = result + " with Label '" + label + "'";
		result = result + " to nodes matching " + nodeQuery;
		return result + "]";
	}

	@Override
	public String userString() {
		String result = "[" + stateString() + "must have " + multiplicity + " edges";
		if (edgeQuery != null)
			result = result + " matching " + edgeQuery.userString();
		if (label != null)
			result = result + " with Label '" + label + "'";
		result = result + " to nodes matching " + nodeQuery.userString();
		return result + "]";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem = (Node)item;

		DynamicList<Edge> edges = null;
		switch (edgeDirection) {
		case IN:
			// caution: not tested yet
			edges = (DynamicList<Edge>) get(localItem.edges(Direction.IN),
				selectZeroOrMany(hasStartNode(nodeQuery)));
//			edges = localItem.getInEdges(hasStartNode(nodeQuery));
			break;
		case OUT:
			edges = (DynamicList<Edge>) get(localItem.edges(Direction.OUT),
				selectZeroOrMany(hasEndNode(nodeQuery)));
//			edges = localItem.getOutEdges(hasEndNode(nodeQuery));
			break;
//		case IN_OUT:
//			edges = localItem.getEdges(hasOtherNode(nodeQuery));
//			break;
		}

		if (edgeQuery != null)
			edges = new FilteredList<Edge>(edges, edgeQuery);

		if (label != null)
			edges = new FilteredList<Edge>(edges, hasTheLabel(label));

		int edgesSize = edges.size();
		
		switch (multiplicity) {
		case ZERO:
			satisfied = (edgesSize == 0);
			break;
		case ONE:
			satisfied = (edgesSize == 1);
			break;
		case ONE_MANY:
			satisfied = (edgesSize >= 1);
			break;
		case ZERO_ONE:
			satisfied = (edgesSize == 0 || edgesSize == 1);
			break;
		case ZERO_MANY:
			satisfied = (edgesSize >= 0);
			break;
		}

		return this;
	}


//	public ReferenceableNode toGraph() {
//		ReferenceableNode result = new ReferenceableNodeImpl();
//		result.setLabel(userString());
//		if (edgeQuery != null) {
//			ReferenceableEdge e = new ReferenceableEdgeImpl(result, edgeQuery.toGraph());
//			result.addEdge(e, Direction.OUT);
//			e.endNode().addEdge(e, Direction.IN);
//			e.setLabel("edgeQuery");
////			result.newEdge(edgeQuery.toGraph(), "edgeQuery");
//		}
//		ReferenceableEdge e = new ReferenceableEdgeImpl(result,nodeQuery.toGraph());
//		result.addEdge(e, Direction.OUT);
//		e.endNode().addEdge(e, Direction.IN);
//		e.setLabel("nodeQuery");
////		result.newEdge(nodeQuery.toGraph(), "nodeQuery");
//		return result;
//	}

}
