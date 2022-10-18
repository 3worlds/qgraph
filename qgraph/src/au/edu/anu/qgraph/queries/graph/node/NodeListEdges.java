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
package au.edu.anu.qgraph.queries.graph.node;

import java.util.HashSet;
import java.util.Set;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * <p> A {@link Queryable} to select In, OUT or all edges of a list of Nodes.</p>
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>Iterable&lt;Node&gt;</dd>
 * <dt>Type of result</dt>
 * <dd>DynamicList&lt;Edge&gt;</dd>
 * <dt>Fails if</dt>
 * <dd>never fails (the returned list may be empty)</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#nodeListEdges() CoreQueries.nodeListEdges()
 * @see au.edu.anu.qgraph.queries.CoreQueries#nodeListInEdges() CoreQueries.nodeListInEdges()
 * @see au.edu.anu.qgraph.queries.CoreQueries#nodeListOutEdges() CoreQueries.nodeListOutEdges()
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeListEdges extends QueryAdaptor {
	private Direction direction;

	/**
	 * Only collections (actually {@link Iterable}s) of {@link Node}s arguments will be checked.
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Iterable<Node> localItem = (Iterable<Node>) input;

		Set<Edge> edgeSet = new HashSet<>();
		if (direction != null)
			for (Node n : localItem)
				edgeSet.addAll(n.edges(direction));
		else
			for (Node n : localItem)
				edgeSet.addAll(n.edges());
		result = new DynamicList<Edge>(edgeSet);
		return this;

	}

	// Fluid interface
	/**
	 * Set the direction in which to search for edges
	 * @param d the direction in which to search
	 * @return this instance for agile programming
	 */
	public NodeListEdges direction(Direction d) {
		direction = d;
		return this;
	}

}
