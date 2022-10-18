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

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import au.edu.anu.qgraph.queries.graph.EdgeNodeSelection;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
 * <p>
 * A {@link Queryable} to check that one or both {@link Node}s of an {@link Edge}
 * satisfy some Query.
 * </p>
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Edge}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>the input tip node(s) as requested in the constructor do not match the query</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasEndNode(Queryable) CoreQuerieshasEndNode(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasStartNode(Queryable) CoreQueries.hasStartNode(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasBothNodes(Queryable) CoreQueries.hasBothNodes(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasOtherNode(Queryable, Node) CoreQueries.hasOtherNode(...)
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
// Tested OK with version 0.1.1 on 21/5/2019
// All static methods moved to CoreQueries
public class EdgeHasNode extends QueryAdaptor {

	private EdgeNodeSelection edgeNodeSelection;
	private Queryable nodeQuery;
	private Node refNode = null;

	/**
	 * Constructor for checking start node, end node or both nodes 
	 * 
	 * @param edgeNodeSelection which node to check: {@link EdgeNodeSelection}{@code .START}, {@code END} or {@code BOTH}
	 * @param nodeQuery the query to check on edge tip nodes
	 */
	public EdgeHasNode(EdgeNodeSelection edgeNodeSelection, Queryable nodeQuery) {
		this.edgeNodeSelection = edgeNodeSelection;
		this.nodeQuery = nodeQuery;
	}

	/**
	 * Constructor for checking the other edge tip node
	 * 
	 * @param nodeQuery the query to check on the edge tip node opposite to refNode
	 * @param refNode one of the tip nodes of the edge (required for the {@code OTHER} case only)
	 */
	public EdgeHasNode(Queryable nodeQuery, Node refNode) {
		this(EdgeNodeSelection.OTHER, nodeQuery);
		this.refNode = refNode;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Edge localItem = (Edge) input;
		switch (edgeNodeSelection) {
		case START:
			if (!nodeQuery.submit(localItem.startNode()).satisfied())
				errorMsg = "Expected start node '" + localItem.startNode().toShortString() + "' of edge '"
					+ localItem.toShortString() + "' to satisfy query but found [" +nodeQuery.errorMsg()+"]. ";
			break;
		case END:
			if (!nodeQuery.submit(localItem.endNode()).satisfied())
				errorMsg = "Expected end node '" + localItem.endNode().toShortString() + "' of edge '"
					+ localItem.toShortString() + "' satisfy query but found [" +nodeQuery.errorMsg()+"]. ";

			break;
		case OTHER:
			if (!nodeQuery.submit(localItem.otherNode(refNode)).satisfied())
				errorMsg = "Expected node '" + refNode.toShortString() + "' of edge '"
					+ localItem.toShortString() + "' satisfy query but found [" +nodeQuery.errorMsg()+"]. ";

			break;
		case BOTH:
			String smsg = nodeQuery.submit(localItem.startNode()).errorMsg();
			String emsg = nodeQuery.submit(localItem.endNode()).errorMsg();
			if (smsg!=null || emsg!=null)
				errorMsg ="Expected '"+localItem.startNode().toShortString()+"' and '"+localItem.endNode() +". to satisfy query but found ["+smsg+"] and ["+emsg+"].";
			break;
		}
		return this;
	}

}
