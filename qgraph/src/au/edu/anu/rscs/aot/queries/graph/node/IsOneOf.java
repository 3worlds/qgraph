package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class IsOneOf extends Query {

	private DynamicList<Node> nodeList;

	public IsOneOf(Node... nodes) {
		add(nodes);
	}

	public static IsOneOf isOneOf(Node... nodes) {
		return new IsOneOf(nodes);
	}

	public void add(Node... nodes) {
		for (Node n : nodes)
			this.nodeList.add(n);
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem  = (Node) item;
		satisfied = nodeList.contains(localItem);
		return this;
	}

	@Override
	public String toString() {
		return "[IsOneOf]";
	}


}
