package au.edu.anu.rscs.aot.queries;

import java.util.Date;
import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.CountQuery;
import au.edu.anu.rscs.aot.queries.base.ForAllQuery;
import au.edu.anu.rscs.aot.queries.base.IfThenQuery;
import au.edu.anu.rscs.aot.queries.base.IsInstanceOf;
import au.edu.anu.rscs.aot.queries.base.NotQuery;
import au.edu.anu.rscs.aot.queries.base.OrQuery;
import au.edu.anu.rscs.aot.queries.base.SelectQuery;
import au.edu.anu.rscs.aot.queries.base.SizeQuery;
import au.edu.anu.rscs.aot.queries.base.Value;
import au.edu.anu.rscs.aot.queries.base.XorQuery;
import au.edu.anu.rscs.aot.queries.base.primitive.IsBoolean;
import au.edu.anu.rscs.aot.queries.base.primitive.IsClass;
import au.edu.anu.rscs.aot.queries.base.primitive.IsDate;
import au.edu.anu.rscs.aot.queries.base.primitive.IsDouble;
import au.edu.anu.rscs.aot.queries.base.primitive.IsFloat;
import au.edu.anu.rscs.aot.queries.base.primitive.IsInteger;
import au.edu.anu.rscs.aot.queries.base.primitive.IsIntegerRange;
import au.edu.anu.rscs.aot.queries.base.primitive.IsLong;
import au.edu.anu.rscs.aot.queries.base.primitive.IsString;
import au.edu.anu.rscs.aot.queries.base.primitive.IsStringList;
import au.edu.anu.rscs.aot.queries.base.string.ClassQuery;
import au.edu.anu.rscs.aot.queries.base.string.ContainsSubstring;
import au.edu.anu.rscs.aot.queries.base.string.DateString;
import au.edu.anu.rscs.aot.queries.base.string.DoubleString;
import au.edu.anu.rscs.aot.queries.base.string.EndsWith;
import au.edu.anu.rscs.aot.queries.base.string.EnumerationString;
import au.edu.anu.rscs.aot.queries.base.string.FileQuery;
import au.edu.anu.rscs.aot.queries.base.string.FloatString;
import au.edu.anu.rscs.aot.queries.base.string.InetAddressString;
import au.edu.anu.rscs.aot.queries.base.string.IntegerString;
import au.edu.anu.rscs.aot.queries.base.string.IsValidName;
import au.edu.anu.rscs.aot.queries.base.string.LongString;
import au.edu.anu.rscs.aot.queries.base.string.PatternString;
import au.edu.anu.rscs.aot.queries.base.string.StartsWith;
import au.edu.anu.rscs.aot.queries.base.string.UserNameQuery;
import au.edu.anu.rscs.aot.queries.base.string.StringLength;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import au.edu.anu.rscs.aot.queries.graph.edge.EdgeHasNode;
import au.edu.anu.rscs.aot.queries.graph.edge.EdgeListNodes;
import au.edu.anu.rscs.aot.queries.graph.edge.EdgeNodes;
import au.edu.anu.rscs.aot.queries.graph.edge.IsEdge;
import au.edu.anu.rscs.aot.queries.graph.element.ElementLabel;
import au.edu.anu.rscs.aot.queries.graph.element.ElementName;
import au.edu.anu.rscs.aot.queries.graph.element.ElementProperty;
import au.edu.anu.rscs.aot.queries.graph.node.HasEdges;
import au.edu.anu.rscs.aot.queries.graph.node.HasParent;
import au.edu.anu.rscs.aot.queries.graph.node.IsNode;
import au.edu.anu.rscs.aot.queries.graph.node.IsOneOf;
import au.edu.anu.rscs.aot.queries.graph.node.NodeCharacteristics;
import au.edu.anu.rscs.aot.queries.graph.node.NodeEdges;
import au.edu.anu.rscs.aot.queries.graph.node.NodeListEdges;
import au.edu.anu.rscs.aot.queries.graph.node.TreeQuery;
import au.edu.anu.rscs.aot.queries.graph.node.NodeCharacteristics.RootLeaf;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Edge;
import fr.cnrs.iees.graph.Node;

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
	public static Queryable andQuery(Queryable... queries) {
		return new AndQuery(queries);
	}

	public static Queryable notQuery(Queryable... queries) {
		return new NotQuery(queries);
	}

	public static Queryable orQuery(Queryable... queries) {
		return new OrQuery(queries);
	}

	public static Queryable xorQuery(Queryable... queries) {
		return new XorQuery(queries);
	}

	/**
	 * <p>Apply a query on a collection of objects</p>
	 * @param query the query to apply
	 * @return the resulting ForAllQuery query
	 */
	public static Queryable forAll(Queryable query) {
		return new ForAllQuery(query);
	}
	
	/**
	 * <p>Test query. If this Query is satisfied, applies a query (called <em>trueQuery</em>), 
	 * and if not, applies another one (called <em>falseQuery</em>)
	 * to the {@code submit()} argument.</p>	 * 
	 * @param testQuery  the test query
	 * @param trueQuery the <em>trueQuery</em>
	 * @param falseQuery <em>falseQuery</em>
	 * @return the resulting IfThenQuery query
	 */
	public static IfThenQuery ifThenQuery(Queryable testQuery, Queryable trueQuery, Queryable falseQuery) {
		return new IfThenQuery(testQuery, trueQuery, falseQuery);
	}
	
	/**
	 * <p>Test query. If this Query is satisfied, applies a query (called <em>trueQuery</em>)
	 * to the {@code submit()} argument.</p>	 * 
	 * @param testQuery  the test query
	 * @param trueQuery the <em>trueQuery</em>
	 * @return the resulting IfThenQuery query
	 */
	public static IfThenQuery ifThenQuery(Queryable testQuery, Queryable trueQuery) {
		return new IfThenQuery(testQuery, trueQuery);
	}
	
	/**
	 * <p>Checks that an Object is an instance of the Class passed as argument of 
	 * this Query's constructor.</p>
	 * @param theClass the class to check.
	 * @return the resulting IsInstanceOf query
	 */
	public static Queryable isInstanceOf(Class<?> theClass) {
		return new IsInstanceOf(theClass);
	}

	/**
	 * <p>Checks that an Object is an instance of the Class passed as argument of 
	 * this Query's constructor.</p>
	 * @param className the name of the class to check (must be a valid java name)
	 * @return the resulting IsInstanceOf query
	 */
	public static Queryable isInstanceOf(String className) {
		try {
			return new IsInstanceOf(Class.forName(className));
		} catch (Exception e) {
			throw new QGraphException("Can't create IsInstanceOf constraint", e);
		}
	}

	/**
	 * <p>get the length of a {@link String}.</p>
	 * @return the resulting StringLength query
	 */
	public static StringLength length() {
		return new StringLength();
	}
	
	/**
	 * <p>return the argument as a result.</p>
	 * @param item the object to return
	 * @return the resulting Value query
	 */
	public static Queryable value(Object item) {
		return new Value(item);
	}
	
	// primitive
	//

	/**
	 * <p>Check that an object is a boolean</p>
	 * @return the resulting IsBoolean query
	 */
	public static Queryable isBoolean() {
		return new IsBoolean();
	}
	
	/**
	 * <p>check that a date is within a range</p>
	 * @param min the lower end of the date range
	 * @param max the upper end of the date range
	 * @return the resulting IsDate query
	 */
	public static Queryable dateInRange(Date min, Date max) {
		return new IsDate(min, max);
	}

	/**
	 * <p>check that an object is a date</p>
	 * @return the resulting IsDate query
	 */
	public static Queryable isDate() {
		return new IsDate(null, null);
	}

	/**
	 * <p>check that a double is within a range</p>
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsDouble query
	 */
	public static Queryable doubleInRange(double min, double max) {
		return new IsDouble(min, max);
	}

	/**
	 * <p>check that an object is a double</p>
	 * @return the resulting IsDouble query
	 */
	public static Queryable isDouble() {
		return new IsDouble(-Double.MAX_VALUE, Double.MAX_VALUE);
	}

	/**
	 * <p>check that a float is within a range</p>
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsFloat query
	 */
	public static Queryable floatInRange(float min, float max) {
		return new IsFloat(min, max);
	}

	/**
	 * <p>check that an object is a float</p>
	 * @return the resulting IsFloat query
	 */
	public static Queryable isFloat() {
		return new IsFloat(-Float.MAX_VALUE, Float.MAX_VALUE);
	}
	
	/**
	 * <p>check that an integer is within a range</p>
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsInteger query
	 */
	public static Queryable integerInRange(int min, int max) {
		return new IsInteger(min, max);
	}

	/**
	 * <p>check that an object is an Integer</p>
	 * @return the resulting IsInteger query
	 */
	public static Queryable isInteger() {
		return new IsInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * <p>check that a long is within a range</p>
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting IsLong query
	 */
	public static Queryable longInRange(long min, long max) {
		return new IsLong(min, max);
	}

	/**
	 * <p>check that an object is a long</p>
	 * @return the resulting IsLong query
	 */
	public static Queryable isLong() {
		return new IsLong(Long.MIN_VALUE, Long.MAX_VALUE);
	}

	/**
	 * <p>check that a String length is within a range</p>
	 * @param minLength the lower end of the range
	 * @param maxLength the upper end of the range
	 * @return the resulting IsString query
	 */
	public static Queryable stringOfLength(int minLength, int maxLength) {
		return new IsString(minLength, maxLength);
	}

	/**
	 * <p>check that an object is a String</p>
	 * @return the resulting IsString query
	 */
	public static Queryable isString() {
		return new IsString(0, Integer.MAX_VALUE);
	}

	/**
	 * <p>check that an object is an IntegerRange</p>
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

	// Strings
	public static Queryable startsWith(String s) {
		return new StartsWith(s);
	}

	public static Queryable endsWith(String s) {
		return new EndsWith(s);
	}

	public static Queryable containsSubstring(String str) {
		return new ContainsSubstring(str);
	}

	public static Queryable classIsClass(Class<?> parentClass) {
		return new ClassQuery(parentClass);
	}

	public static Queryable stringIsClass(String parentClassName) {
		return new ClassQuery(parentClassName);
	}

	public static Queryable isDateString() {
		return new DateString();
	}

	public static Queryable isDoubleString() {
		return new DoubleString();
	}

	public static Queryable isFloatString() {
		return new FloatString();
	}

	public static Queryable isIntegerString() {
		return new IntegerString();
	}

	public static Queryable isLongString() {
		return new LongString();
	}

	public static Queryable isEnumStrings(String... valueList) {
		return new EnumerationString(valueList);
	}

	public static Queryable isEnum(Enum<?>... enumList) {
		return new EnumerationString(enumList);
	}

	public static Queryable isFileName() {
		return new FileQuery();
	}

	public static Queryable isInetAddress() {
		return new InetAddressString();
	}

	public static Queryable isValidName() {
		return new IsValidName();
	}

	public static Queryable matchesPattern(String pattern) {
		return new PatternString(pattern);
	}

	public static Queryable isUserName() {
		return new UserNameQuery();
	}

	// Size
	/**
	 * Check that the size of the input (of class {@link Sizeable}) is within range
	 * 
	 * @param min the lower end of the range
	 * @param max the upper end of the range
	 * @return the resulting SizeQuery query
	 */
	public static Queryable inRange(int min, int max) {
		return new SizeQuery().min(min).max(max);
	}

	/**
	 * Check that the size of the input (of class {@link Sizeable}) is above minimum
	 * 
	 * @param min the minimum
	 * @return the resulting SizeQuery query
	 */
	public static Queryable hasMin(int min) {
		return new SizeQuery().min(min);
	}

	/**
	 * Check that the size of the input (of class {@link Sizeable}) is below maximum
	 * 
	 * @param min the maximum
	 * @return the resulting SizeQuery query
	 */
	public static Queryable hasMax(int max) {
		return new SizeQuery().max(max);
	}

	/**
	 * Check that the size of the input (of class {@link Sizeable}) is within range
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
	 * {@link Identity} implementation, actually.
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
	public static Queryable children() {
//		return new Trees(null, false, false);
		return new TreeQuery();
	}

	public static Queryable parent() {
		// Trees(null, true, false);
		return new TreeQuery().parent();
	}

	public static Queryable parent(Queryable q) {
		return new TreeQuery().parent().root().query(q);
	}

	public static Queryable childTree() {
		// Trees(null, false, true);
		return new TreeQuery().root();
	}

	public static Queryable childTree(Queryable q) {
		return new TreeQuery().root().query(q);
	}

	public static Queryable inEdges() {
		return new NodeEdges().direction(Direction.IN);
	}

	public static Queryable inEdges(Queryable q) {
		return new NodeEdges().direction(Direction.IN).query(q);
	}

	public static Queryable outEdges() {
		return new NodeEdges().direction(Direction.OUT);
	}

	public static Queryable outEdges(Queryable q) {
		return new NodeEdges().direction(Direction.OUT).query(q);
	}
	
	public static Queryable hasEdges(Direction edgeDirection, Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(edgeDirection, nodeQuery, null, multiplicity, null);
	}
	
	public static Queryable hasInEdges(Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.IN, nodeQuery, null, multiplicity, null);
	}
	
	public static Queryable hasOutEdges(Queryable nodeQuery, Multiplicity multiplicity) {
		return new HasEdges(Direction.OUT, nodeQuery, null, multiplicity, null);
	}
	
	public static Queryable hasParent(Queryable query) {
		return new HasParent(query);
	}

	public static Queryable hasParent() {
		return new HasParent(null);
	}
	
	public static Queryable isNode(Node node) {
		return new IsNode(node);
	}	
	
	public static Queryable isOneOf(Node... nodes) {
		return new IsOneOf(nodes);
	}
	public static Queryable isRoot() {
		return new NodeCharacteristics(RootLeaf.ROOT);
	}

	public static Queryable isLeaf() {
		return new NodeCharacteristics(RootLeaf.LEAF);
	}

	// ------------------- Node lists
	public static Queryable nodeListInEdges() {
		return new NodeListEdges().direction(Direction.IN);
	}

	public static Queryable nodeListOutEdges() {
		return new NodeListEdges().direction(Direction.OUT);
	}

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
	public static Queryable endNode() {
		return new EdgeNodes().edgeNodeSelection(EdgeNodeSelection.END);
	}

	public static Queryable startNode() {
		return new EdgeNodes().edgeNodeSelection(EdgeNodeSelection.START);
	}
	
	public static EdgeHasNode hasEndNode(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.END, nodeQuery);
	}
	
	public static Queryable hasStartNode(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.START, nodeQuery);
	}

	public static Queryable hasOtherNode(Queryable nodeQuery, Node refNode) {
		return new EdgeHasNode(EdgeNodeSelection.OTHER, nodeQuery, refNode);
	}
	
	public static Queryable hasBothNodes(Queryable nodeQuery) {
		return new EdgeHasNode(EdgeNodeSelection.BOTH, nodeQuery);
	}
	
	public static Queryable isEdge(Edge edge) {
		return new IsEdge(edge);
	}

}
