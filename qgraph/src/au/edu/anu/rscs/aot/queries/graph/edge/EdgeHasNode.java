package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Node;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
public class EdgeHasNode extends Query {

	private EdgeNodeSelection edgeNodeSelection;
	private Query nodeQuery;
	private Node refNode = null;

	public EdgeHasNode(EdgeNodeSelection edgeNodeSelection, Query nodeQuery) {
		this.edgeNodeSelection = edgeNodeSelection;
		this.nodeQuery = nodeQuery;
	}

	public EdgeHasNode(EdgeNodeSelection edgeNodeSelection, Query nodeQuery, Node refNode) {
		this(edgeNodeSelection, nodeQuery);
		this.refNode = refNode;
	}

	public static Query hasEndNode(Query nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.END, nodeQuery);
	}

	public static Query hasStartNode(Query nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.START, nodeQuery);
	}

	public static Query hasOtherNode(Query nodeQuery, Node refNode) {
		return new EdgeHasNode(EdgeNodeSelection.OTHER, nodeQuery, refNode);
	}

	public static Query hasBothNodes(Query nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.BOTH, nodeQuery);
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Edge localItem = (Edge)item;
		switch (edgeNodeSelection) {
		case START:
			satisfied = nodeQuery.satisfied(localItem.startNode());
			break;
		case END:
			satisfied = nodeQuery.satisfied(localItem.endNode());
			break;
		case OTHER:
			satisfied = nodeQuery.satisfied(localItem.otherNode(refNode));
			break;
		case BOTH:
			satisfied = (nodeQuery.satisfied(localItem.startNode()) && nodeQuery.satisfied(localItem.endNode()));
			break;
		}
		return this;
	}

	@Override
	public String toString() {
		return "[" + stateString() + "must have " + edgeNodeSelection + " node(s) matching " + nodeQuery + "]";
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
