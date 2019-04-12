/**************************************************************************
 *  QGRAPH - A Query system for graphs                                    *
 *                                                                        *
 *  Copyright 2018: Shayne Flint, Jacques Gignoux & Ian D. Davies         *
 *       shayne.flint@anu.edu.au                                          * 
 *       jacques.gignoux@upmc.fr                                          *
 *       ian.davies@anu.edu.au                                            * 
 *                                                                        *
 *  QGRAPH implements a Query system enabling one to search a set of      *
 *  objects and return results if these objects match the queries. It has *
 *  been designed for graphs but some queries are more general and can    *
 *  apply to any kind of object.                                          * 
 **************************************************************************                                       
 *  This file is part of QGRAPH (A Query system for graphs).              *
 *                                                                        *
 *  QGRAPH is free software: you can redistribute it and/or modify        *
 *  it under the terms of the GNU General Public License as published by  *
 *  the Free Software Foundation, either version 3 of the License, or     *
 *  (at your option) any later version.                                   *
 *                                                                        *
 *  QGRAPH is distributed in the hope that it will be useful,             *
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of        *
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the         *
 *  GNU General Public License for more details.                          *                         
 *                                                                        *
 *  You should have received a copy of the GNU General Public License     *
 *  along with QGRAPH. If not, see <https://www.gnu.org/licenses/gpl.html>*
 *                                                                        *
 **************************************************************************/
package au.edu.anu.rscs.aot.queries.graph.node;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.get;

/**
 * 
 * @author Shayne Flint - 2/4/2012
 * refactored by J. Gignoux - 12/4/2019 to handle new tree structure
 *
 */
public class Trees extends Query {

	// deals with navigating trees formed by edges labeled "_child" (as created by the DSL and XML importers)
	//

	public final static String CHILD_LABEL = "_child";

	private boolean getParent;
	private boolean traverse;
	private Query query;

	public Trees(Query query, boolean getParent, boolean getRoot) {
		this.getParent = getParent;
		this.traverse = getRoot;
		this.query = query;
	}

	// return parent node
	public static Trees parent() {
		return new Trees(null, true, false);
	}

	// traverse up tree to root node and return it
	public static Trees root() {
		return new Trees(null, true, true);
	}

	// traverse up tree looking for a node q=that matches the query
	public static Trees parent(Query query) {
		return new Trees(query, true, true);
	}

	// return all children
	public static Trees children() {
		return new Trees(null, false, false);
	}

	// return all nodes in tree below this node
	public static Trees childTree() {
		return new Trees(null, false, true);
	}

	// return all children that match query
	public static Trees children(Query query) {
		return new Trees(query, false, false);
	}

	// return all nodes in tree below this node that match query
	public static Trees childTree(Query query) {
		return new Trees(query, false, true);
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


	private Node root(Node node) {
//		Edge parentEdge = node.getInEdge(hasTheLabel(CHILD_LABEL));
		Edge parentEdge = (Edge) get(node.getEdges(Direction.IN),
			selectZeroOrOne(hasTheLabel(CHILD_LABEL)));
		if (parentEdge == null)
			return node;
		else
			return root(parentEdge.startNode());
	}

	private Node parent(Node node, Query query) {
		if (query.satisfied(node))
			return node;
		else {
			Node parent = (Node)get(node, 
				inEdges(hasTheLabel(CHILD_LABEL)), 
				selectZeroOrOne(), 
				startNode());
			if (parent == null)
				return null;
			else
				return parent(parent, query);
		}
	}

	@SuppressWarnings("unchecked")
	public DynamicList<Node> childTree(Node node) {
		DynamicList<Node> nodeList = new DynamicList<Node>();
		nodeList.addAll((DynamicList<Node>)get(node, 
			outEdges(hasTheLabel(CHILD_LABEL)), 
			selectZeroOrMany(), 
			edgeListEndNodes()));
		for (Node n : nodeList) 
			nodeList.addAll(childTree(n));
		return nodeList;
	}
	
	public DynamicList<Node> childTree(Node node, Query query) {
		DynamicList<Node> childTree = childTree(node);
		DynamicList<Node> nodeList = new DynamicList<Node>();
		for (Node n : childTree) 
			if (query.satisfied(n))
				nodeList.add(n);
		return nodeList;		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Node localItem = (Node)item;
		//		NodeList nodeList = new NodeList();
		//		nodeList.addNode(localItem);		
		if (traverse) {
			if (getParent) {
				if (query == null) {
					result = root(localItem);
				} else {
					result = parent(localItem, query);
					if (result == null) {
						satisfied = false;
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
				result = (Node)get(localItem, 
					inEdges(hasTheLabel(CHILD_LABEL)), 
					selectZeroOrOne(), 
					startNode());
			} else {
				if (query == null)
					result = (DynamicList<Node>)get(localItem, 
						outEdges(hasTheLabel(CHILD_LABEL)), 
						edgeListEndNodes());
				else
					result = (DynamicList<Node>)get(localItem, 
						outEdges(hasTheLabel(CHILD_LABEL)), 
						edgeListEndNodes(), 
						selectZeroOrMany(query));	
			}
		}
		satisfied = true;
		return this;
	}


}
