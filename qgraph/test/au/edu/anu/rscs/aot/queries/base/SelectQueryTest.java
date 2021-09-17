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
package au.edu.anu.rscs.aot.queries.base;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.base.SelectQuery;
import au.edu.anu.rscs.aot.queries.base.SequenceQuery;

/**
 * 
 * @author Yao Wang - 11/9/2012
 *
 */
class SelectQueryTest {

	DynamicList<String> twi = new DynamicList<String>("test", "bill", "john", "tim", "xxx");
	@Test
	public void testSelectZeroOrOne()
	{
		try 
		{
			SelectQuery c1 = selectZeroOrOne(startsWith("b"));
			System.out.println("query: " + c1);
//			c1.check(twi);
			System.out.println("  result: " + c1);
		} 
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		
		try
		{
			SelectQuery c2 = selectZeroOrOne(startsWith("t"));
			System.out.println("query: " + c2);
//			c2.check(twi);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}
	@Test
	public void testSelectZeroOrMany()
	{
		try 
		{
			SelectQuery c1 = selectZeroOrMany(startsWith("b"));
			System.out.println("query: " + c1);
//			c1.check(twi);
			System.out.println("  result: " + c1);
			SelectQuery c2 = selectZeroOrMany(startsWith("Z"));
			System.out.println("query: " + c2);
//			c2.check(twi);
			System.out.println("  result: " + c2);
		} 
		catch (Exception e) 
		{
			fail("should not throw an exception ");
		}
	}
	
	@Test
	public void testSelectOne()
	{
		try 
		{
			SelectQuery c1 = selectOne(startsWith("x"));
			System.out.println("query: " + c1);
//			c1.check(twi);
			System.out.println("  result: " + c1);
			
		} 
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		
		try
		{
			SelectQuery c2 = selectOne(startsWith("t"));
			System.out.println("query: " + c2);
//			c2.check(twi);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}
	
	@Test
	public void testSelectOneOrMany()
	{
		try 
		{
			SelectQuery c1 = selectOneOrMany(startsWith("b"));
			System.out.println("query: " + c1);
//			c1.check(twi);
			System.out.println("  result: " + c1);
			
		} 
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		try
		{
			SelectQuery c2 = selectOneOrMany(startsWith("Z"));
			System.out.println("query: " + c2);
//			c2.check(twi);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}
	@Test
	public void testExclusive()
	{
//		SelectQuery eq = selectZeroOrMany(new SequenceQuery(length(), hasCount(4))).exclusive();
//		System.out.println("query: " + eq);
		
		DynamicList<String> strList1 = new DynamicList<String>("test", "bill", "john");
//		eq.check(strList1);
//		System.out.println("  result: " + eq);
		try
		{
			DynamicList<String> strList2 = new DynamicList<String>("test", "bill", "john", "tim", "xxx");
//			eq.check(strList2);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}

}
