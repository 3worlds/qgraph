package au.edu.anu.rscs.aot.queries;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

import org.junit.jupiter.api.Test;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.util.IntegerRange;

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
		// isDoubleString()
		// isDoubleString() and isFloatString() can be replaced by isRealString
		{
			Queryable q = isDoubleString();
			q.submit("0.1");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isDoubleString();
			q.submit("0.1f");// parser accepts f,F,d,D
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isDoubleString();
			q.submit("2");
			assertTrue(q.satisfied());
		}
		{
			Queryable q = isDoubleString();
			q.submit("2L");
			assertTrue(!q.satisfied());
		}

	}

}
