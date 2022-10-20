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

import au.edu.anu.qgraph.queries.*;
import fr.cnrs.iees.omugi.graph.*;

/**
 * <p>Check that the tip {@link Node}s of an {@link Edge} ends satisfy their respective
 * queries and that they have a specific {@link Node#classId() classId()}; also checks
 * the {@link Edge#classId() classId()}.</p>
 * <p>A bit old-fashioned (over-specialized legacy code, I mean).</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Edge}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input start node does not satisfy startQuery</li>
 * <li>input end node does not satisfy endQuery</li>
 * <li>input class id does not match the (optional) label passed to the constructor</li>
 * <li>input start node class id does not match the (optional) startLabel passed to the constructor</li>
 * <li>input end node class id does not match the (optional) endLabel passed to the constructor</li>
 * </ol></dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#isEdge(Queryable, Queryable)
 * @see au.edu.anu.qgraph.queries.CoreQueries#isEdge(Queryable, Queryable, String)
 * @see au.edu.anu.qgraph.queries.CoreQueries#isEdge(Queryable, Queryable, String, String, String)
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeQuery extends QueryAdaptor {

	private Queryable startNodeQuery;
	private Queryable endNodeQuery;

	private String startLabel;
	private String label;
	private String endLabel;

	/**
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 * @param startLabel a class identifier the start node must match
	 * @param label a class identifier the edge must match
	 * @param endLabel a class identifier the end node must match
	 */
	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery, String startLabel, String label,
			String endLabel) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = startLabel;
		this.label = label;
		this.endLabel = endLabel;
	}

	/**
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 * @param label a class identifier the edge must match
	 */
	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery, String label) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = label;
		this.endLabel = null;
	}

	/**
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 */
	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = null;
		this.endLabel = null;
	}

	// this code must be brittle - it assumes an Edge is also Labelled...
	// that's ok, an edge has a classId
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Edge localItem = (Edge) input;
		Node startNode = localItem.startNode();
		Node endNode = localItem.endNode();

		boolean ok = true;
		ok = ok && startNodeQuery.submit(startNode).satisfied();
		ok = ok && endNodeQuery.submit(endNode).satisfied();

		if (startLabel != null)
			ok = ok && startNode.classId().equals(startLabel);
		if (label != null)
			ok = ok && localItem.classId().equals(label);
		if (endLabel != null)
			ok = ok && endNode.classId().equals(endLabel);
		if (!ok)
			errorMsg = formatErrorMsg();
		return this;
	}

	private String formatErrorMsg() {
		String result = startNodeQuery.errorMsg();
		if (startLabel != null)
			result = result + "-" + startLabel;
		else
			result = result + "--";
		if (label != null)
			result = result + "-" + label;
		else
			result = result + "--";
		if (endLabel != null)
			result = result + "-" + endLabel;
		else
			result = result + "--";
		result = result + "-" + endNodeQuery.errorMsg();
		return result;
	}

}
