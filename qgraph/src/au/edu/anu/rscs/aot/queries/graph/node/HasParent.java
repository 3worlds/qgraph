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

import fr.cnrs.iees.graph.TreeNode;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.get;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;


/**
 * <p>Check if a {@link TreeNode} has a parent that satisfies an (optional) query.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link TreeNode}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>no query was passed to the constructor and input has no parent</li>
 * <li>input has a parent that does not satisfy que query passed to the constructor</li>
 * </ol></dd> 
 * </dl>
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#hasParent() CoreQueries.hasParent()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#hasParent(Queryable) CoreQueries.hasParent(Queryable)
 * 
 * @author Shayne Flint - 2/4/2012
 */
public class HasParent extends QueryAdaptor {

	private Queryable query;

	/**
	 * 
	 * @param query a query to be satisfied by the parent of the input argument to {@code submit(...)}
	 */
	public HasParent(Queryable query) {
		this.query = query;
	}

	@SuppressWarnings("unused")
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		TreeNode localItem = (TreeNode) input;
		if (query==null) {
			if (localItem.getParent()==null) {
				errorMsg = "Expected parent but found none.";
			}				
		} else {
			TreeNode parent = (TreeNode) get(localItem, parent(query));
			if (!query.satisfied())
				errorMsg = "Expected parent that satisfies but found "+query.errorMsg()+"'.";			
		}
		return this;
	}


}
