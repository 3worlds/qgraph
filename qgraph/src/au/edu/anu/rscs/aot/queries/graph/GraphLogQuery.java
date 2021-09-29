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
package au.edu.anu.rscs.aot.queries.graph;

import java.util.logging.Logger;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Node;
import fr.ens.biologie.generic.utils.Logging;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Graph;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
@Deprecated // I think this class is completely useless - JG 29/9/2021
public class GraphLogQuery extends QueryAdaptor {

	private static Logger log = Logging.getLogger(GraphLogQuery.class);

	private String prefix;

	public GraphLogQuery(String prefix) {
		this.prefix = prefix;
	}

	public static GraphLogQuery logGraph(String prefix) {
		return new GraphLogQuery(prefix);
	}

	public static GraphLogQuery logGraph() {
		return new GraphLogQuery("");
	}

//	@SuppressWarnings("unchecked")
//	@Override
//	public Query process(Object item) {
//		defaultProcess(item);
//		Graph<Node,Edge> localItem = (Graph<Node,Edge>)item;
//		log.fine(prefix + localItem.toString());
//		satisfied = true;
//		return this;
//	}
	@SuppressWarnings("unchecked")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Graph<Node, Edge> localItem = (Graph<Node, Edge>) input;
		log.fine(prefix + localItem.toString());
		return this;
	}

//	@Override
//	public String toString() {
//		return "[GraphLogQuery]";
//	}

}
