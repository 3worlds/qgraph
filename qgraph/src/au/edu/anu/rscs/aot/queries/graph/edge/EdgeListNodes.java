package au.edu.anu.rscs.aot.queries.graph.edge;

import java.util.HashSet;
import java.util.Set;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

public class EdgeListNodes extends QueryAdaptor {
	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode;

	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Iterable<Edge> localItem = (Iterable<Edge>) input;
		Set<Node> resultSet = new HashSet<>();
		switch (edgeNodeSelection) {
		case START:
			for (Edge e : localItem)
				resultSet.add(e.startNode());
			break;
		case END:
			for (Edge e : localItem)
				resultSet.add( e.endNode());
			break;
		case OTHER:
			for (Edge e : localItem)
				if (e.otherNode(refNode) != null)
					resultSet.add(e.otherNode(refNode));
			break;
		case BOTH:
			for (Edge e : localItem) {
				resultSet.add( e.startNode());
				resultSet.add( e.endNode());
			}
			break;
		default:
			throw new QGraphException("Unknown edge node option [" + edgeNodeSelection + "]");
		}
		result = new DynamicList<Node>(resultSet);
		return this;
	}
	// fluid interface
	public EdgeListNodes edgeNodeSelection(EdgeNodeSelection s) {
		this.edgeNodeSelection=s;
		return this;
	}
	
	public EdgeListNodes refNode(Node n) {
		this.refNode=n;
		return this;
	}

}
