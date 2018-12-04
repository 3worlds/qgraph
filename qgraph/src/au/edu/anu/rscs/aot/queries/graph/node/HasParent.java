package au.edu.anu.rscs.aot.queries.graph.node;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.TreeNode;

import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.get;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class HasParent extends Query {

	private Query query;

	public HasParent(Query query) {
		this.query = query;
	}

	public static HasParent hasParent(Query query) {
		return new HasParent(query);
	}

	public static HasParent hasParent() {
		return new HasParent(null);
	}



	@Override
	public String toString() {
		String result = "[HasParent";
		if (query != null)
			result = result + " matching " + query;
		return result + "]";
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		TreeNode localItem = (TreeNode)item;

		if (query == null) {
			satisfied = (localItem.getParent() != null);
			return this;
		} else {
			TreeNode parent = (TreeNode) get(localItem, parent(query));
//			AotNode parent = localItem.getParent(query);
			satisfied = (parent != null);
			return this;
		}

	}


}
