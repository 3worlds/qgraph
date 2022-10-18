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

import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

import static au.edu.anu.qgraph.queries.CoreQueries.*;
import static au.edu.anu.qgraph.queries.base.SequenceQuery.get;

import au.edu.anu.qgraph.collections.FilteredList;
import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import au.edu.anu.qgraph.queries.graph.uml.Multiplicity;

/**
 * <p>Check if a {@link Node} has {@link Edge}s that satisfy some constraints like
 * a Query, a class Id, a multiplicity.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Node}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input edge list filtered by a query on node, a query on edges, an edge class id, an edge direction,
 * does not match the multiplicity passed to the constructor</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasEdges(Queryable, Multiplicity) CoreQueries.hasEdges(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasInEdges(Queryable, Multiplicity) CoreQueries.hasInEdges(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasOutEdges(Queryable, Multiplicity) CoreQueries.hasOutEdges(...)
 * 
 * @author Shayne Flint - 2/4/2012<br/>
 * refactored by Jacques Gignoux - 4/10/2021
 *
 */
// TODO: test this in detail - and maybe refactor.
public class HasEdges extends QueryAdaptor {

	private Direction edgeDirection;
	private Queryable nodeQuery;
	private Queryable edgeQuery;
	private Multiplicity multiplicity;
	private String label;

	/**
	 * 
	 * @param edgeDirection the edge direction - {@code null} means all directions 
	 * @param nodeQuery a query to check on the {@code Node} argument to {@code submit(...)} (input)
	 * @param edgeQuery a query to check on the returned {@code Edge}s
	 * @param multiplicity constraint on the number of {@code Edge}s to search
	 * @param label an {@code Edge} class id to match
	 */
	public HasEdges(Direction edgeDirection, 
			Queryable nodeQuery, 
			Queryable edgeQuery, 
			Multiplicity multiplicity,
			String label) {
		this.edgeDirection = edgeDirection;
		this.nodeQuery = nodeQuery;
		this.edgeQuery = edgeQuery;
		this.multiplicity = multiplicity;
		this.label = label;
	}

	/**
	 * 
	 * @param edgeDirection the edge direction - {@code null} means all directions 
	 * @param nodeQuery a query to check on the {@code Node} argument to {@code submit(...)} (input)
	 * @param multiplicity constraint on the number of {@code Edge}s to search
	 */
	public HasEdges(Queryable nodeQuery,
			Direction edgeDirection,
			Multiplicity multiplicity) {
		this.edgeDirection = edgeDirection;
		this.multiplicity = multiplicity;	
		this.nodeQuery = nodeQuery;
	}

	/**
	 * Set a query {@code Edge}s must satisfy
	 * @param edgeQuery a query to check on the returned {@code Edge}s
	 * @return this instance for agile programmming
	 */
	public HasEdges withEdgeQuery(Queryable edgeQuery) {
		this.edgeQuery = edgeQuery;
		return this;
	}

	/**
	 * Set a constraint on edge labels (class ids)
	 * @param label the label edges must match
	 * @return this instance for agile programmming
	 */
	public HasEdges withLabel(String label) {
		this.label = label;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node) input;

		DynamicList<Edge> edges = null;
		switch (edgeDirection) {
		case IN:
			// caution: not tested yet
			edges = (DynamicList<Edge>) get(localItem.edges(Direction.IN), 
				selectZeroOrMany(hasStartNode(nodeQuery)));
			break;
		case OUT:
			edges = (DynamicList<Edge>) get(localItem.edges(Direction.OUT), 
				selectZeroOrMany(hasEndNode(nodeQuery)));
			break;
		default:
			edges = (DynamicList<Edge>) get(localItem.edges(), 
				selectZeroOrMany(hasOtherNode(nodeQuery,localItem))); 
			break;
		}

		if (edgeQuery != null)
			edges = new FilteredList<Edge>(edges, edgeQuery);

		if (label != null)
			edges = new FilteredList<Edge>(edges, hasTheLabel(label));

		int edgesSize = edges.size();

		switch (multiplicity) {
		case ZERO:
			if (edgesSize != 0)
				errorMsg = "Expected " + multiplicity + " but found " + edgesSize + ".";
			break;
		case ONE:
			if (edgesSize != 1)
				errorMsg = "Expected " + multiplicity + " but found " + edgesSize + ".";
			break;
		case ONE_MANY:
			if (edgesSize < 1)
				errorMsg = "Expected " + multiplicity + " but found " + edgesSize + ".";
			break;
		case ZERO_ONE:
			if (edgesSize > 1)
				errorMsg = "Expected " + multiplicity + " but found " + edgesSize + ".";
			break;
		case ZERO_MANY:
//			if (edgesSize <0 )//?
//				errorMsg = "Expected " + multiplicity + " but found " + edgesSize + ".";
			break;
		}

		return null;
	}

}
