package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;

/**
 * <p>A {@link Query} to select start, end, other-end or both-ends Nodes of an Edge</p>
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Edge}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link Node}</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeNodes extends QueryAdaptor {
	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode;

	/**
	 * Only {@link Edge} arguments will be checked.
	 */
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

	/**
	 * Set the {@link Node} at one of the tips of the edge (to look for the other)
	 * @param n the start or end node of the edge
	 * @return this instance for agile programming
	 */
	public EdgeNodes refNode(Node n) {
		refNode = n;
		return this;
	}

	/**
	 * Set which end of the Edge should be searched for
	 * @param s a type of edge tip
	 * @return this instance for agile programming
	 */
	public EdgeNodes edgeNodeSelection(EdgeNodeSelection s) {
		edgeNodeSelection = s;
		return this;
	}

}
