package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Node;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeListNodes extends Query {

	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode = null;

	public EdgeListNodes(EdgeNodeSelection edgeNodeSelection) {
		this.edgeNodeSelection = edgeNodeSelection;
	}

	public EdgeListNodes(EdgeNodeSelection edgeNodeSelection, Node refNode) {
		this(edgeNodeSelection);
		this.refNode = refNode;
	}

	public static Query startNodes() {
		return new EdgeListNodes(EdgeNodeSelection.START);
	}

	public static Query endNodes() {
		return new EdgeListNodes(EdgeNodeSelection.END);
	}

	public static Query otherNodes(Node refNode) {
		return new EdgeListNodes(EdgeNodeSelection.OTHER, refNode);
	}

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
