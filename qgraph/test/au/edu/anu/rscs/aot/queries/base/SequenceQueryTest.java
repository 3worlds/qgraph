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

import static au.edu.anu.rscs.aot.old.queries.CoreQueries.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.old.queries.base.SequenceQuery;

/**
 * 
 * @author Yao Wang - 11/9/2012
 *
 */
class SequenceQueryTest {
	@Test
	public void testStartWith()
	{
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),startsWith("Hell")); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testEndWith()
	{
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),startsWith("Hell"),endsWith("ere")); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testContainsSubstring()
	{
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),startsWith("Hell"),endsWith("ere"),containsSubstring("ello")); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testLength()
	{
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),
					startsWith("Hell"),
					endsWith("ere"),
					containsSubstring("ello"),
					length()); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	
	@Test
	public void testPop()
	{
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),
					startsWith("Hell"),
					endsWith("ere"),
					containsSubstring("ello"),
					length(),
					pop(1),startsWith("H")); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),
					startsWith("Hell"),
					endsWith("ere"),
					containsSubstring("ello"),
					length(),
					pop(5),startsWith("H")); 
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
		try
		{
			SequenceQuery.check("Hello There", 
					startsWith("He"),
					startsWith("Hell"),
					endsWith("ere"),
					containsSubstring("ello"),
					length(),
					pop(6),startsWith("H")); 
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
		
	}
	@Test
	public void testIntegerInRange()
	{
		try
		{
			SequenceQuery.check(1,integerInRange(1, 20));
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testFloatInRange()
	{
		try
		{
			SequenceQuery.check(1.2f,floatInRange(1f, 20f));
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testLongInRange()
	{
		try
		{
			SequenceQuery.check(10l, longInRange(1l, 20l));
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
	@Test
	public void testIsDouble()
	{
		try
		{
			SequenceQuery.check(1.2,doubleInRange((double)1, (double)20));
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception " + e);
		}
	}
}
