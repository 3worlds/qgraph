/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.graph.node;

import java.io.File;
import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.util.Uid;
import fr.cnrs.iees.graph.generic.DataNode;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
// Note: this should probably move to a 'configurable graph' library
public class NodeReferenceQuery extends Query {

	private String reference;

	public NodeReferenceQuery(String reference) {
		this.reference = reference;
	}

	public static NodeReferenceQuery matchesRef(String reference) {
		return new NodeReferenceQuery(reference);
	}



	@Override
	public boolean satisfied(Object item) {
		DataNode localItem = (DataNode)item;
		return NodeReference.matchesRef(localItem, reference);
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		DataNode localItem = (DataNode)item;
//		satisfied = localItem.matchesRef(reference);
		satisfied = NodeReference.matchesRef(localItem, reference);
		return this;
	}


	@Override
	public String toString() {
		return "[Node matches reference " + reference + "]";
	}


	// TESTING
	//

//	public static void main(String[] args) throws Exception {
//		File graphFile = Resource.getFile("testGraph.dsl", Parser.class);
//		AotGraph g = new AotGraph();
//		Parser p = new UniversalParser(new Tokenizer(graphFile));
//		g = p.getGraph();
//		g.show();
//
//		ReferenceableNode n = g.findNode("node:Node1");
//		System.out.println(n);
//
//		NodeReferenceQuery q1 = NodeReferenceQuery.matchesRef("Node1");
//		q1.check(n);
//
//		System.out.println(g.findNode("Node1"));
//		Uid id = n.getId();
//		String idStr = id.toString();
//		System.out.println("looking for node id=\"" + id + "\", found " + g.findNode(id));
//
//	}


}
