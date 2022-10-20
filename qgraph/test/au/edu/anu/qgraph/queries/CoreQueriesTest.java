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
package au.edu.anu.qgraph.queries;

import static au.edu.anu.qgraph.queries.CoreQueries.*;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;

import au.edu.anu.omhtk.collections.DynamicList;
import au.edu.anu.omhtk.util.IntegerRange;
import fr.cnrs.iees.omugi.graph.Direction;
import fr.cnrs.iees.omugi.io.GraphFileFormats;

class CoreQueriesTest {
	public static void show(Queryable q) {
		System.out.println(q.errorMsg());
	}

	@Test
	void test() {
		// andQuery
		//
		{
			Queryable q = andQuery(startsWith("Hel"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = andQuery(startsWith("Hel"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = andQuery(startsWith("xx"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = andQuery(startsWith("xx"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		// orQuery
		//
		{
			Queryable q = orQuery(startsWith("Hel"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = orQuery(startsWith("Hel"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = orQuery(startsWith("xx"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = orQuery(startsWith("xx"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		// notQuery
		//
		{
			Queryable q = notQuery(startsWith("Hel"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = notQuery(startsWith("xx"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = notQuery(startsWith("Hel"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = notQuery(startsWith("xx"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		// xorQuery
		//
		{
			Queryable q = xorQuery(startsWith("Hel"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = xorQuery(startsWith("xx"), endsWith("lo"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = xorQuery(startsWith("Hel"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = xorQuery(startsWith("xx"), endsWith("xx"));
			q.submit("Hello");
			assertTrue(!q.satisfied());
		}

		// ForAllQuery
		//
		{
			DynamicList<String> lst = new DynamicList<String>("ab", "ac");
			Queryable q = forAll(startsWith("a"));
			q.submit(lst);
			assertTrue(q.satisfied());

			lst.add("xg");
			q.submit(lst);
			assertTrue(!q.satisfied());
		}
		// ifThenQuery (else)
		//
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("c"), endsWith("z"));
			q.submit("abc");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("c"), endsWith("z"));
			q.submit("xyz");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("z"), endsWith("c"));
			q.submit("abc");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("z"), endsWith("c"));
			q.submit("xyz");
			assertTrue(!q.satisfied());
		}
		// ifThenQuery
		//
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("c"));
			q.submit("abc");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("c"));
			q.submit("abz");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = ifThenQuery(startsWith("a"), endsWith("c"));
			q.submit("xbc");
			assertTrue(q.satisfied());
		}
		// isInstanceOf(Class<?> theClass)
		//
		{
			Queryable q = isInstanceOf(String.class);
			q.submit("abc");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isInstanceOf(String.class);
			q.submit(123);
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = isInstanceOf(String.class.getName());
			q.submit("abc");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isInstanceOf(String.class.getName());
			q.submit(123);
			assertTrue(!q.satisfied());
		}
		// length
		//
		{
			Queryable q = length();
			q.submit("abc");
			assertTrue((Integer) q.result() == 3);
		}
		// isBoolean
		//
		{
			Queryable q = isBoolean();
			q.submit(true);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isBoolean();
			q.submit(0);
			assertTrue(!q.satisfied());
			// System.out.println(q.errorMsg());
		}
		// isDate
		//
		{
			Queryable q = isDate();
			q.submit(new Date());
			assertTrue(q.satisfied());
		}
		{
			Queryable q = dateInRange(new Date(0), new Date(5));
			q.submit(new Date(6));
			assertTrue(!q.satisfied());
		}
		// isDouble
		//
		{
			Queryable q = isDouble();
			q.submit(1.0);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isDouble();
			q.submit(1.0f);
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = doubleInRange(0.0, 1.0);
			q.submit(0.5);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = doubleInRange(0.0, 1.0);
			q.submit(-0.0);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = doubleInRange(0.0, 1.0);
			q.submit(-0.00001);
			assertTrue(!q.satisfied());
//			show(q);
		}
		// isFloat
		//
		{
			Queryable q = isFloat();
			q.submit(1.0f);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isFloat();
			q.submit(1.0d);
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = floatInRange(0.0f, 1.0f);
			q.submit(0.5f);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = floatInRange(0.0f, 1.0f);
			q.submit(-0.0f);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = floatInRange(0.0f, 1.0f);
			q.submit(-0.00001f);
			assertTrue(!q.satisfied());
//			show(q);
		}
		// isInteger
		//
		{
			Queryable q = isInteger();
			q.submit(123);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isInteger();
			q.submit(1.0d);
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = integerInRange(0, 2);
			q.submit(3);
			assertTrue(!q.satisfied());
//			show(q);
		}
		{
			Queryable q = integerInRange(0, 2);
			q.submit(-0);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = integerInRange(0, 2);
			q.submit(-1);
			assertTrue(!q.satisfied());
//			show(q);
		}
		// isLong
		//
		{
			Queryable q = isLong();
			q.submit(123L);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isLong();
			q.submit(1.0d);
			assertTrue(!q.satisfied());
//			show(q);
		}
		{
			Queryable q = longInRange(0, 2);
			q.submit(3);
			assertTrue(!q.satisfied());
//			show(q);
		}
		{
			Queryable q = longInRange(0, 2);
			q.submit(-0L);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = longInRange(0, 2);
			q.submit(-1);
			assertTrue(!q.satisfied());
//			show(q);
		}
		// isString
		//
		{
			Queryable q = isString();
			q.submit("123");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isString();
			q.submit(123);
			assertTrue(!q.satisfied());
//			show(q);
		}
		{
			Queryable q = stringOfLength(0, 3);
			q.submit("123");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = stringOfLength(0, 3);
			q.submit("12");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = stringOfLength(0, 3);
			q.submit("1234");
			assertTrue(!q.satisfied());
//			show(q);
		}
		// IntegerRange
		//
		{
			Queryable q = isIntegerRange();
			q.submit(new IntegerRange(0, 1));
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isIntegerRange();
			q.submit("abc");
			assertTrue(!q.satisfied());
//			show(q);
		}
		// isClass
		//
		{
			Queryable q = isClass(Integer.class, Double.class);
			q.submit(1);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isClass(Integer.class, Double.class);
			q.submit("1");
			assertTrue(!q.satisfied());
//			show(q);
		}
		{
			Queryable q = isClass(Integer.class.getName(), Double.class.getName());
			q.submit(1);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isClass(Integer.class.getName(), Double.class.getName());
			q.submit("1");
			assertTrue(!q.satisfied());
//			show(q);
		}

		// isStringList
		//
		{
			Queryable q = isStringList();
			List<String> slst = new ArrayList<>();
			List<Integer> ilst = new ArrayList<>();
			ilst.add(1);
			q.submit("abc");
			assertTrue(!q.satisfied());
//			show(q);
			q.submit(slst);
			assertTrue(!q.satisfied());
//			show(q);
			q.submit(ilst);
			assertTrue(!q.satisfied());
//			show(q);
			slst.add("abc");
			q.submit(slst);
			assertTrue(q.satisfied());
		}
		// classIsClass
		{
			Queryable q = classIsClass(Integer.class);
			q.submit(Integer.class.getName());
//			show(q);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = classIsClass(Double.class);
			q.submit(Integer.class.getName());
//			show(q);
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = stringIsClass(Integer.class.getName());
			q.submit(Integer.class.getName());
//			show(q);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = stringIsClass(Double.class.getName());
			q.submit(Integer.class.getName());
//			show(q);
			assertTrue(!q.satisfied());
		}
		// isDateString()
		//
		{
			Queryable q = isDateString();
			String s = "2018-05-05";
			q.submit(s);
//			show(q);
			assertTrue(q.satisfied());
			s = "2018/05/05";
			q.submit(s);
			assertTrue(!q.satisfied());
//			show(q);

		}
		// iRealString()
		//
		{
			Queryable q = isRealString();
			q.submit("0.1");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isRealString();
			q.submit("0.1f");// parser accepts f,F,d,D
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isRealString();
			q.submit("2");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isRealString();
			q.submit("2L");
			assertTrue(!q.satisfied());
		}
		// isIntegerString
		//
		{
			Queryable q = isIntegerString();
			q.submit("2");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isIntegerString();
			q.submit("2.0");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = isIntegerString();
			String s = Integer.toString(Integer.MAX_VALUE);
			q.submit(s);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isIntegerString();
			String s = Long.toString(Long.MAX_VALUE);
			q.submit(s);
			assertTrue(!q.satisfied());
		}
		// isLongString()
		//
		{
			Queryable q = isLongString();
			q.submit("2");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isLongString();
			String s = Long.toString(Long.MAX_VALUE);
			q.submit(s);
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isLongString();
			q.submit("2.0");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = isLongString();
			String s = Long.toString(Long.MAX_VALUE) + "1";
			q.submit(s);
//			show(q);
			assertTrue(!q.satisfied());
		}
		// isEnumString
		//
		{
			Queryable q = isEnum(Direction.values());
			q.submit(Direction.OUT.name());
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isEnumStrings(Direction.IN.name(), Direction.OUT.name());
			q.submit(Direction.OUT.name());
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isEnumStrings(Direction.IN.name(), Direction.OUT.name());
			q.submit(GraphFileFormats.XML.name());
			assertTrue(!q.satisfied());
		}
		// isFireName
		//
		{
			Queryable q = isFileName();
			File f = new File("");
			q.submit(f.getAbsolutePath());
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isFileName();
			File f = new File("rgsdfgsdfg");
			q.submit(f.getAbsolutePath());
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = isFileName();
			q.submit("sdiuapdiu");
			assertTrue(!q.satisfied());
		}
		{
			Queryable q = isFileName();
			try {
				// typecast error
				q.submit(1234);
			} catch (Exception e) {
				assertTrue(true);
			}
			// no error msg will have been set - its a programmer's error
			assertTrue(q.satisfied());
		}
		// InetAddressString
		//
		{
			Queryable q = isInetAddress();
			q.submit("172.217.167.67");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isInetAddress();
			q.submit("172.217");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isInetAddress();
			q.submit("172.217.167.677");
			assertTrue(!q.satisfied());
		}
		// matchesPattern
		//
		{
			// alphanumeric
			Queryable q = matchesPattern("([a-zA-Z0-9]*)?");
			q.submit("aBc1234");
			assertTrue(q.satisfied());
		}
		{
			// alphanumeric
			Queryable q = matchesPattern("([a-zA-Z0-9]*)?");
			q.submit("aBc )(&% 1234");
			assertTrue(!q.satisfied());
		}
		///// SIZE
		// inRange
		//
		{
			DynamicList<String> lst = new DynamicList<>();
			Queryable q = inRange(1,2);
			q.submit(lst);
			assertTrue(!q.satisfied());
			lst.add("a");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("b");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("c");
			q.submit(lst);
//			show(q);
			assertTrue(!q.satisfied());	
		}
		// hasMin
		//
		{
			DynamicList<String> lst = new DynamicList<>();
			Queryable q = hasMin(1);
			q.submit(lst);
//			show(q);
			assertTrue(!q.satisfied());
			lst.add("a");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("b");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("c");
			q.submit(lst);
			assertTrue(q.satisfied());	
		}
		// hasMax
		//
		{
			DynamicList<String> lst = new DynamicList<>();
			Queryable q = hasMax(2);
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("a");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("b");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("c");
			q.submit(lst);
			assertTrue(!q.satisfied());	
		}
		
		///// IntegerRange 
		//inRange
		//
		{
			DynamicList<String> lst = new DynamicList<>();
			IntegerRange r = new IntegerRange(1,2);
			Queryable q = inRange(r);
			q.submit(lst);
			assertTrue(!q.satisfied());
			lst.add("a");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("b");
			q.submit(lst);
			assertTrue(q.satisfied());
			lst.add("c");
			q.submit(lst);
			assertTrue(!q.satisfied());
		}
		///// Count queries (integer inputs)
		// hasMinCount
		{
			Queryable q = hasMinCount(1);
			q.submit(0);
			assertTrue(!q.satisfied());		
			q.submit(1);
			assertTrue(q.satisfied());
			q.submit(2);
			assertTrue(q.satisfied());
			q.submit(3);
			assertTrue(q.satisfied());
		}
		// hasMaxCount
		{
			Queryable q = hasMaxCount(2);
			q.submit(-1);
			assertTrue(q.satisfied());		
			q.submit(0);
			assertTrue(q.satisfied());
			q.submit(2);
			assertTrue(q.satisfied());
			q.submit(3);
			assertTrue(!q.satisfied());
		}
		// countInRange
		{
			Queryable q = countInRange(1,2);
			q.submit(-1);
			assertTrue(!q.satisfied());		
			q.submit(0);
			assertTrue(!q.satisfied());
			q.submit(2);
			assertTrue(q.satisfied());
			q.submit(3);
			assertTrue(!q.satisfied());
		}
		// hasCount
		{
			Queryable q = hasCount(2);
			q.submit(-1);
			assertTrue(!q.satisfied());		
			q.submit(0);
			assertTrue(!q.satisfied());
			q.submit(2);
			assertTrue(q.satisfied());
			q.submit(3);
			assertTrue(!q.satisfied());
		}

	}

}
