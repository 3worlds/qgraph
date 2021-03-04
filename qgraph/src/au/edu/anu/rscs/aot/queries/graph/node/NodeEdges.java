package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Node;
import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.*;

public class NodeEdges extends QueryAdaptor{
	private Direction direction;
	private Queryable query;

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
	public NodeEdges direction(Direction d) {
		direction = d;
		return this;
	}
	
	public NodeEdges query(Queryable q) {
		query = q;
		return this;
	}
}
