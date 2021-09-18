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
package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>
 * A {@link Query} to check that one or both {@link Node}s of an {@link Edge}
 * satisfy some Query.
 * </p>
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>Edge</dd>
 * <dt>Type of result</dt>
 * <dd>no result returned</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
// Tested OK with version 0.1.1 on 21/5/2019

public class EdgeHasNode extends QueryAdaptor {

	private EdgeNodeSelection edgeNodeSelection;
	private Queryable nodeQuery;
	private Node refNode = null;

	public EdgeHasNode(EdgeNodeSelection edgeNodeSelection, Queryable nodeQuery) {
		this.edgeNodeSelection = edgeNodeSelection;
		this.nodeQuery = nodeQuery;
	}

	public EdgeHasNode(EdgeNodeSelection edgeNodeSelection, Queryable nodeQuery, Node refNode) {
		this(edgeNodeSelection, nodeQuery);
		this.refNode = refNode;
	}

	/**
	 * Checks that the end Node of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
//	public static EdgeHasNode hasEndNode(Queryable nodeQuery) {
//		return new EdgeHasNode(EdgeNodeSelection.END, nodeQuery);
//	}

	/**
	 * Checks that the start Node of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
//	public static EdgeHasNode hasStartNode(Queryable nodeQuery) {
//		return new EdgeHasNode(EdgeNodeSelection.START, nodeQuery);
//	}

	/**
	 * Checks that the other Node of the Edge satisfies a Query
	 * 
	 * @param nodeQuery the Query to satisfy
	 * @param refNode   the node to use as the start (the other node is checked)
	 * @return the resulting EdgeHasNode query
	 */
//	public static EdgeHasNode hasOtherNode(Queryable nodeQuery, Node refNode) {
//		return new EdgeHasNode(EdgeNodeSelection.OTHER, nodeQuery, refNode);
//	}

	/**
	 * Checks that both Nodes of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
//	public static EdgeHasNode hasBothNodes(Queryable nodeQuery) {
//		return new EdgeHasNode(EdgeNodeSelection.BOTH, nodeQuery);
//	}

//	@Override
//	public Query process(Object item) {
//		defaultProcess(item);
//		Edge localItem = (Edge)item;
//		switch (edgeNodeSelection) {
//		case START:
//			satisfied = nodeQuery.satisfied(localItem.startNode());
//			break;
//		case END:
//			satisfied = nodeQuery.satisfied(localItem.endNode());
//			break;
//		case OTHER:
//			satisfied = nodeQuery.satisfied(localItem.otherNode(refNode));
//			break;
//		case BOTH:
//			satisfied = (nodeQuery.satisfied(localItem.startNode()) && nodeQuery.satisfied(localItem.endNode()));
//			break;
//		}
//		return this;
//	}

//	@Override
//	public String toString() {
//		return "[" + stateString() + "must have " + edgeNodeSelection + " node(s) matching " + nodeQuery + "]";
//	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Edge localItem = (Edge) input;
//		boolean ok = true;
		switch (edgeNodeSelection) {
		case START:
			if (!nodeQuery.submit(localItem.startNode()).satisfied())
				errorMsg = "Expected start node '" + localItem.startNode().toShortString() + "' of edge '"
						+ localItem.toShortString() + "' to satisfy query but found [" +nodeQuery.errorMsg()+"]. ";
			break;
		case END:
			if (!nodeQuery.submit(localItem.endNode()).satisfied())
				errorMsg = "Expected end node '" + localItem.endNode().toShortString() + "' of edge '"
						+ localItem.toShortString() + "' satisfy query but found [" +nodeQuery.errorMsg()+"]. ";

			break;
		case OTHER:
			if (!nodeQuery.submit(localItem.otherNode(refNode)).satisfied())
				errorMsg = "Expected node '" + refNode.toShortString() + "' of edge '"
						+ localItem.toShortString() + "' satisfy query but found [" +nodeQuery.errorMsg()+"]. ";

			break;
		case BOTH:
			String smsg = nodeQuery.submit(localItem.startNode()).errorMsg();
			String emsg = nodeQuery.submit(localItem.endNode()).errorMsg();
			if (smsg!=null || emsg!=null)
				errorMsg ="Expected '"+localItem.startNode().toShortString()+"' and '"+localItem.endNode() +". to satisfy query but found ["+smsg+"] and ["+emsg+"].";
				break;
		}
		return this;
	}

	// TESTING
	//

//	public static void main(String[] args) {
//		AotNode n1 = new AotNode().setLabel("n1");
//		AotNode n2 = new AotNode().setLabel("n2");
//		AotNode n3 = new AotNode().setLabel("n3");
//		n1.newEdge(n2, "e1");
//		n1.newEdge(n2, "e2");
//		n1.newEdge(n3, "e3");
//		n1.newEdge(n3, "e4");
//		n1.export(new GraphVisualisation());
//
//		Iterable<AotEdge> edges = (Iterable<AotEdge>)SequenceQuery.get(n1.getEdges(Direction.OUT), selectZeroOrMany(hasEndNode(isNode(n2))));
//		for (AotEdge e : edges)
//			System.out.println(e);
//		edges = (Iterable<AotEdge>)SequenceQuery.get(n1.getEdges(Direction.OUT), selectZeroOrMany(hasEndNode(isNode(n3))));
//		for (AotEdge e : edges)
//			System.out.println(e);
//	}

}
