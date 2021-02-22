package au.edu.anu.rscs.aot.queries.prototype.testing;

import static au.edu.anu.rscs.aot.queries.CoreQueries.startsWith;

import au.edu.anu.rscs.aot.collections.DynamicList;
import au.edu.anu.rscs.aot.queries.base.SelectQuery;
import au.edu.anu.rscs.aot.queries.prototype.base.XSelectQuery;
import au.edu.anu.rscs.aot.queries.prototype.queries.XCoreQueries;

public class Test {

	public static void main(String[] args) {
		int trial = 1;
		DynamicList<String> twi = new DynamicList<String>("test", "bill", "john", "tim", "xxx");
		{// ok: return 'bill'
			System.out.println("----- " + trial++ + "-----------");
			SelectQuery c1 = SelectQuery.selectZeroOrOne(startsWith("b"));
			XSelectQuery sq = XCoreQueries.selectZeroOrOne(XCoreQueries.startsWith("b"));
			c1.check(twi);
			sq.process(twi);
			System.out.println("new query: " + sq);
			System.out.println("old query: " + c1);
			System.out.println("old result: " + c1.getResult());
			System.out.println("New result: " + sq.result());
		}
		try { // !ok: two possibilities
			System.out.println("----- " + trial++ + "-----------");
			SelectQuery c1 = SelectQuery.selectZeroOrOne(startsWith("t"));
			System.out.println("old query: " + c1);
			c1.check(twi);
			System.out.println("  (bad) result: " + c1);
		} catch (Exception e) {
			System.out.println("old error: " + e);
		}
		{
			XSelectQuery sq = XCoreQueries.selectZeroOrOne(XCoreQueries.startsWith("t"));
			sq.process(twi);
			System.out.println("new query: " + sq);
			System.out.println("new error: " + sq.errorMsg());
		}
		{
			System.out.println("----- " + trial++ + "-----------");
			SelectQuery c1 = SelectQuery.selectZeroOrMany(startsWith("b"));
			c1.check(twi);
			System.out.println("old query: " + c1);
			XSelectQuery sq = XCoreQueries.selectZeroOrMany(XCoreQueries.startsWith("b"));
			sq.process(twi);
			System.out.println("new query: " + sq);
			System.out.println("old result: " + c1.getResult());
			System.out.println("new result: " + sq.result());

		}
		{
			System.out.println("----- " + trial++ + "-----------");

		}
	}

}