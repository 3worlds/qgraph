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
package au.edu.anu.rscs.aot.old.queries.graph.edge;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.old.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * <p>A {@link Query} to select start, end, other-end or both-ends Nodes of a list of Edges</p> 
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>Iterable&lt;Edge&gt;</dd>
 * <dt>Type of result</dt>
 * <dd>DynamicList&lt;Node&gt;</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
// tested OK with version 0.1.1 on 21/5/2019
@Deprecated
public class EdgeListNodes extends Query {

	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode = null;

	private EdgeListNodes(EdgeNodeSelection edgeNodeSelection) {
		this.edgeNodeSelection = edgeNodeSelection;
	}

	private EdgeListNodes(EdgeNodeSelection edgeNodeSelection, Node refNode) {
		this(edgeNodeSelection);
		this.refNode = refNode;
	}

	/**
	 * Selects all start nodes of the input edge list
	 * @return the resulting EdgeListNodes query
	 */
	public static Query startNodes() {
		return new EdgeListNodes(EdgeNodeSelection.START);
	}

	/**
	 * Selects all end nodes of the input edge list
	 * @return the resulting EdgeListNodes query
	 */
	public static Query endNodes() {
		return new EdgeListNodes(EdgeNodeSelection.END);
	}

	/**
	 * Selects all nodes of the input edge list which have refNode at the other end of the 
	 * Edge.
	 * @param refNode the node to use as the start (the other node is selected) 
	 * @return the resulting EdgeListNodes query
	 */
	public static Query otherNodes(Node refNode) {
		return new EdgeListNodes(EdgeNodeSelection.OTHER, refNode);
	}

	/**
	 * Selects all nodes of the input edge list
	 * @return the resulting EdgeListNodes query
	 */
	public static Query bothNodes() {
		return new EdgeListNodes(EdgeNodeSelection.BOTH);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Iterable<Edge> localItem = (Iterable<Edge>)item;
		DynamicList<Node> tempResult = new DynamicList<Node>();
		switch (edgeNodeSelection) {
		case START:
			for (Edge e : localItem) 
				tempResult.addUnique((Node) e.startNode());
			satisfied = true;
			break;
		case END:
			for (Edge e : localItem) 
				tempResult.addUnique((Node) e.endNode());
			satisfied = true;
			break;
		case OTHER:
			for (Edge e : localItem) 
				if (e.otherNode(refNode)!=null)
					tempResult.addUnique((Node) e.otherNode(refNode));
			satisfied = true;
			break;
		case BOTH:
			for (Edge e : localItem) {
				tempResult.addUnique((Node) e.startNode());
				tempResult.addUnique((Node) e.endNode());
			}
			satisfied = true;
			break;
		default:
			satisfied = false;
			break;
		}
		result = new DynamicList<Node>(tempResult);
		return this;
	}


	@Override
	public String toString() {
		return "[" + edgeNodeSelection + " nodes from list of edges]";
	}
	


//	// TESTING
//	//
//
//	public static void main(String[] args) {
//		Logger log = LoggerFactory.getLogger(EdgeListNodes.class, "test main");
//
//		Node n1 = new AotNode().setLabel("n1");
//		Node n2 = new AotNode().setLabel("n2");
//		Node n3 = new AotNode().setLabel("n3");
//		Node n4 = new AotNode().setLabel("n4");
//
//		Edge e1 = new AotEdge(n1, n2).setLabel("e1");
//		Edge e2 = new AotEdge(n1, n3).setLabel("e2");
//		Edge e3 = new AotEdge(n1, n4).setLabel("e3");
//		Edge e4 = new AotEdge(n2, n3).setLabel("e4");
//
//		n1.export(new GraphVisualisation());
//
//		NodeList g = new NodeList(n1);
//
//		for (Node n : (Iterable<Node>)startNodes().process(g.getEdges()).getResult())
//			log.trace("start node " + n);
//
//		for (Node n : (Iterable<Node>)endNodes().process(g.getEdges()).getResult())
//			log.trace("end node " + n);
//	}


}
