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

import java.util.HashSet;
import java.util.Set;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * <p>A {@link Query} to select start, end, other-end or both-ends Nodes of a list of Edges</p> 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>Iterable&lt;Edge&gt;</dd>
 * <dt>Type of result</dt>
 * <dd>DynamicList&lt;Node&gt;</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
// tested OK with version 0.1.1 on 21/5/2019
public class EdgeListNodes extends QueryAdaptor {
	
	private EdgeNodeSelection edgeNodeSelection;
	private Node refNode;

	/**
	 * Only collections (actually {@link Iterable}s) of {@link Edge}s arguments will be checked.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Iterable<Edge> localItem = (Iterable<Edge>) input;
		Set<Node> resultSet = new HashSet<>();
		switch (edgeNodeSelection) {
		case START:
			for (Edge e : localItem)
				resultSet.add(e.startNode());
			break;
		case END:
			for (Edge e : localItem)
				resultSet.add( e.endNode());
			break;
		case OTHER:
			for (Edge e : localItem)
				if (e.otherNode(refNode) != null)
					resultSet.add(e.otherNode(refNode));
			break;
		case BOTH:
			for (Edge e : localItem) {
				resultSet.add( e.startNode());
				resultSet.add( e.endNode());
			}
			break;
		default:
			throw new QGraphException("Unknown edge node option [" + edgeNodeSelection + "]");
		}
		result = new DynamicList<Node>(resultSet);
		return this;
	}
	
	// fluid interface
	/**
	 * Set which end of the Edges should be searched for
	 * @param s a type of edge tip
	 * @return this instance for agile programming
	 */
	public EdgeListNodes edgeNodeSelection(EdgeNodeSelection s) {
		this.edgeNodeSelection=s;
		return this;
	}
	
	/**
	 * Set the {@link Node} at one of the tips of the edges (to look for the other)
	 * @param n the start or end node of edges
	 * @return this instance for agile programming
	 */
	public EdgeListNodes refNode(Node n) {
		this.refNode=n;
		return this;
	}

}
