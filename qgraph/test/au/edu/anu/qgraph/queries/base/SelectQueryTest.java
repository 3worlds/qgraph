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

import au.edu.anu.omhtk.collections.DynamicList;

/**
 * 
 * @author Yao Wang - 11/9/2012
 *
 */
class SelectQueryTest {

	DynamicList<String> twi = new DynamicList<String>("test", "bert", "jenny", "tanya", "xerces");

	@Test
	public void testSelectZeroOrOne() {
		{
			SelectQuery q = selectZeroOrOne(startsWith("b"));
			q.submit(twi);
			assertTrue(q.satisfied());
		}

		{
			SelectQuery q = selectZeroOrOne(startsWith("t"));
			q.submit(twi);
			assertTrue(!q.satisfied());
		}
	}

	@Test
	public void testSelectZeroOrMany() {

		{
			SelectQuery q = selectZeroOrMany(startsWith("b"));
			q.submit(twi);
			assertTrue(q.satisfied());
		}

		{
			SelectQuery q = selectZeroOrMany(startsWith("Z"));
			q.submit(twi);
			assertTrue(q.satisfied());
		}
	}

	@Test
	public void testSelectOne() {
		{
			SelectQuery q = selectOne(startsWith("x"));
			q.submit(twi);
			assertTrue(q.satisfied());
		}

		{
			SelectQuery q = selectOne(startsWith("t"));
			q.submit(twi);
			assertTrue(!q.satisfied());
		}
	}

	@Test
	public void testSelectOneOrMany() {

		{
			SelectQuery q = selectOneOrMany(startsWith("b"));
			q.submit(twi);
			assertTrue(q.satisfied());
		}

		{
			SelectQuery q = selectOneOrMany(startsWith("Z"));
			q.submit(twi);
			assertTrue(!q.satisfied());
		}
	}

	@Test
	public void testExclusive() {
		SelectQuery q = selectZeroOrMany(new SequenceQuery(length(), hasCount(4))).exclusive();

		{
			DynamicList<String> lst = new DynamicList<String>("test", "bert", "jenn");
			q.submit(lst);
			assertTrue(q.satisfied());
		}
		{
			DynamicList<String> lst = new DynamicList<String>("test", "bert", "jenny", "tanya", "xerces");
			q.submit(lst);
			assertTrue(!q.satisfied());
		}
	}

}
