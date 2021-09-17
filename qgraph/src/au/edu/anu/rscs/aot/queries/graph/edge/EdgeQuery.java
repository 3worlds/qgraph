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

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

/**
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

	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery, String startLabel, String label,
			String endLabel) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = startLabel;
		this.label = label;
		this.endLabel = endLabel;
	}

	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery, String label) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = label;
		this.endLabel = null;
	}

	public EdgeQuery(Queryable startNodeQuery, Queryable endNodeQuery) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = null;
		this.endLabel = null;
	}

	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery, String startLabel, String label,
			String endLabel) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, startLabel, label, endLabel);
	}

	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery, String label) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, label);
	}

	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery) {
		return new EdgeQuery(startNodeQuery, endNodeQuery);
	}

//	@Override
//	public Query process(Object item) {
//		defaultProcess(item);
//		Edge localItem = (Edge) item;
//		Node startNode = localItem.startNode();
//		Node endNode = localItem.endNode();
//
//		startNodeQuery.process(startNode);
//		endNodeQuery.process(endNode);
//
//		satisfied = true;
//		satisfied = satisfied && startNodeQuery.satisfied();
//		satisfied = satisfied && endNodeQuery.satisfied();
//
//		if (startLabel != null)
//			satisfied = satisfied && startNode.classId().equals(startLabel);
//		if (label != null)
//			satisfied = satisfied && localItem.classId().equals(label);
//		if (endLabel != null)
//			satisfied = satisfied && endNode.classId().equals(endLabel);
//
//		return this;
//	}

	// this code must be brittle - it assumes an Edge is also Labelled...
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

	public String formatErrorMsg() {
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
