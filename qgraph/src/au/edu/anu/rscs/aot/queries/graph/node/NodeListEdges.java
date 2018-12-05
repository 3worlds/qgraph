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
