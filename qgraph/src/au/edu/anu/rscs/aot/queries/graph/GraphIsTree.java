package au.edu.anu.rscs.aot.queries.graph;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Graph;
import fr.cnrs.iees.graph.generic.Node;
import fr.cnrs.iees.graph.generic.Edge;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class GraphIsTree extends Query {
	
	public static GraphIsTree isTree() {
		return new GraphIsTree();
	}
	
	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Graph<Node,Edge> localItem = (Graph<Node,Edge>)item;
		throw new QGraphException("GraphIsTree is not implemented");
	}

}
