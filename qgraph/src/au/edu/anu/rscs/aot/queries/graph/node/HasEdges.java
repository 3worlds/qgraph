package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.FilteredList;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import fr.cnrs.iees.graph.generic.Direction;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Node;
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
			edges = (DynamicList<Edge>) get(localItem.getEdges(Direction.IN),
				selectZeroOrMany(hasStartNode(nodeQuery)));
//			edges = localItem.getInEdges(hasStartNode(nodeQuery));
			break;
		case OUT:
			edges = (DynamicList<Edge>) get(localItem.getEdges(Direction.OUT),
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
