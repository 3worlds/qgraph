/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Direction;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
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
			tempResult.addAllUnique((DynamicList<Edge>) n.getEdges(direction));
		satisfied = true;
		result = new DynamicList<Edge>(tempResult);
		return this;
	}
	
	
	// TESTING
	//

//	public static void main(String[] args) {
//
//		AotNode n1 = new AotNode().setLabel("n1");
//		AotNode n2 = new AotNode().setLabel("n2");
//		AotNode n3 = new AotNode().setLabel("n3");
//		AotNode n4 = new AotNode().setLabel("n4");
//
//		AotEdge e1 = new AotEdge(n1, n2).setLabel("e1");
//		AotEdge e2 = new AotEdge(n1, n3).setLabel("e2");
//		AotEdge e3 = new AotEdge(n1, n4).setLabel("e3");
//		AotEdge e4 = new AotEdge(n2, n3).setLabel("e4");
//
//		NodeList nl = n1.traversal();
//
//		nl.export(new GraphVisualisation());
//
//		for (AotEdge e : (Iterable<AotEdge>)outEdges().process(nl).getResult())
//			System.out.println("out edge " + e);
//
//		for (AotEdge e : (Iterable<AotEdge>)inEdges().process(nl).getResult())
//			System.out.println("in edge " + e);
//
//		for (AotEdge e : (Iterable<AotEdge>)edges().process(nl).getResult())
//			System.out.println("in-out edge " + e);
//
//	}



}
