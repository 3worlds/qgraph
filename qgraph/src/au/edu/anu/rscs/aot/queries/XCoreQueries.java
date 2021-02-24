package au.edu.anu.rscs.aot.queries;

import au.edu.anu.rscs.aot.queries.base.XAndQuery;
import au.edu.anu.rscs.aot.queries.base.XNotQuery;
import au.edu.anu.rscs.aot.queries.base.XOrQuery;
import au.edu.anu.rscs.aot.queries.base.XSelectQuery;
import au.edu.anu.rscs.aot.queries.base.XSizeQuery;
import au.edu.anu.rscs.aot.queries.base.XXorQuery;
import au.edu.anu.rscs.aot.queries.base.string.XStartsWith;
import au.edu.anu.rscs.aot.queries.graph.element.XElementLabel;
import au.edu.anu.rscs.aot.queries.graph.element.XElementName;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;

public class XCoreQueries {
	private XCoreQueries() {
	}

	// General
	//
	public static Queryable andQuery(Queryable... queries) {
		return new XAndQuery(queries);
	}

	public static Queryable notQuery(Queryable... queries) {
		return new XNotQuery(queries);
	}

	public static Queryable orQuery(Queryable... queries) {
		return new XOrQuery(queries);
	}

	public static Queryable xorQuery(Queryable... queries) {
		return new XXorQuery(queries);
	}

	// SelectQuery
	public static XSelectQuery selectOne() {
		return new XSelectQuery().returnOne().multiplicity(Multiplicity.ONE);
	}

	public static XSelectQuery selectOne(Queryable q) {
		return  selectOne().query(q);
	}

	public static XSelectQuery selectZeroOrOne() {
		return new XSelectQuery().returnOne().multiplicity(Multiplicity.ZERO_ONE);
	}

	public static XSelectQuery selectZeroOrOne(Queryable q) {
		return selectZeroOrOne().query(q);
	}

	private static XSelectQuery selectZeroOrMany() {
		return new XSelectQuery().returnMany().multiplicity(Multiplicity.ZERO_MANY);
	}

	public static XSelectQuery selectZeroOrMany(Queryable q) {
		return selectZeroOrMany().query(q);
	}

	// Strings TODO
	public static Queryable startsWith(String s) {
		return new XStartsWith(s);
	}

	// Size
	public static Queryable inRange(int min, int max) {
		return new XSizeQuery().min(min).max(max);
	}

	public static Queryable hasMin(int min) {
		return new XSizeQuery().min(min);
	}

	public static Queryable hasMax(int max) {
		return new XSizeQuery().max(max);
	}

	public static Queryable inRange(IntegerRange range) {
		return new XSizeQuery().min(range.getFirst()).max(range.getLast());
	}

	// Elements
	//
	public static Queryable hasTheName(String... names) {
		return new XElementName(names);
	}

	public static Queryable hasTheLabel(String... labels) {
		return new XElementLabel(labels);
	}

}
