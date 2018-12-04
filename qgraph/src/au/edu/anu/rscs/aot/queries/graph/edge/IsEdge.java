package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Edge;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class IsEdge extends Query {

	private Edge edge;
	
	public IsEdge(Edge edge) {
		this.edge = edge;
	}

	public static IsEdge isEdge(Edge edge) {
		return new IsEdge(edge);
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Edge localItem  = (Edge) item;
		satisfied = localItem.equals(edge);
		return this;
	}
	
	@Override
	public String toString() {
		return "[IsEdge]";
	}
	

}
