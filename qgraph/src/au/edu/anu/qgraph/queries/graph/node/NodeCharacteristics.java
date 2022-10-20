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

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import fr.cnrs.iees.omugi.graph.Node;

/**
 * <p>Check if a {@link Node} is a leaf or root node.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Node}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input {@code Node} is not a leaf/root as requested in the constructor</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#isLeaf() CoreQueries.isLeaf()
 * @see au.edu.anu.qgraph.queries.CoreQueries#isRoot() CoreQueries.isRoot()
 * 
 * @author Shayne Flint - 2/4/2012
 *
 */
public class NodeCharacteristics extends QueryAdaptor {

	public enum RootLeaf {
		ROOT, LEAF;
	}

	private RootLeaf mode;

	/**
	 * 
	 * @param mode {@code RootLeaf.ROOT} to check if node is root or {@code RootLeaf.LEAF} to
	 * check if node is leaf
	 */
	public NodeCharacteristics(RootLeaf mode) {
		this.mode = mode;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Node localItem = (Node)input;
		boolean ok = true;
		switch (mode) {
		case ROOT:
			if (!localItem.isRoot())
				ok = false;
			break;
		case LEAF:
			if (!localItem.isLeaf())
				ok = false;
			break;
		}
		if (!ok)
			errorMsg = "Expected '"+localItem.toShortString()+"' to be "+mode+" node.";
		return this;
	}
	

}
