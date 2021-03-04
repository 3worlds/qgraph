package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;

public class EdgeNodes extends QueryAdaptor {
	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode;

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (input == null)
			throw new QGraphException("Input is NULL for '" + this.getClass().getSimpleName() + "'.");

		Edge localItem = (Edge) input;
		switch (edgeNodeSelection) {
		case START:
			result = localItem.startNode();
			break;
		case END:
			result = localItem.endNode();
			break;
		case OTHER:
			result = localItem.otherNode(refNode);
			break;
		case BOTH:
			Node[] nn = new Node[2];
			nn[0] = localItem.startNode();
			nn[1] = localItem.endNode();
			result = nn;
			break;
		default:
			throw new QGraphException(
					"Unrecognized option for '" + this.getClass().getSimpleName() + "' [" + edgeNodeSelection + "].");
		}
		return this;

	}
	// Fluid interface

	public EdgeNodes refNode(Node n) {
		refNode = n;
		return this;
	}

	public EdgeNodes edgeNodeSelection(EdgeNodeSelection s) {
		edgeNodeSelection = s;
		return this;
	}

}
