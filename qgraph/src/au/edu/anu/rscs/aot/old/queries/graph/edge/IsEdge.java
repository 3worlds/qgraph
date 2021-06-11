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
package au.edu.anu.rscs.aot.old.queries.graph.edge;

import au.edu.anu.rscs.aot.old.queries.Query;
import fr.cnrs.iees.graph.Edge;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
@Deprecated
public class IsEdge extends Query {

	private Edge edge;
	
	public IsEdge(Edge edge) {
		this.edge = edge;
	}

	public static IsEdge isEdge(Edge edge) {
		return new IsEdge(edge);
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Edge localItem  = (Edge) item;
		satisfied = localItem.equals(edge);
		return this;
	}
	
	@Override
	public String toString() {
		return "[IsEdge]";
	}
	

}