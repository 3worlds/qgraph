/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.Node;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeCharacteristics extends Query {

	private enum RootLeaf {
		ROOT, LEAF;
	}

	private RootLeaf mode;

	public NodeCharacteristics(RootLeaf mode) {
		this.mode = mode;
	}

	public static NodeCharacteristics isRoot() {
		return new NodeCharacteristics(RootLeaf.ROOT);
	}

	public static NodeCharacteristics isLeaf() {
		return new NodeCharacteristics(RootLeaf.LEAF);
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem = (Node)item;
		satisfied = false;
		switch (mode) {
		case ROOT:
			satisfied = localItem.isRoot();
			break;
		case LEAF:
			satisfied = localItem.isLeaf();
			break;
		}
		return this;
	}

	@Override
	public String toString() {
		return "[must be a " + mode + " node]";
	}
	

}
