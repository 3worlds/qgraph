package au.edu.anu.rscs.aot.queries.graph.node;


import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.TreeNode;

import static au.edu.anu.rscs.aot.queries.XCoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.XSequenceQuery.*;

public class XTrees extends QueryAdaptor{
	private boolean getParent;
	private boolean traverse;
	private Queryable query;

	@Override
	public Queryable query(Object input) {
		initQuery(input,TreeNode.class);
		TreeNode localItem = (TreeNode)input;
		if (traverse) {
			if (getParent) {
				if (query == null) {
//					result = root(localItem);
				} else {
//					result = parent(localItem, query);
					if (result == null) {
//						satisfied = false;
						return this;
					}
				}
			} else {
				if (query == null) {
//					result = childTree(localItem);
				} else {
//					result = childTree(localItem, query);
				}
			}
		} else {
			if (getParent) {
				result = localItem.getParent();
			} else {
				if (query == null)
					result = localItem.getChildren();
				else
					result = (DynamicList<TreeNode>)get(localItem.getChildren(),
						selectZeroOrMany(query));
			}
		}
//		satisfied = true;
		return this;

	}

}
