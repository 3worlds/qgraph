package au.edu.anu.rscs.aot.queries.graph;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Node;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Graph;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class HasOnlyNodes extends Query {

	private Query nodeQuery;

	public HasOnlyNodes(Query nodeQuery) {
		this.nodeQuery = nodeQuery;
	}


	public static HasOnlyNodes hasOnlyNodes(Query nodeQuery) {
		return new HasOnlyNodes(nodeQuery);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Graph<Node,Edge> localItem = (Graph<Node,Edge>)item;
		for (Node n : localItem.nodes()) {
			nodeQuery.process(n);
			if (!nodeQuery.satisfied()) {
				satisfied = false;
				return this;
			}
		}
		satisfied = true;
		return this;
	}

	
	@Override
	public String toString() {
		return "[All nodes must satisfy " + nodeQuery + "]";
	}
	

	// TESTING
	//

//	public static void main(String[] args) {
//		TestGraph tg = new TestGraph();
//		NodeList  nl = tg.getNodeList();
//		HasOnlyNodes q = hasOnlyNodes(IsClass.isClass(TestNode.class, TestNode2.class));
//		//		HasNodes q = hasNodes(IsClass.isClass(TestNode.class));
//		q.check(nl);
//	}

}
