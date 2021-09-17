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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.graph.Graph;
import fr.cnrs.iees.graph.Node;
import fr.cnrs.iees.graph.impl.ALEdge;
import fr.cnrs.iees.graph.impl.ALGraph;
import fr.cnrs.iees.graph.impl.ALGraphFactory;
import fr.cnrs.iees.graph.impl.ALNode;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018)
 *
 */
class EdgeListNodesTest {

	private Node n1,n2,n3,n4;
	@SuppressWarnings("unused")
	private Graph<ALNode,ALEdge> g;
	
	@BeforeEach
	private void init() {
		ALGraphFactory gf = new ALGraphFactory("brouf");
		g = new ALGraph<ALNode,ALEdge>(gf);
		n1 = gf.makeNode();
		n2 = gf.makeNode();
		n3 = gf.makeNode();
		n4 = gf.makeNode();
		gf.makeEdge(n1,n2);
		gf.makeEdge(n1,n3);
		gf.makeEdge(n1,n4);
		gf.makeEdge(n2,n3);
	}
	
	@Test
	void testStartNodes() {
//		List<Node> nodeList = (List<Node>)EdgeListNodes
//			.startNodes().process(g.edges()).getResult();
//		assertTrue(nodeList.size() == 2);
//		assertTrue(nodeList.contains(n1));
//		assertTrue(nodeList.contains(n2));
	}

	@Test
	void testEndNodes() {
//		List<Node> nodeList = (List<Node>)EdgeListNodes
//			.endNodes().process(g.edges()).getResult();
//		assertTrue(nodeList.size() == 3);
//		assertTrue(nodeList.contains(n2));
//		assertTrue(nodeList.contains(n3));
//		assertTrue(nodeList.contains(n4));
	}

	@Test
	void testOtherNodes() {
//		List<Node> nodeList = (List<Node>)EdgeListNodes
//			.otherNodes(n3).process(g.edges()).getResult();
//		assertTrue(nodeList.size() == 2);
//		assertTrue(nodeList.contains(n1));
//		assertTrue(nodeList.contains(n2));
	}

	@Test
	void testBothNodes() {
//		List<Node> nodeList = (List<Node>)EdgeListNodes
//			.bothNodes().process(g.edges()).getResult();
//		assertTrue(nodeList.size() == 4);
//		assertTrue(nodeList.contains(n1));
//		assertTrue(nodeList.contains(n2));
//		assertTrue(nodeList.contains(n3));
//		assertTrue(nodeList.contains(n4));
	}

}
