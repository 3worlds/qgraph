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
package au.edu.anu.qgraph.queries.graph.element;

import static au.edu.anu.qgraph.queries.CoreQueries.*;

import org.junit.Test;

import au.edu.anu.qgraph.queries.Queryable;
import fr.cnrs.iees.properties.SimplePropertyList;
import fr.cnrs.iees.properties.impl.SimplePropertyListImpl;
import junit.framework.TestCase;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018 refactored ID 2021)
 *
 */
public class ElementPropertyTest extends TestCase {
	@Test
	public void testHasProperty() {
		SimplePropertyList props = new SimplePropertyListImpl("p1");
		props.setProperty("p1", 1234);
		{
			Queryable q = hasProperty("p1");
			q.submit(props);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = hasProperty("p1", 1234);
			q.submit(props);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = hasProperty("p1", 12345);
			q.submit(props);
			assertTrue(!q.satisfied());
		}
		// TODO
	}
}
