package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class IsNode extends Query {

	private Node node;
	
	public IsNode(Node node) {
		this.node = node;
	}

	public static IsNode isNode(Node node) {
		return new IsNode(node);
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem  = (Node) item;
		satisfied = localItem.equals(node);
		return this;
	}
	
	@Override
	public String toString() {
		return "[IsNode]";
	}
	

}
