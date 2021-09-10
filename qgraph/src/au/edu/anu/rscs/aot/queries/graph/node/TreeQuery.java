package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.TreeNode;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.*;

import java.util.ArrayList;
import java.util.List;

/**
*
* @author Shayne Flint - 2/4/2012
* refactored by J. Gignoux - 12/4/2019 to handle new tree structure
*
*/
public class TreeQuery extends QueryAdaptor {
	private boolean getParent;
	private boolean traverse;
	private Queryable query;

	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		TreeNode localItem = (TreeNode) input;
		if (traverse) {
			if (getParent) {
				if (query == null) {
					result = root(localItem);
				} else {
					result = parent(localItem, query);
					if (result == null) {
						errorMsg = "not parent found to satisfy " + query;
						return this;
					}
				}
			} else {
				if (query == null) {
					result = childTree(localItem);
				} else {
					result = childTree(localItem, query);
				}
			}
		} else {
			if (getParent) {
				result = localItem.getParent();
			} else {
				if (query == null)
					result = localItem.getChildren();
				else
					result = (DynamicList<TreeNode>) get(localItem.getChildren(), selectZeroOrMany(query));
			}
		}
//		satisfied = true;
		return this;

	}

	@Override
	public String toString() {
		String result = "[Trees ";
		if (traverse)
			result = result + " traverse ";
		if (getParent)
			result = result + " parent node";
		else
			result = result + " child node(s)";
		if (query != null)
			result = result + " matching " + query;
		return result + "]";
	}

	// Fluid interface
	public TreeQuery query(Queryable q) {
		query = q;
		return this;
	}

	public TreeQuery parent() {
		getParent = true;
		return this;
	}

	public TreeQuery root() {
		traverse = true;
		return this;
	}

	public TreeQuery notRoot() {
		traverse = false;
		return this;
	}

	public TreeQuery notParent() {
		getParent = false;
		return this;
	}

	private TreeNode root(TreeNode node) {
		// recurse to the root. Could be the input node.
		while (node.getParent() != null)
			node = node.getParent();
		return node;
	}

	private TreeNode parent(TreeNode node, Queryable query) {
		// WARNING: DONT EXPOSE Tree methods requiring queries until the behaviour is
		// clearly defined.
		query.submit(node);
		if (query.satisfied())
			return node;
		else {
			TreeNode parent = node.getParent();
			if (parent == null)
				return null;
			else
				return parent(parent, query);
		}
	}

	public List<TreeNode> childTree(TreeNode root) {
		List<TreeNode> result = new ArrayList<TreeNode>(root.subTree());
		return result;
	}

	public DynamicList<TreeNode> childTree(TreeNode node, Queryable query) {
// WARNING: DONT EXPOSE Tree methods requiring queries until the behaviour is clearly defined.
		List<TreeNode> subTree = childTree(node);
		DynamicList<TreeNode> result = new DynamicList<TreeNode>();
		for (TreeNode n : subTree) {
			query.submit(n);
			if (query.satisfied())
				result.add(n);
		}
		return result;
	}

}
