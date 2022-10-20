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
package au.edu.anu.qgraph.queries.base;

import static au.edu.anu.qgraph.queries.CoreQueries.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.qgraph.queries.base.PopQuery;
import au.edu.anu.qgraph.queries.base.SequenceQuery;

/**
 * 
 * @author Yao Wang - 11/9/2012
 *
 */
class SequenceQueryTest {
	@Test
	public void testStartWith() {
		SequenceQuery q = new SequenceQuery(startsWith("He"), startsWith("Hell"));
		q.submit("Hello There");
		assertTrue(q.satisfied());
	}

	@Test
	public void testEndWith() {
		SequenceQuery q = new SequenceQuery(startsWith("He"), startsWith("Hell"));
		q.submit("Hello There");
		assertTrue(q.satisfied());
	}

	@Test
	public void testContainsSubstring() {
		SequenceQuery q = new SequenceQuery(startsWith("He"), endsWith("ere"), containsSubstring("ello"));
		q.submit("Hello There");
		assertTrue(q.satisfied());
	}

	@Test
	public void testLength() {
		{
			SequenceQuery q = new SequenceQuery(startsWith("He"), endsWith("ere"), containsSubstring("ello"), length(),
					integerInRange(1, 11));
			q.submit("Hello There");
			assertTrue(q.satisfied());
		}
		{
			SequenceQuery q = new SequenceQuery(startsWith("He"), endsWith("ere"), containsSubstring("ello"), length(),
					integerInRange(1, 10));
			q.submit("Hello There");
			assertTrue(!q.satisfied());
		}
	}

	@Test
	public void testPop() {
		{
			SequenceQuery q = new SequenceQuery(startsWith("He"), endsWith("ere"), containsSubstring("ello"), length(),
					PopQuery.pop(1), integerInRange(1, 10));
			q.submit("Hello There");
			assertTrue(!q.satisfied());
		}
		{
			SequenceQuery q = new SequenceQuery(startsWith("He"), endsWith("ere"), containsSubstring("ello"), length(),
					PopQuery.pop(1));
			q.submit("Hello There");
			assertTrue(q.satisfied());
		}

	}

	@Test
	public void testIntegerInRange() {
		SequenceQuery q = new SequenceQuery(integerInRange(1, 20));
		q.submit(1);
		assertTrue(q.satisfied());
	}

	@Test
	public void testFloatInRange() {
		SequenceQuery q = new SequenceQuery(floatInRange(1f, 20f));
		q.submit(1.2f);
		assertTrue(q.satisfied());
	}

	@Test
	public void testLongInRange() {
		SequenceQuery q = new SequenceQuery(longInRange(1l, 20l));
		q.submit(10l);
		assertTrue(q.satisfied());
	}

	@Test
	public void testIsDouble() {
		SequenceQuery q = new SequenceQuery(doubleInRange(1, 20));
		q.submit(1.2);
		assertTrue(q.satisfied());
	}
}
