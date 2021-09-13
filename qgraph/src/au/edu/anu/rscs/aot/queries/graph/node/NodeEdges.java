package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Node;
import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.*;

/**
 * <p>A Query to select {@link Edge}s linked to a {@link Node}.</p>
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Node}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link Collection}{@code <Edge>}</dd>
 * </dl>
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeEdges extends QueryAdaptor{
	private Direction direction;
	private Queryable query;

	/**
	 *  Only {@link Node} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node)input;
		if (query==null)
			result = localItem.edges(direction);
		else
			result = get(localItem.edges(direction),selectZeroOrMany(query));
		return this;
	}

	// Fluid interface
	/**
	 * Set the direction in which to search for {@code Edge}s
	 * @param d the direction
	 * @return this instance for agile programming
	 */
	public NodeEdges direction(Direction d) {
		direction = d;
		return this;
	}
	
	/**
	 * Set the Query edges must satisfy
	 * @param q the query
	 * @return this instance for agile programming
	 */
	public NodeEdges query(Queryable q) {
		query = q;
		return this;
	}
}
