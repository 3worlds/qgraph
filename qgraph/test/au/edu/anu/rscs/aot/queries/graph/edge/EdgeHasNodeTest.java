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

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.graph.Direction;
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
class EdgeHasNodeTest {

	private Node n1, n2, n3;
	private List<Node> nl = new LinkedList<>();
	private ALGraphFactory gf = new ALGraphFactory("zozo");

	@BeforeEach
	private void init() {
		n1 = gf.makeNode();
		n2 = gf.makeNode();
		n3 = gf.makeNode();
		gf.makeEdge(n1, n2);
		gf.makeEdge(n1, n2);
		gf.makeEdge(n1, n3);
		gf.makeEdge(n1, n3);
		nl.add(n1);
		nl.add(n2);
		nl.add(n3);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testHasEndNode() {
		{
			Collection<Edge> edges = (Collection<Edge>) get(n1.edges(Direction.OUT),
					selectZeroOrMany(hasEndNode(isNode(n2))));
			assertTrue(edges.size() == 2);
		}
		{
			Collection<Edge> edges = (Collection<Edge>) get(n1.edges(Direction.OUT),
					selectZeroOrMany(hasEndNode(isNode(n3))));
			assertTrue(edges.size() == 2);
		}
	}

	@Test
	void testHasStartNode() {
		{
			Collection<Edge> edges = (Collection<Edge>) get(n2.edges(Direction.IN),
					selectZeroOrMany(hasStartNode(isNode(n1))));
			assertTrue(edges.size() == 2);
		}
		{
			Collection<Edge> edges = (Collection<Edge>) get(n3.edges(Direction.IN),
					selectZeroOrMany(hasStartNode(isNode(n1))));
			assertTrue(edges.size() == 2);
		}
	}

	@Test
	void testHasOtherNode() {
		{
			Collection<Edge> edges = (Collection<Edge>) get(n2.edges(), selectZeroOrMany(hasOtherNode(isNode(n1), n2)));
			assertTrue(edges.size() == 2);
		}
		{
			Collection<Edge> edges = (Collection<Edge>) get(n3.edges(Direction.IN),
					selectZeroOrMany(hasOtherNode(isNode(n1), n3)));
			assertTrue(edges.size() == 2);
		}
	}

	@Test
	void testHasBothNodes() {
		{
			gf.makeEdge(n1, n1);
			Collection<Edge> edges = (Collection<Edge>) get(n1.edges(Direction.IN),
					selectZeroOrMany(hasBothNodes(isNode(n1))));
			assertTrue(edges.size() == 1);
		}
		{
			Collection<Edge> edges = (Collection<Edge>) get(n3.edges(Direction.IN),
					selectZeroOrMany(hasBothNodes(isNode(n1))));
			assertTrue(edges.size() == 0);
		}
	}

}
