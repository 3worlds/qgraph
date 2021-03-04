package au.edu.anu.rscs.aot.queries.graph.node;

import java.util.HashSet;
import java.util.Set;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.old.queries.Query;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * <p>
 * A {@link Query} to select In, OUT or all edges of a list of Nodes.
 * </p>
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>Iterable&lt;Node&gt;</dd>
 * <dt>Type of result</dt>
 * <dd>DynamicList&lt;Edge&gt;</dd>
 * </dl>
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeListEdges extends QueryAdaptor {
	private Direction direction;

	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Iterable<Node> localItem = (Iterable<Node>) input;

		Set<Edge> edgeSet = new HashSet<>();
		if (direction != null)
			for (Node n : localItem)
				edgeSet.addAll(n.edges(direction));
		else
			for (Node n : localItem)
				edgeSet.addAll(n.edges());
		result = new DynamicList<Edge>(edgeSet);
		return this;

	}

	// Fluid interface
	public NodeListEdges direction(Direction d) {
		direction = d;
		return this;
	}

}
