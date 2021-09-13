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
 * Get parts of a {@link Tree} starting from a {@link TreeNode}.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code TreeNode}</dd>
 * <dt>Type of result</dt>
 * <dd>a {@code TreeNode} or a {@code Collection<TreeNode>}</dd>
 * </dl>
 *
 * @author Shayne Flint - 2/4/2012<br/>
 * refactored by J. Gignoux - 12/4/2019 to handle new tree structure
 *
 */
public class TreeQuery extends QueryAdaptor {
	private boolean getParent;
	private boolean traverse;
	private Queryable query;

	/**
	 *  Only {@link TreeNode} arguments will be checked.
	 */
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
	/**
	 * Set the query to check on tree nodes
	 * @param q the query
	 * @return this instance for agile programming
	 */
	public TreeQuery query(Queryable q) {
		query = q;
		return this;
	}

	/**
	 * If the tree should be searched up 
	 * @return this instance for agile programming
	 */
	public TreeQuery parent() {
		getParent = true;
		return this;
	}

	/**
	 * If the tree should be searched from its root
	 * @return this instance for agile programming
	 */
	public TreeQuery root() {
		traverse = true;
		return this;
	}

	/**
	 * If the tree should be searched from the input argument
	 * @return this instance for agile programming
	 */
	public TreeQuery notRoot() {
		traverse = false;
		return this;
	}

	/**
	 * If the tree should be searched down (children)
	 * @return this instance for agile programming
	 */
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

	/**
	 * Get the childtree starting at the argument
	 * @param root the root of the child tree
	 * @return the child tree as a list of tree nodes
	 */
	public List<TreeNode> childTree(TreeNode root) {
		List<TreeNode> result = new ArrayList<TreeNode>(root.subTree());
		return result;
	}

	/**
	 * Get the childtree starting at the argument, filtering nodes by the query argument
	 * @param node the root of the child tree
	 * @param query the query to check on all tree nodes
	 * @return the child tree as a list of tree nodes (CAUTION: possibly disconnected)
	 */
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
