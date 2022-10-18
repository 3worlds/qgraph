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

import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Node;

import static au.edu.anu.qgraph.queries.CoreQueries.*;
import static au.edu.anu.qgraph.queries.base.SequenceQuery.*;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>A Query to select {@link fr.cnrs.iees.graph.Edge Edge}s linked to a {@link fr.cnrs.iees.graph.Node Node}.</p>
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Node}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link java.util.Collection Collection}{@code <Edge>}</dd>
 * <dt>Fails if</dt>
 * <dd>never fails (the returned list may be empty)</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#inEdges()			CoreQueries.inEdges()
 * @see au.edu.anu.qgraph.queries.CoreQueries#outEdges()			CoreQueries.outEdges()
 * @see au.edu.anu.qgraph.queries.CoreQueries#inEdges(Queryable)	CoreQueries.inEdges(Queryable)
 * @see au.edu.anu.qgraph.queries.CoreQueries#outEdges(Queryable)	CoreQueries.outEdges(Queryable)
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeEdges extends QueryAdaptor{
	private Direction direction;
	private Queryable query;

	/**
	 *  Only {@link Node} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node)input;
		if (query==null)
			result = localItem.edges(direction);
		else
			result = get(localItem.edges(direction),selectZeroOrMany(query));
		return this;
	}

	// Fluid interface
	/**
	 * Set the direction in which to search for {@code Edge}s
	 * @param d the direction
	 * @return this instance for agile programming
	 */
	public NodeEdges direction(Direction d) {
		direction = d;
		return this;
	}
	
	/**
	 * Set the Query edges must satisfy
	 * @param q the query
	 * @return this instance for agile programming
	 */
	public NodeEdges query(Queryable q) {
		query = q;
		return this;
	}
}
