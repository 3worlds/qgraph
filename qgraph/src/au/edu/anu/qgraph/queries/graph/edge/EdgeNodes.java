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
package au.edu.anu.qgraph.queries.graph.edge;

import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import au.edu.anu.qgraph.queries.graph.EdgeNodeSelection;

/**
 * <p>A {@link Queryable} to select start, end, other-end or both-ends Nodes of an Edge</p>
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Edge}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link Node}</dd>
 * <dt>Fails if</dt>
 * <dd>never fails (may return {@code null})</dd>
 * </dl>
 *  
 * @see au.edu.anu.qgraph.queries.CoreQueries#endNode() CoreQueries.endNode()
 * @see au.edu.anu.qgraph.queries.CoreQueries#startNode() CoreQueries.startNode()
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeNodes extends QueryAdaptor {
	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode;

	/**
	 * Only {@link Edge} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (input == null)
			throw new NullPointerException("Input is NULL for '" + this.getClass().getSimpleName() + "'.");

		Edge localItem = (Edge) input;
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
//		case BOTH:
//			// CAUTION: this is probably flawed. shouldnt return an array but a list ???
//			Node[] nn = new Node[2];
//			nn[0] = localItem.startNode();
//			nn[1] = localItem.endNode();
//			result = nn;
//			break;
		default:{
			// BOTH NB CAUTION: this is probably flawed. shouldnt return an array but a list ???
			Node[] nn = new Node[2];
			nn[0] = localItem.startNode();
			nn[1] = localItem.endNode();
			result = nn;
		}
		}
		return this;

	}
	// Fluid interface

	/**
	 * Set the {@link Node} at one of the tips of the edge (to look for the other)
	 * @param n the start or end node of the edge
	 * @return this instance for agile programming
	 */
	public EdgeNodes refNode(Node n) {
		refNode = n;
		return this;
	}

	/**
	 * Set which end of the Edge should be searched for
	 * @param s a type of edge tip
	 * @return this instance for agile programming
	 */
	public EdgeNodes edgeNodeSelection(EdgeNodeSelection s) {
		edgeNodeSelection = s;
		return this;
	}

}
