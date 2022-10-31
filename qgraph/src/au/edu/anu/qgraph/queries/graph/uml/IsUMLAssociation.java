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
package au.edu.anu.qgraph.queries.graph.uml;

import static au.edu.anu.qgraph.queries.CoreQueries.*;

import au.edu.anu.qgraph.queries.CoreQueries;
import au.edu.anu.qgraph.queries.Queryable;
import au.edu.anu.qgraph.queries.base.AndQuery;
import au.edu.anu.qgraph.queries.base.SequenceQuery;
import au.edu.anu.qgraph.queries.graph.node.HasEdges;
import fr.cnrs.iees.omugi.graph.Direction;

/**
 * <p>
 * Check if an object is an UML association.
 * </p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Object}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input is not an {@link fr.cnrs.iees.omugi.graph.Element Element} with
 * class id "association" ... and other complicated things (TODO: finish
 * this)</dd>
 * </dl>
 * 
 * <p>
 * Note: implemented as an AndQuery.
 * </p>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#isAssociation()
 *      CoreQueries.isAssociation()
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsUMLAssociation extends AndQuery {

	/**
	 * Parameterless constructor.
	 */
	public IsUMLAssociation() {
		Queryable edgeQuery = new SequenceQuery(hasProperty("phrase"),
				hasProperty("multiplicity", isEnum(Multiplicity.values())));
		addQuery(hasTheLabel("association"),
				new HasEdges(CoreQueries.isUMLClass(), Direction.OUT, Multiplicity.ONE).withLabel("passive")
						.withEdgeQuery(edgeQuery),
				new HasEdges(CoreQueries.isUMLClass(), Direction.OUT, Multiplicity.ONE).withLabel("active")
						.withEdgeQuery(edgeQuery),
				new HasEdges(CoreQueries.isUMLClass(), Direction.OUT, Multiplicity.ZERO_ONE)
						.withLabel("associativeClass"));
	}

}
