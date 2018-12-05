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

import static org.junit.jupiter.api.Assertions.*;
import static au.edu.anu.rscs.aot.queries.CoreQueries.isNode;
import static au.edu.anu.rscs.aot.queries.CoreQueries.selectZeroOrMany;
import static au.edu.anu.rscs.aot.queries.base.SequenceQuery.get;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import fr.cnrs.iees.graph.generic.Direction;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.GraphElementFactory;
import fr.cnrs.iees.graph.generic.Node;
import fr.cnrs.iees.graph.generic.impl.DefaultGraphFactory;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018)
 *
 */
class EdgeHasNodeTest {

	private Node n1,n2,n3;
	private List<Node> nl = new LinkedList<>();
	private GraphElementFactory gf = new DefaultGraphFactory();
	
	@BeforeEach
	private void init() {
		n1 = gf.makeNode();
		n2 = gf.makeNode();
		n3 = gf.makeNode();
		gf.makeEdge(n1,n2);
		gf.makeEdge(n1,n2);
		gf.makeEdge(n1,n3);
		gf.makeEdge(n1,n3);
		nl.add(n1); nl.add(n2); nl.add(n3);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testHasEndNode() {
		Iterable<Edge> edges = (Iterable<Edge>)get(n1.getEdges(Direction.OUT), 
				selectZeroOrMany(EdgeHasNode.hasEndNode(isNode(n2))));
		ArrayList<Edge> temp=new ArrayList<Edge>();
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==2);
		temp.clear();
		edges = (Iterable<Edge>)get(n1.getEdges(Direction.OUT), selectZeroOrMany(EdgeHasNode.hasEndNode(isNode(n3))));
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==2);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testHasStartNode() {
		Iterable<Edge> edges = (Iterable<Edge>)get(n2.getEdges(Direction.IN), selectZeroOrMany(EdgeHasNode.hasStartNode(isNode(n1))));
		ArrayList<Edge> temp=new ArrayList<Edge>();
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==2);
		temp.clear();
		edges = (Iterable<Edge>)get(n3.getEdges(Direction.IN), selectZeroOrMany(EdgeHasNode.hasStartNode(isNode(n1))));
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==2);
	}

	@Test
	void testHasOtherNode() {
		fail("Not yet implemented");
	}

	@SuppressWarnings("unchecked")
	@Test
	void testHasBothNodes() {
		gf.makeEdge(n1,n1);
		Iterable<Edge> edges = (Iterable<Edge>)get(n1.getEdges(Direction.IN), selectZeroOrMany(EdgeHasNode.hasBothNodes(isNode(n1))));
		ArrayList<Edge> temp=new ArrayList<Edge>();
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==1);

		temp.clear();
		edges = (Iterable<Edge>)get(n3.getEdges(Direction.IN), selectZeroOrMany(EdgeHasNode.hasBothNodes(isNode(n1))));
		for (Edge e : edges)
			temp.add(e);
		assertTrue(temp.size()==0);
	}

}
