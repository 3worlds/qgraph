/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */

public class NodeCharacteristics extends QueryAdaptor {

	public enum RootLeaf {
		ROOT, LEAF;
	}

	private RootLeaf mode;

	public NodeCharacteristics(RootLeaf mode) {
		this.mode = mode;
	}

//	public static Queryable isRoot() {
//		return new NodeCharacteristics(RootLeaf.ROOT);
//	}
//
//	public static Queryable isLeaf() {
//		return new NodeCharacteristics(RootLeaf.LEAF);
//	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node)input;
		boolean ok = true;
		switch (mode) {
		case ROOT:
			if (!localItem.isRoot())
				ok = false;
			break;
		case LEAF:
			if (!localItem.isLeaf())
				ok = false;
			break;
		}
		if (!ok)
			errorMsg = "Expected '"+localItem.toShortString()+"' to be "+mode+" node.";
		return this;
	}
	

}
