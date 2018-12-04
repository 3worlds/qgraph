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
// NOT TESTED
public class EdgeNodes extends Query {

	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode = null;
	

	public EdgeNodes(EdgeNodeSelection edgeNodeSelection) {
		this.edgeNodeSelection = edgeNodeSelection;
	}

	public EdgeNodes(EdgeNodeSelection edgeNodeSelection, Node refNode) {
		this(edgeNodeSelection);
		this.refNode = refNode;
	}

	public static Query startNode() {
		return new EdgeNodes(EdgeNodeSelection.START);
	}

	public static Query endNode() {
		return new EdgeNodes(EdgeNodeSelection.END);
	}

	public static Query otherNode(Node refNode) {
		return new EdgeNodes(EdgeNodeSelection.END, refNode);
	}

	public static Query bothNodes() {
		return new EdgeNodes(EdgeNodeSelection.BOTH);
	}

	@Override
	public String toString() {
		return "[" + edgeNodeSelection + " node(s)]";
	}
	

	@Override
	public Query process(Object item) {
		Edge localItem = (Edge)item;
		if (localItem == null) {
			satisfied = false;
			return this;
		} else {
			satisfied = true;
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
				satisfied = false;
				break;
			}
			return this;
		}
	}

}
