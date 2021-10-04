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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;
import fr.cnrs.iees.graph.impl.ALGraphFactory;
import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018)
 *
 */
class NodeListEdgesTest {

	private Node n1, n2, n3, n4;
	private Edge e1, e2, e3, e4;
	private List<Node> nl = new LinkedList<>();

	@BeforeEach
	private void init() {
		ALGraphFactory gf = new ALGraphFactory("grünt");
		n1 = gf.makeNode();
		n2 = gf.makeNode();
		n3 = gf.makeNode();
		n4 = gf.makeNode();
		e1 = gf.makeEdge(n1, n2);
		e2 = gf.makeEdge(n1, n3);
		e3 = gf.makeEdge(n1, n4);
		e4 = gf.makeEdge(n2, n3);
		nl.add(n1);
		nl.add(n2);
		nl.add(n3);
		nl.add(n4);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testInEdges() {
		Collection<Edge> edgeList = (Collection<Edge>) get(nl, nodeListInEdges());
		assertTrue(edgeList.size() == 4);
		assertTrue(edgeList.contains(e1));
		assertTrue(edgeList.contains(e2));
		assertTrue(edgeList.contains(e3));
		assertTrue(edgeList.contains(e4));
	}

	@SuppressWarnings("unchecked")
	@Test
	void testOutEdges() {
		Collection<Edge> edgeList = (Collection<Edge>) get(nl, nodeListOutEdges());
		assertTrue(edgeList.size() == 4);
		assertTrue(edgeList.contains(e1));
		assertTrue(edgeList.contains(e2));
		assertTrue(edgeList.contains(e3));
		assertTrue(edgeList.contains(e4));
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testEdges() {
		Collection<Edge> edgeList = (Collection<Edge>) get(nl, nodeListEdges());
		assertTrue(edgeList.size() == 4);
		assertTrue(edgeList.contains(e1));
		assertTrue(edgeList.contains(e2));
		assertTrue(edgeList.contains(e3));
		assertTrue(edgeList.contains(e4));
	}

}
