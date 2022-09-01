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
package au.edu.anu.rscs.aot.queries;

import java.util.Date;
import au.edu.anu.rscs.aot.queries.base.*;
import au.edu.anu.rscs.aot.queries.base.primitive.*;
import au.edu.anu.rscs.aot.queries.base.string.*;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import au.edu.anu.rscs.aot.queries.graph.edge.*;
import au.edu.anu.rscs.aot.queries.graph.element.*;
import au.edu.anu.rscs.aot.queries.graph.node.*;
import au.edu.anu.rscs.aot.queries.graph.node.NodeCharacteristics.RootLeaf;
import au.edu.anu.rscs.aot.queries.graph.uml.IsEnumeration;
import au.edu.anu.rscs.aot.queries.graph.uml.IsMultiplicity;
import au.edu.anu.rscs.aot.queries.graph.uml.IsUMLAssociation;
import au.edu.anu.rscs.aot.queries.graph.uml.IsUMLAttribute;
import au.edu.anu.rscs.aot.queries.graph.uml.IsUMLClass;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;
import fr.cnrs.iees.graph.*;

/**
 *
 * <p>
 * This class is a convenience class of static methods that brings together
 * queries of all types.
 * </p>
 * <p>
 * It is specially designed to work in interaction with the
 * {@link au.edu.anu.rscs.aot.queries.base.SequenceQuery#get(Object, Queryable...)
 * SequenceQuery.get(...)} method to query particular elements of a
 * {@link Graph}. Examples:
 * </p>
 * <p>
 * return all the OUT edges of a node labelled 'blue':
 * </p>
 * 
 * <pre>
 * get(node, edges(Direction.OUT), edgeListEndNodes(), selectZeroOrMany(hasTheLabel("blue")));
 * </pre>
 * <p>
 * return all the children of a (tree)node that have property color='red'
 * </p>
 * 
 * <pre>
 * get(node, children(), selectZeroOrMany(hasProperty("color", "blue")));
 * </pre>
 * 
 * @author Shayne Flint - 26/3/2012<br/>
 *         refactored by Ian Davies - feb. 2021
 */
public class CoreQueries {
	private CoreQueries() {
	}

	// General
	//
	/**
	 * <p>Test if all its sub-queries are satisfied. Will stop checking at the first 
	 * sub-query failure.</p>
	 * 
	 * @param queries  the queries to check
	 * @return the resulting AndQuery query
	 */
	public static Queryable andQuery(Queryable... queries) {
		return new AndQuery(queries);
	}

	/**
	 * <p>Test if all its sub-queries are <em>not</em> satisfied. Will stop checking at the first 
	 * sub-query success.</p>
	 * 
	 * @param queries the queries to check
	 * @return the resulting NotQuery query
	 */
	public static Queryable notQuery(Queryable... queries) {
		return new NotQuery(queries);
	}

	/**
	 * <p>Test if at least one its sub-queries is satisfied. Will stop checking at the first 
	 * sub-query success.</p>
	 * 
	 * @param queries the queries to check
	 * @return the resulting OrQuery query
	 */
	public static Queryable orQuery(Queryable... queries) {
		return new OrQuery(queries);
	}

	/**
	 * <p>Test if <em>exactly one</em> of its sub-queries is satisfied.</p>
	 * @param queries the queries to check
	 * @return the resulting XorQuery query
	 */
	public static Queryable xorQuery(Queryable... queries) {
		return new XorQuery(queries);
	}

	/**
	 * <p>
	 * Apply a query on a collection of objects
	 * </p>
	 * 
	 * @param query the query to apply
	 * @return the resulting ForAllQuery query
	 */
	public static Queryable forAll(Queryable query) {
		return new ForAllQuery(query);
	}

	/**
	 * <p>
	 * Test query. If this Query is satisfied, applies a query (called
	 * <em>trueQuery</em>), and if not, applies another one (called
	 * <em>falseQuery</em>) to the {@code submit()} argument.
	 * </p>
	 * *
	 * 
	 * @param testQuery  the test query
	 * @param trueQuery  the <em>trueQuery</em>
	 * @param falseQuery <em>falseQuery</em>
	 * @return the resulting IfThenQuery query
	 */
	public static Queryable ifThenQuery(Queryable testQuery, Queryable trueQuery, Queryable falseQuery) {
		return new IfThenQuery(testQuery, trueQuery, falseQuery);
	}

	/**
	 * <p>
	 * Test query. If this Query is satisfied, applies a query (called
	 * <em>trueQuery</em>) to the {@code submit()} argument.
	 * </p>
	 * *
	 * 
	 * @param testQuery the test query
	 * @param trueQuery the <em>trueQuery</em>
	 * @return the resulting IfThenQuery query
	 */
	public static Queryable ifThenQuery(Queryable testQuery, Queryable trueQuery) {
		return new IfThenQuery(testQuery, trueQuery);
	}

	/**
	 * <p>
	 * Checks that an Object is an instance of the Class passed as argument of this
	 * Query's constructor.
	 * </p>
	 * 
	 * @param theClass the class to check.
	 * @return the resulting IsInstanceOf query
	 */
	public static Queryable isInstanceOf(Class<?> theClass) {
		return new IsInstanceOf(theClass);
	}

	/**
	 * <p>
	 * Checks that an Object is an instance of the Class passed as argument of this
	 * Query's constructor.
	 * </p>
	 * 
	 * @param className the name of the class to check (must be a valid java name)
	 * @return the resulting IsInstanceOf query
	 */
	public static Queryable isInstanceOf(String className) {
		try {
			return new IsInstanceOf(Class.forName(className));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * <p>
	 * get the length of a {@link String}.
	 * </p>
	 * 
	 * @return the resulting StringLength query
	 */
	public static StringLength length() {
		return new StringLength();
	}

	/**
	 * <p>
	 * return the argument to the constructor as a result.
	 * </p>
	 * 
	 * @param item the object to return
	 * @return the resulting Value query
	 */
	public static Queryable value(Object item) {
		return new Value(item);
	}

	// primitive
	//

	/**
	 * <p>
	 * Check that an object is a boolean
	 * </p>
	 * 
	 * @return the resulting IsBoolean query
	 */
	public static Queryable isBoolean() {
		return new IsBoolean();
	}

	/**
	 * <p>
	 * check that a date is within a range
	 * </p>
	 * 
	 * @param min the lower end of the date range
	 * @param max the upper end of the date range
	 * @return the resulting IsDate query
	 */
	public static Queryable dateInRange(Date min, Date max) {
		return new IsDate(min, max);
	}

	/**
	 * <p>
	 * check that an object is a date
	 * </p>
	 * 
	 * @return the resulting IsDate query
	 */
	public static Queryable isDate() {
		return new IsDate(null, null);
	}

	/**
	 * <p>
	 * check that a double is within a range
	 * </p>
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsDouble query
	 */
	public static Queryable doubleInRange(double min, double max) {
		return new IsDouble(min, max);
	}

	/**
	 * <p>
	 * check that an object is a double
	 * </p>
	 * 
	 * @return the resulting IsDouble query
	 */
	public static Queryable isDouble() {
		return new IsDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
	}

	/**
	 * <p>
	 * check that a float is within a range
	 * </p>
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsFloat query
	 */
	public static Queryable floatInRange(float min, float max) {
		return new IsFloat(min, max);
	}

	/**
	 * <p>
	 * check that an object is a float
	 * </p>
	 * 
	 * @return the resulting IsFloat query
	 */
	public static Queryable isFloat() {
		return new IsFloat(-Float.MAX_VALUE, Float.MAX_VALUE);
	}

	/**
	 * <p>
	 * check that an integer is within a range
	 * </p>
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsInteger query
	 */
	public static Queryable integerInRange(int min, int max) {
		return new IsInteger(min, max);
	}

	/**
	 * <p>
	 * check that an object is an Integer
	 * </p>
	 * 
	 * @return the resulting IsInteger query
	 */
	public static Queryable isInteger() {
		return new IsInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * check that a long is within a range
	 * </p>
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsLong query
	 */
	public static Queryable longInRange(long min, long max) {
		return new IsLong(min, max);
	}

	/**
	 * <p>
	 * check that an object is a long
	 * </p>
	 * 
	 * @return the resulting IsLong query
	 */
	public static Queryable isLong() {
		return new IsLong(Long.MIN_VALUE, Long.MAX_VALUE);
	}

	/**
	 * <p>
	 * check that a String length is within a range
	 * </p>
	 * 
	 * @param minLength the lower end of the range
	 * @param maxLength the upper end of the range
	 * @return the resulting IsString query
	 */
	public static Queryable stringOfLength(int minLength, int maxLength) {
		return new IsString(minLength, maxLength);
	}

	/**
	 * <p>
	 * check that an object is a String
	 * </p>
	 * 
	 * @return the resulting IsString query
	 */
	public static Queryable isString() {
		return new IsString(0, Integer.MAX_VALUE);
	}

	/**
	 * <p>
	 * check that an object is an IntegerRange
	 * </p>
	 * 
	 * @return the resulting IsIntegerRange query
	 */
	public static Queryable isIntegerRange() {
		return new IsIntegerRange();
	}

	public static Queryable isClass(Class<?>... classList) {
		return new IsClass(classList);
	}

	public static Queryable isClass(String... classNames) {
		return new IsClass(classNames);
	}

	public static Queryable isStringList() {
		return new IsStringList();
	}

	// SelectQuery
	//

	/**
	 * 
	 * @return
	 */
	public static SelectQuery selectOne() {
		return new SelectQuery().returnOne().multiplicity(Multiplicity.ONE);
	}

	public static SelectQuery selectOne(Queryable q) {
		return selectOne().query(q);
	}

	public static SelectQuery selectZeroOrOne() {
		return new SelectQuery().returnOne().multiplicity(Multiplicity.ZERO_ONE);
	}

	public static SelectQuery selectZeroOrOne(Queryable q) {
		return selectZeroOrOne().query(q);
	}

	public static SelectQuery selectZeroOrMany() {
		return new SelectQuery().returnMany().multiplicity(Multiplicity.ZERO_MANY);
	}

	public static SelectQuery selectZeroOrMany(Queryable q) {
		return selectZeroOrMany().query(q);
	}

	public static SelectQuery selectOneOrMany() {
		return new SelectQuery().returnMany().multiplicity(Multiplicity.ONE_MANY);
	}

	public static SelectQuery selectOneOrMany(Queryable q) {
		return selectOneOrMany().query(q);
	}

	// Strings : Testing up to here except for some node element tests
	/**
	 * <p>Check if a {@link String} starts with a given sequence of characters.</p>
	 * @param s the initial String inputs must start with
	 * @return the StartsWith query
	 */
	public static Queryable startsWith(String s) {
		return new StartsWith(s);
	}

	/**
	 * <p>Check if a {@link String} ends with the {@code String} passed as argument.</p>
	 * @param s the string to search for
	 * @return the resulting EndsWith query
	 */
	public static Queryable endsWith(String s) {
		return new EndsWith(s);
	}

	/**
	 * <p>Check if a {@link String} contains the {@code String} that was passed to the constructor.</p>
	 * @param str the string to search for
	 * @return the resulting ContainsSubstring query
	 */
	public static Queryable containsSubstring(String str) {
		return new ContainsSubstring(str);
	}

	/**
	 * <p>Check if a {@link String} represents the class passed to the constructor.</p>
	 * @param parentClass the class to compare to
	 * @return the resulting ClassQuery query
	 */
	public static Queryable classIsClass(Class<?> parentClass) {
		return new ClassQuery(parentClass);
	}

	/**
	 * <p>Check if a {@link String} represents the class passed to the constructor.</p>
	 * @param parentClassName the class to compare to
	 * @return the resulting ClassQuery query
	 */
	public static Queryable stringIsClass(String parentClassName) {
		return new ClassQuery(parentClassName);
	}

	/**
	 *  <p>Check if a {@link String} represents a date.</p>
	 * @return the resulting DateString query
	 */
	public static Queryable isDateString() {
		return new DateString();
	}

	/**
	 * <p>Check if a {@link String} represents a real number ({@code Double}).</p>
	 * @return the RealString query
	 */
	public static Queryable isRealString() {
		return new RealString();
	}

	/**
	 * <p>Check if a {@link String} represents an integer number.</p>
	 * @return the IntegerString query
	 */
	public static Queryable isIntegerString() {
		return new IntegerString();
	}

	/**
	 * <p>Check if a {@link String} represents a long integer number.</p>
	 * @return the LongString query to do the check
	 */
	public static Queryable isLongString() {
		return new LongString();
	}

	/**
	 * <p>Check if a {@link String} represents an enum field.</p>
	 * @param valueList the list of enum values to compare to
	 * @return the resulting EnumerationString query
	 */
	public static Queryable isEnumStrings(String... valueList) {
		return new EnumerationString(valueList);
	}

	/**
	 * <p>Check if a {@link String} represents an enum field.</p>
	 * @param enumList the list of enum values to compare to
	 * @return the resulting EnumerationString query
	 */
	public static Queryable isEnum(Enum<?>... enumList) {
		return new EnumerationString(enumList);
	}

	/**
	 * <p>Check if a {@link String} represents the name of an existing file.</p>
	 * @return the resulting FileQuery query
	 */
	public static Queryable isFileName() {
		return new FileQuery();
	}

	/**
	 * <p>Check if a {@link String} represents an IPv4 address.</p>
	 * @return the resulting InetAddressString query
	 */
	public static Queryable isInetAddress() {
		return new InetAddressString();
	}

	/**
	 * <p>Check if a {@link String} matches a regular expression pattern.</p>
	 * @param pattern a regular expression
	 * @return the PatternString query initialised with pattern
	 */
	public static Queryable matchesPattern(String pattern) {
		return new PatternString(pattern);
	}

	// Size
	//
	/**
	 * Check that the size of the input (of class {@link fr.ens.biologie.generic.Sizeable Sizeable}) is within range
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting SizeQuery query
	 */
	public static Queryable inRange(int min, int max) {
		return new SizeQuery().min(min).max(max);
	}

	/**
	 * Check that the size of the input (of class {@link fr.ens.biologie.generic.Sizeable Sizeable}) is above minimum
	 * 
	 * @param min the minimum
	 * @return the resulting SizeQuery query
	 */
	public static Queryable hasMin(int min) {
		return new SizeQuery().min(min);
	}

	/**
	 * Check that the size of the input (of class {@link fr.ens.biologie.generic.Sizeable Sizeable}) is below maximum
	 * 
	 * @param max the maximum
	 * @return the resulting SizeQuery query
	 */
	public static Queryable hasMax(int max) {
		return new SizeQuery().max(max);
	}

	/**
	 * Check that the size of the input (of class {@link fr.ens.biologie.generic.Sizeable Sizeable}) is within range
	 * 
	 * @param range the range
	 * @return the resulting SizeQuery query
	 */
	public static Queryable inRange(IntegerRange range) {
		return new SizeQuery().min(range.getFirst()).max(range.getLast());
	}

	// Count

	/**
	 * Check that the input (integer) is greater than minimum
	 * 
	 * @param min the minimum
	 * @return the resulting CountQuery query
	 */
	public static CountQuery hasMinCount(int min) {
		return new CountQuery(min, Integer.MAX_VALUE);
	}

	/**
	 * Check that the input (integer) is smaller than maximum
	 * 
	 * @param max the maximum
	 * @return the resulting CountQuery query
	 */
	public static CountQuery hasMaxCount(int max) {
		return new CountQuery(Integer.MIN_VALUE, max);
	}

	/**
	 * Check that the input (integer) is within range
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting CountQuery query
	 */
	public static CountQuery countInRange(int min, int max) {
		return new CountQuery(min, max);
	}

	/**
	 * Check that the input (integer) is within range
	 * 
	 * @param range the range
	 * @return the resulting CountQuery query
	 */
	public static CountQuery countInRange(IntegerRange range) {
		return new CountQuery(range.getFirst(), range.getLast());
	}

	/**
	 * Check that the input (integer) is equal to size
	 * 
	 * @param size the value to check
	 * @return the resulting CountQuery query
	 */
	public static CountQuery hasCount(int size) {
		return new CountQuery(size, size);
	}

	// ------------------ Elements
	//
	/**
	 * Checks that an element (=graph node or edge) has one of the unique
	 * identifiers ({@code id}) passed as arguments. Will work for any
	 * {@link fr.cnrs.iees.identity.Identity Identity} implementation, actually.
	 * 
	 * @param names the names/ids to compare to
	 * @return the resulting ElementName query
	 */
	public static Queryable hasTheName(String... names) {
		return new ElementName(names);
	}

	/**
	 * Checks that an element (=graph node or edge) has one of the class identifiers
	 * ({@code classId}) passed as arguments. Will work for any {@link Specialized}
	 * implementation, actually.
	 * 
	 * @param labels the labels/classIds to compare to
	 * @return the resulting ElementLabel query
	 */
	public static Queryable hasTheLabel(String... labels) {
		return new ElementLabel(labels);
	}

	/**
	 * Checks that a property exists in the input
	 * 
	 * @param k the name of the property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k) {
		return new ElementProperty().key(k);
	}

	/**
	 * Checks that a property exists and has the specified value in the input
	 * 
	 * @param k the name of the property
	 * @param v the expected value
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k, Object v) {
		return new ElementProperty().key(k).value(v);
	}

	/**
	 * Checks that a property exists and satisfied the specified query
	 * 
	 * @param k the name of the property
	 * @param q the query to check for this property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k, Queryable q) {
		return new ElementProperty().key(k).query(q);
	}

	/**
	 * Checks that an <em>optional</em> property exists and satisfied the specified
	 * query
	 * 
	 * @param k the name of the property
	 * @param q the query to check for this property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasOptionalProperty(String k, Queryable q) {
		return new ElementProperty().key(k).query(q).optional();
	}

	/**
	 * Get the value of the specified property, and use the specified default value
	 * if property not set
	 * 
	 * @param k  the name of the property
	 * @param dv the value to set if the property was empty
	 * @return the resulting ElementProperty query
	 */
	public static ElementProperty getProperty(String k, Object dv) {
		return new ElementProperty().key(k).defaultValue(dv).getValue();
	}

	/**
	 * Get the value of the specified property
	 * 
	 * @param k the name of the property
	 * @return the resulting ElementProperty query
	 */
	public static ElementProperty getProperty(String k) {
		return new ElementProperty().key(k).getValue();
	}

	// ---------------------- Nodes
	//
	/**
	 * Get all the children of the {@code TreeNode} passed to {@code submit(...)}.
	 * @return an instance of the TreeQuery query
	 */
	public static Queryable children() {
//		return new Trees(null, false, false);
		return new TreeQuery();
	}

	/**
	 * Get all the parents (up to the tree root) of the {@code TreeNode} passed to {@code submit(...)}.
	 * @return an instance of the TreeQuery query
	 */
	public static Queryable parent() {
		// Trees(null, true, false);
		return new TreeQuery().parent();
	}

	/**
	 * Get all the parents (up to the tree root) of the {@code TreeNode} passed to {@code submit(...)},
	 * provided that they satisfy the query.
	 * @param q the query parents must satisfy
	 * @return an instance of the TreeQuery query
	 */
	public static Queryable parent(Queryable q) {
		return new TreeQuery().parent().root().query(q);
	}

	/**
	 * Get all the sub-tree (i.e. all children's children's...'s children) of the {@code TreeNode} 
	 * passed to {@code submit(...)}.
	 * @return an instance of the TreeQuery query
	 */
	public static Queryable childTree() {
		// Trees(null, false, true);
		return new TreeQuery().root();
	}

	/**
	 * Get all the sub-tree (i.e. all children's children's...'s children) of the {@code TreeNode} 
	 * passed to {@code submit(...)} provided that they satisfy the query.
	 * @param q
	 * @return an instance of the TreeQuery query
	 */
	public static Queryable childTree(Queryable q) {
		return new TreeQuery().root().query(q);
	}

	/**
	 * <p>A Query to select in-{@link Edge}s linked to a {@link Node}.</p>
	 * @return an instance of the NodeEdges query
	 */
	public static Queryable inEdges() {
		return new NodeEdges().direction(Direction.IN);
	}

	/**
	 * <p>A Query to select in-{@link Edge}s of a {@link Node} that satisfy a query.</p>
	 * @param q a query that edges must satisfy
	 * @return an instance of the NodeEdges query
	 */
	public static Queryable inEdges(Queryable q) {
		return new NodeEdges().direction(Direction.IN).query(q);
	}

	/**
	 * <p>A Query to select out-{@link Edge}s linked to a {@link Node}.</p>
	 * @return an instance of the NodeEdges query
	 */
	public static Queryable outEdges() {
		return new NodeEdges().direction(Direction.OUT);
	}

	/**
	 * <p>A Query to select in-{@link Edge}s of a {@link Node} that satisfy a query.</p>
	 * @param q a query that edges must satisfy
	 * @return an instance of the NodeEdges query
	 */
	public static Queryable outEdges(Queryable q) {
		return new NodeEdges().direction(Direction.OUT).query(q);
	}

	/**
	 * <p>Check if a {@link Node} that satisfies a query has  number of {@link Edge}s  
	 * that match a multiplicity.</p>
	 * 
	 * @param nodeQuery the query applied to the node
	 * @param multiplicity the multiplicity of edges
	 * @return an instance of HasEdges query
	 */
	public static Queryable hasEdges(Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(null, nodeQuery, null, multiplicity, null);
	}

	/**
	 * <p>Check if a {@link Node} that satisfies a query has  number of in-{@link Edge}s  
	 * that match a multiplicity.</p>
	 * 
	 * @param nodeQuery the query applied to the node
	 * @param multiplicity the multiplicity of edges
	 * @return an instance of HasEdges query
	 */
	public static Queryable hasInEdges(Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.IN, nodeQuery, null, multiplicity, null);
	}

	/**
	 * <p>Check if a {@link Node} that satisfies a query has  number of out-{@link Edge}s  
	 * that match a multiplicity.</p>
	 * 
	 * @param nodeQuery the query applied to the node
	 * @param multiplicity the multiplicity of edges
	 * @return an instance of HasEdges query
	 */
	public static Queryable hasOutEdges(Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.OUT, nodeQuery, null, multiplicity, null);
	}

	/**
	 * <p>Check if a {@link TreeNode} has a parent that satisfies a query.</p>
	 * @param query the query the parent must satisfy
	 * @return an instance of HasParent query
	 */
	public static Queryable hasParent(Queryable query) {
		return new HasParent(query);
	}

	/**
	 * <p>Check if a {@link TreeNode} has a parent.</p>
	 * @return an instance of HasParent query
	 */
	public static Queryable hasParent() {
		return new HasParent(null);
	}

	/**
	 * <p>Check if a {@link Node} is the same as the one passed to the constructor.</p>
	 * @param node the node instance to compare to
	 * @return an instance of IsNode query
	 */
	public static Queryable isNode(Node node) {
		return new IsNode(node);
	}

	/**
	 * <p>Check if a {@link Node} is found in the list of nodes passed to the constructor.</p>
	 * 
	 * @param nodes nodes to match
	 * @return an instance of IsOneOf query
	 */
	public static Queryable isOneOf(Node... nodes) {
		return new IsOneOf(nodes);
	}

	/**
	 * <p>Check if a {@link Node} is a root node.</p>
	 * @return an instance of NodeCharacteristics query
	 */
	public static Queryable isRoot() {
		return new NodeCharacteristics(RootLeaf.ROOT);
	}

	/**
	 * <p>Check if a {@link Node} is a leaf node.</p>
	 * @return an instance of NodeCharacteristics query
	 */
	public static Queryable isLeaf() {
		return new NodeCharacteristics(RootLeaf.LEAF);
	}

	// ------------------- Node lists
	//
	/**
	 * <p> A {@link Queryable} to select in-edges of a list of Nodes.</p>
	 * @return an instance of NodeListEdges query
	 */
	public static Queryable nodeListInEdges() {
		return new NodeListEdges().direction(Direction.IN);
	}

	/**
	 * <p> A {@link Queryable} to select out-edges of a list of Nodes.</p>
	 * @return an instance of NodeListEdges query
	 */
	public static Queryable nodeListOutEdges() {
		return new NodeListEdges().direction(Direction.OUT);
	}

	/**
	 * <p> A {@link Queryable} to select all edges of a list of Nodes.</p>
	 * @return an instance of NodeListEdges query
	 */
	public static Queryable nodeListEdges() {
		return new NodeListEdges();
	}

	// ------------------- Edge lists
	//
	/**
	 * Get the list of start nodes of an edge list
	 * 
	 * @return the resulting EdgeListNodes query
	 */
	public static Queryable edgeListStartNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.START);
	}

	/**
	 * Get the list of end nodes of an edge list
	 * 
	 * @return the resulting EdgeListNodes query
	 */
	public static Queryable edgeListEndNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.END);
	}

	/**
	 * Get the list of all start and end nodes of an edge list
	 * 
	 * @return the resulting EdgeListNodes query
	 */
	public static Queryable edgeListBothNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.BOTH);
	}

	/**
	 * Get the list of nodes at the opposite end of the argument of an edge list
	 * 
	 * @param n the node which opposite end has to be found
	 * @return the resulting EdgeListNodes query
	 */
	public static Queryable edgeListOtherNodes(Node n) {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.OTHER).refNode(n);
	}

	// - ------------------------- Edge
	//

	/**
	 * <p>A {@link Queryable} to select the end Node of an Edge</p>
	 * @return the EdgeNodes query
	 */
	public static Queryable endNode() {
		return new EdgeNodes().edgeNodeSelection(EdgeNodeSelection.END);
	}

	/**
	 * <p>A {@link Queryable} to select the start Node of an Edge</p>
	 * @return the EdgeNodes query
	 */
	public static Queryable startNode() {
		return new EdgeNodes().edgeNodeSelection(EdgeNodeSelection.START);
	}

	/**
	 * Checks that the end Node of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
	public static EdgeHasNode hasEndNode(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.END, nodeQuery);
	}

	/**
	 * Checks that the start Node of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
	public static Queryable hasStartNode(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.START, nodeQuery);
	}

	/**
	 * Checks that the other Node of the Edge satisfies a Query
	 * 
	 * @param nodeQuery the Query to satisfy
	 * @param refNode   the node opposite to the node is checked
	 * @return the resulting EdgeHasNode query
	 */
	public static Queryable hasOtherNode(Queryable nodeQuery, Node refNode) {
		return new EdgeHasNode(nodeQuery, refNode);
	}

	/**
	 * Checks that both Nodes of the Edge satisfies a Query.
	 * 
	 * @param nodeQuery the query to satisfy
	 * @return the resulting EdgeHasNode query
	 */
	public static Queryable hasBothNodes(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.BOTH, nodeQuery);
	}

	/**
	 * <p>Check if an {@link Edge} matches the one passed as argument.</p>
	 * @param edge the edge to compare to
	 * @return the IsEdge query
	 */
	public static Queryable isEdge(Edge edge) {
		return new IsEdge(edge);
	}
	
	/**
	 * <p>Check that the tip {@link Node}s of an {@link Edge} ends satisfy their respective
	 * queries and that they have a specific {@link Node#classId() classId()}; also checks
	 * the {@link Edge#classId() classId()}.</p>
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 * @param startLabel a class identifier the start node must match
	 * @param label a class identifier the edge must match
	 * @param endLabel a class identifier the end node must match
	 * @return the EdgeQuery query
	 */
	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery, String startLabel, String label,
			String endLabel) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, startLabel, label, endLabel);
	}

	/**
	 * <p>Check that the tip {@link Node}s of an {@link Edge} ends satisfy their respective
	 * queries and that the {@link Edge#classId() classId()} matches the label.</p>
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 * @param label a class identifier the edge must match
	 * @return the EdgeQuery query
	 */
	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery, String label) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, label);
	}

	/**
	 * <p>Check that the tip {@link Node}s of an {@link Edge} ends satisfy their respective
	 * queries.</p>
	 * 
	 * @param startNodeQuery a query to check on the start node
	 * @param endNodeQuery a query to check on the end node
	 * @return the EdgeQuery query
	 */
	public static EdgeQuery isEdge(Queryable startNodeQuery, Queryable endNodeQuery) {
		return new EdgeQuery(startNodeQuery, endNodeQuery);
	}
	
	// UML
	//
	/**
	 * <p>Check if an object is a UML multiplicity.</p>
	 * @return an instance of IsMultiplicity query
	 */
	public static Queryable isMultiplicity() {
		return new IsMultiplicity();
	}
	
	/**
	 * <p>Check if an object is an UML enumeration.</p>
	 * <p>CAUTION: this is different from a java enumeration. see {@link CoreQueries#isEnum(Enum...)}
	 * or {@link CoreQueries#isEnumStrings(String...)} if that is what you want to test.
	 * @return an instance of the IsEnumeration query
	 */
	public static IsEnumeration isEnumeration() {
		return new IsEnumeration();
	}

	/**
	 * <p>Check if an object is an UML attribute.</p>
	 * @return an instance of the IsUMLAttribute query
	 */
	public static IsUMLAttribute isAttribute() {
		return new IsUMLAttribute();
	}

	/**
	 * <p>Check if an object is an UML association.</p>
	 * @return an instance of the IsUMLAssociation query
	 */
	public static IsUMLAssociation isAssociation() {
		return new IsUMLAssociation();
	}

	/**
	 * <p>Check if an object is an UML class.</p>
	 * @return
	 */
	public static IsUMLClass isUMLClass() {
		return new IsUMLClass();
	}

}
