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
package au.edu.anu.rscs.aot.queries.graph.element;

import org.junit.Test;

import au.edu.anu.rscs.aot.queries.graph.element.ElementProperty;
import fr.cnrs.iees.graph.DataNode;
import fr.cnrs.iees.graph.impl.DefaultGraphFactory;
import fr.cnrs.iees.properties.SimplePropertyList;
import fr.cnrs.iees.properties.impl.SimplePropertyListImpl;
import junit.framework.TestCase;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018)
 *
 */
public class ElementPropertyTest extends TestCase 
{
	@Test
	public void testHasProperty()
	{
		DefaultGraphFactory gf = new DefaultGraphFactory();
		SimplePropertyList props = new SimplePropertyListImpl("p1");
		props.setProperty("p1", 1234);
		DataNode n = (DataNode) gf.makeNode(props);
		try
		{
			ElementProperty ep = ElementProperty.hasProperty("p1");
			ep.check(n);
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		
		ElementProperty ep2 =null;
		try
		{
			ep2= ElementProperty.hasProperty("p1", 1234);
			ep2.check(n);
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		try
		{
			ep2 = ElementProperty.hasProperty("p1", 12345);
			ep2.check(n);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}
}
