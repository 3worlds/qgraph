package au.edu.anu.rscs.aot.queries.graph;

import java.util.logging.Logger;
import fr.cnrs.iees.graph.generic.Node;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Graph;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class GraphLogQuery extends Query {

	private Logger log = Logger.getLogger(GraphLogQuery.class.toString());

	private String prefix;
	
	public GraphLogQuery(String prefix) {
		this.prefix= prefix;
	}
	
	public static GraphLogQuery logGraph(String prefix) {
		return new GraphLogQuery(prefix);
	}
	
	public static GraphLogQuery logGraph() {
		return new GraphLogQuery("");
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Graph<Node,Edge> localItem = (Graph<Node,Edge>)item;
		log.fine(prefix + localItem.toString());
		satisfied = true;
		return this;
	}

	@Override
	public String toString() {
		return "[GraphLogQuery]";
	}
	

}
