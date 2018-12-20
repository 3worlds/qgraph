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
package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeNodes extends Query {

	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode = null;
	

	public EdgeNodes(EdgeNodeSelection edgeNodeSelection) {
		this.edgeNodeSelection = edgeNodeSelection;
	}

	public EdgeNodes(EdgeNodeSelection edgeNodeSelection, Node refNode) {
		this(edgeNodeSelection);
		this.refNode = refNode;
	}

	public static Query startNode() {
		return new EdgeNodes(EdgeNodeSelection.START);
	}

	public static Query endNode() {
		return new EdgeNodes(EdgeNodeSelection.END);
	}

	public static Query otherNode(Node refNode) {
		return new EdgeNodes(EdgeNodeSelection.END, refNode);
	}

	public static Query bothNodes() {
		return new EdgeNodes(EdgeNodeSelection.BOTH);
	}

	@Override
	public String toString() {
		return "[" + edgeNodeSelection + " node(s)]";
	}
	

	@Override
	public Query process(Object item) {
		Edge localItem = (Edge)item;
		if (localItem == null) {
			satisfied = false;
			return this;
		} else {
			satisfied = true;
			switch (edgeNodeSelection) {
			case START:
				result = localItem.startNode();
				break;
			case END:
				result = localItem.endNode();
				break;
			case OTHER:
				result = localItem.otherNode(refNode);
				break;
			case BOTH:
				Node[] nn = new Node[2];
				nn[0] = localItem.startNode();
				nn[1] = localItem.endNode();
				result = nn;
				break;
			default:
				satisfied = false;
				break;
			}
			return this;
		}
	}

}
