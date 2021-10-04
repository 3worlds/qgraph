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
package au.edu.anu.rscs.aot.queries.graph.node;

import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.cnrs.iees.graph.Node;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Check if a {@link Node} is found in the list of nodes passed to the constructor.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Node}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input {@code Node} is not found in the list passed to the constructor</dd>
 * </dl>
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#isOneOf(Node...) CoreQueries.isOneOf(...)
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
// TODO: rename this class, the name is to vague (ie could apply to many other things than Nodes
// actually could be generalized to look for any list of objects.
public class IsOneOf extends QueryAdaptor {

	private DynamicList<Node> nodeList;

	/**
	 * 
	 * @param nodes nodes to match
	 */
	public IsOneOf(Node... nodes) {
		add(nodes);
	}

	/**
	 * Add nodes to search for
	 * @param nodes nodes to match
	 */
	public void add(Node... nodes) {
		for (Node n : nodes)
			this.nodeList.add(n);
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node)input;
		if (!nodeList.contains(localItem))
			errorMsg = "Expected list to contain '"+localItem.toShortString()+"'.";			
		return this;
	}


}
