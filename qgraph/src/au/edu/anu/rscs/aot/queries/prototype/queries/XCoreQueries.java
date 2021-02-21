package au.edu.anu.rscs.aot.queries.prototype.queries;

import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.queries.prototype.base.XSelectQuery;
import au.edu.anu.rscs.aot.queries.prototype.base.XSizeQuery;
import au.edu.anu.rscs.aot.queries.prototype.base.string.XStartsWith;

public class XCoreQueries {
	private XCoreQueries() {	
	}

	// SelectQuery
	public static XSelectQuery selectOne() {
		return new XSelectQuery().returnOne().multiplicity(Multiplicity.ONE);
	}

	public static XSelectQuery selectOne(XQuery q) {
		return selectOne().query(q);
	}

	public static XSelectQuery selectZeroOrOne() {
		return new XSelectQuery().returnOne().multiplicity(Multiplicity.ZERO_ONE);
	}

	public static XSelectQuery selectZeroOrOne(XQuery q) {
		return selectZeroOrOne().query(q);
	}
	
	private static XSelectQuery selectZeroOrMany() {
		return new XSelectQuery().returnMany().multiplicity(Multiplicity.ZERO_MANY);
	}

	public static XSelectQuery selectZeroOrMany(XQuery q) {
		return selectZeroOrMany().query(q);
	}
	
	// Strings TODO
	public static XQuery startsWith(String s) {
		return new XStartsWith(s);
	}
	// Size TODO
	public static XQuery inRange(int min, int max) {
		return new XSizeQuery(min,max);
	}

}
