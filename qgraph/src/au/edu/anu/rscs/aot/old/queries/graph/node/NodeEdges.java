/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.old.queries.graph.node;

import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Node;

import static au.edu.anu.rscs.aot.old.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.old.queries.base.SequenceQuery.get;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
@Deprecated
public class NodeEdges extends Query {

	private Direction direction;
	private Query query;
	
	public NodeEdges(Direction direction, Query query) {
		this.direction = direction;
		this.query = query;
	}

	public static Query inEdges() {
		return new NodeEdges(Direction.IN, null);
	}
	
	public static Query inEdges(Query query) {
		return new NodeEdges(Direction.IN, query);
	}
	
	public static Query outEdges() {
		return new NodeEdges(Direction.OUT, null);
	}
	
	public static Query outEdges(Query query) {
		return new NodeEdges(Direction.OUT, query);
	}
	
//	public static Query edges() {
//		return new NodeEdges(EdgeDirection.IN_OUT, null);
//	}
//	
//	public static Query edges(Query query) {
//		return new NodeEdges(EdgeDirection.IN_OUT, query);
//	}
	
	public static Query edges(Direction direction) {
		return new NodeEdges(direction, null);
	}
	
	public static Query edges(Direction direction, Query query) {
		return new NodeEdges(direction, query);
	}
	
	@Override
	public String toString() {
		String result = "[Node " + direction + " edges";
		if (query != null)
			result = result + " matching " + query;
		return result + "]";
	}
	
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem = (Node)item;
		if (query == null)
		    result = localItem.edges(direction);
		else {
		    result = get(localItem.edges(direction), selectZeroOrMany(query));
		}
		satisfied = true;
		return this;
	}

	
	// fluent API
	//
	
}
