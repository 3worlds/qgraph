package au.edu.anu.rscs.aot.queries;

import java.util.Date;

import au.edu.anu.rscs.aot.queries.base.*;
import au.edu.anu.rscs.aot.queries.base.primitive.*;
import au.edu.anu.rscs.aot.queries.base.string.*;
import au.edu.anu.rscs.aot.queries.graph.*;
import au.edu.anu.rscs.aot.queries.graph.edge.*;
import au.edu.anu.rscs.aot.queries.graph.element.*;
import au.edu.anu.rscs.aot.queries.graph.node.*;
import au.edu.anu.rscs.aot.queries.graph.uml.*;
import au.edu.anu.rscs.aot.util.IntegerRange;
import fr.cnrs.iees.graph.generic.Direction;
import fr.cnrs.iees.graph.generic.Edge;
import fr.cnrs.iees.graph.generic.Node;


/**
 * 
 * This class is a convenience class of static methods that brings together queries of all types
 * 
 * @author Shayne Flint - 26/3/2012
 */
public class CoreQueries {

	// General
	//

	public static AndQuery andQuery(Query... queries) {
		return AndQuery.andQuery(queries);
	}

	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery, Query falseQuery) {
		return IfThenQuery.ifThenQuery(testQuery, trueQuery, falseQuery);
	}

	public static IfThenQuery ifThenQuery(Query testQuery, Query trueQuery) {
		return IfThenQuery.ifThenQuery(testQuery, trueQuery);
	}

	public static NotQuery notQuery(Query... queries) {
		return NotQuery.notQuery(queries);
	}

	public static OrQuery orQuery(Query... queries) {
		return OrQuery.orQuery(queries);
	}

	public static XorQuery xorQuery(Query... queries) {
		return XorQuery.xorQuery(queries);
	}

	public static Query pop(int count) {
		return PopQuery.pop(count);
	}

	public static Query reset() {
		return ResetQuery.reset();
	}

	public static Query value(Object item) {
		return Value.value(item);
	}

	public static Query isInstanceOf(Class<?> theClass) {
		return IsInstanceOf.isInstanceOf(theClass);
	}

	public static Query forAll(Query query) {
		return ForAllQuery.forAll(query);
	}

	public static Query isQuery() {
		return IsQuery.isQuery();
	}

	// List sizes
	//

	public static Query hasCount(int min, int max) {
		return SizeQuery.inRange(min, max);
	}

	public static Query hasMin(int min) {
		return SizeQuery.hasMin(min);
	}

	public static Query hasMax(int max) {
		return SizeQuery.hasMax(max);
	}

	public static Query inRange(int min, int max) {
		return SizeQuery.inRange(min, max);
	}

	public static Query inRange(IntegerRange range) {
		return SizeQuery.inRange(range);
	}

	public static Query hasSize(int size) {
		return SizeQuery.hasSize(size);
	}

	public static Query hasZero() {
		return SizeQuery.hasZero();
	}

	public static Query hasOne() {
		return SizeQuery.hasOne();
	}

	public static Query hasTwo() {
		return SizeQuery.hasTwo();
	}

	public static Query hasZeroOrOne() {
		return SizeQuery.hasZeroOrOne();
	}

	public static Query hasOneOrMany() {
		return SizeQuery.hasOneOrMany();
	}

	public static Query hasZeroOrMany() {
		return SizeQuery.hasZeroOrMany();
	}

	// Integer counting
	//

	public static Query hasMinCount(int min) {
		return CountQuery.hasMinCount(min);
	}

	public static Query hasMaxCount(int max) {
		return CountQuery.hasMaxCount(max);
	}

	public static Query countInRange(int min, int max) {
		return CountQuery.countInRange(min, max);
	}

	public static Query countInRange(IntegerRange range) {
		return CountQuery.countInRange(range);
	}

	public static Query hasCount(int size) {
		return CountQuery.hasCount(size);
	}

	// Selecting
	//

	public static SelectQuery select(Multiplicity multiplicity, Query query) {
		return SelectQuery.select(multiplicity, query);
	}

	public static SelectQuery select(Multiplicity multiplicity) {
		return SelectQuery.select(multiplicity);
	}

	public static SelectQuery select(IntegerRange multiplicity, Query query) {
		return SelectQuery.select(multiplicity, query);
	}

	public static SelectQuery select(IntegerRange multiplicity) {
		return SelectQuery.select(multiplicity);
	}

	public static SelectQuery selectZeroOrOne(Query query) {
		return SelectQuery.selectZeroOrOne(query);
	}

	public static SelectQuery selectZeroOrOne() {
		return SelectQuery.selectZeroOrOne();
	}

	public static SelectQuery selectZeroOrMany(Query query) {
		return SelectQuery.selectZeroOrMany(query);
	}

	public static SelectQuery selectZeroOrMany() {
		return SelectQuery.selectZeroOrMany();
	}

	public static SelectQuery selectAny(Query query) {
		return SelectQuery.selectAny(query);
	}

	public static SelectQuery selectAny() {
		return SelectQuery.selectAny();
	}

	public static SelectQuery selectOne(Query query) {
		return SelectQuery.selectOne(query);
	}

	public static SelectQuery selectOne() {
		return SelectQuery.selectOne();
	}

	public static SelectQuery selectOneOrMany(Query query) {
		return SelectQuery.selectOneOrMany(query);
	}

	public static SelectQuery selectOneOrMany() {
		return SelectQuery.selectOneOrMany();
	}

	// Graphs
	//

	public static GraphLogQuery logGraph(String prefix) {
		return GraphLogQuery.logGraph(prefix);
	}

	public static GraphLogQuery logGraph() {
		return GraphLogQuery.logGraph();
	}

//	public static GraphVisualiseQuery visualiseGraph(String prefix) {
//		return GraphVisualiseQuery.visualiseGraph(prefix);
//	}

//	public static GraphVisualiseQuery visualiseGraph() {
//		return GraphVisualiseQuery.visualiseGraph();
//	}

	// // Models
	// //
	//
	// public static Query compilesWithElementModel(ElementModel elementModel) {
	// return ElementModelQuery.compliesWithElementModel(elementModel);
	// }
	//
	// public static Query compilesWithNodeModel(NodeModel nodeModel) {
	// return NodeModelQuery.compliesWithNodeModel(nodeModel);
	// }

	// Elements
	//

	public static ElementLabel hasTheLabel(String... labels) {
		return ElementLabel.hasTheLabel(labels);
	}

	public static ElementName hasTheName(String... labels) {
		return ElementName.hasTheName(labels);
	}

	public static Query hasProperty(String key) {
		return ElementProperty.hasProperty(key);
	}

	public static Query hasProperty(String key, Query query) {
		return ElementProperty.hasProperty(key, query);
	}

	public static Query hasOptionalProperty(String key, Query query) {
		return ElementProperty.hasOptionalProperty(key, query);
	}

	public static Query hasProperty(String key, Object value) {
		return ElementProperty.hasProperty(key, value);
	}

	public static Query getProperty(String key, Object defaultValue) {
		return ElementProperty.getProperty(key, defaultValue);
	}

	public static Query getProperty(String key) {
		return ElementProperty.getProperty(key);
	}

	// Nodes
	//

	public static Query inEdges() {
		return NodeEdges.inEdges();
	}

	public static Query inEdges(Query query) {
		return NodeEdges.inEdges(query);
	}

	public static Query outEdges() {
		return NodeEdges.outEdges();
	}

	public static Query outEdges(Query query) {
		return NodeEdges.outEdges(query);
	}

	// public static Query edges() {
	// return NodeEdges.edges();
	// }

	// public static Query edges(Query query) {
	// return NodeEdges.edges(query);
	// }

	public static Query edges(Direction direction) {
		return NodeEdges.edges(direction);
	}

	public static Query edges(Direction direction, Query query) {
		return NodeEdges.edges(direction, query);
	}

//	public static Query logNode(String prefix) {
//		return NodeLog.logNode(prefix);
//	}
//
//	public static Query logNode() {
//		return NodeLog.logNode("");
//	}

	public static Query isRoot() {
		return NodeCharacteristics.isRoot();
	}

	public static Query matchesRef(String reference) {
		return NodeReferenceQuery.matchesRef(reference);
	}

	public static Query isNode(Node node) {
		return IsNode.isNode(node);
	}

	public static HasEdges hasEdges(Direction direction, Query endNodeQuery, Multiplicity endNodeMultiplicity) {
		return HasEdges.hasEdges(direction, endNodeQuery, endNodeMultiplicity);
	}

	public static HasEdges hasInEdges(Query endNodeQuery, Multiplicity endNodeMultiplicity) {
		return HasEdges.hasInEdges(endNodeQuery, endNodeMultiplicity);
	}

	public static HasEdges hasOutEdges(Query endNodeQuery, Multiplicity endNodeMultiplicity) {
		return HasEdges.hasOutEdges(endNodeQuery, endNodeMultiplicity);
	}

	public static Trees parent() {
		return Trees.parent();
	}

	public static Trees root() {
		return Trees.root();
	}

	public static Trees parent(Query query) {
		return Trees.parent(query);
	}

	public static Trees children() {
		return Trees.children();
	}

	public static Trees childTree() {
		return Trees.childTree();
	}

	public static Trees children(Query query) {
		return Trees.children(query);
	}

	public static Trees childTree(Query query) {
		return Trees.childTree(query);
	}

	public static HasParent hasParent(Query query) {
		return HasParent.hasParent(query);
	}

	public static HasParent hasParent() {
		return HasParent.hasParent();
	}

//	public static IsClass isGridNode() {
//		return IsClass.isClass(GridNode);
//	}

	public static IsClass isNodeOfType(Class<?> theClass) {
		return IsClass.isClass(theClass);
	}

	// Node Lists
	//

	public static Query hasOnlyNodes(Query nodeQuery) {
		return HasOnlyNodes.hasOnlyNodes(nodeQuery);
	}

	public static Query nodeListInEdges() {
		return NodeListEdges.inEdges();
	}

	public static Query nodeListOutEdges() {
		return NodeListEdges.outEdges();
	}

	// public static Query nodeListEdges() {
	// return NodeListEdges.edges();
	// }

	public static Query nodeListEdges(Direction direction) {
		return NodeListEdges.edges(direction);
	}

	// Edges
	//
	public static Query logEdge(String prefix) {
		return EdgeLog.logEdge(prefix);
	}

	public static Query logEdge() {
		return EdgeLog.logEdge();
	}

	public static Query startNode() {
		return EdgeNodes.startNode();
	}

	public static Query endNode() {
		return EdgeNodes.endNode();
	}

	public static Query otherNode(Node refNode) {
		return EdgeNodes.otherNode(refNode);
	}

	public static Query bothNode() {
		return EdgeNodes.bothNodes();
	}

	public static Query hasStartNode(Query toNodeQuery) {
		return EdgeHasNode.hasStartNode(toNodeQuery);
	}

	public static Query hasEndNode(Query toNodeQuery) {
		return EdgeHasNode.hasEndNode(toNodeQuery);
	}

	public static Query hasOtherNode(Query toNodeQuery, Node refNode) {
		return EdgeHasNode.hasOtherNode(toNodeQuery, refNode);
	}

	public static Query hasBothNodes(Query toNodeQuery) {
		return EdgeHasNode.hasBothNodes(toNodeQuery);
	}

	public static Query isEdge(Edge edge) {
		return IsEdge.isEdge(edge);
	}

	// Edge Lists
	//

	public static Query edgeListStartNodes() {
		return EdgeListNodes.startNodes();
	}

	public static Query edgeListEndNodes() {
		return EdgeListNodes.endNodes();
	}

	public static Query edgeListOtherNodes(Node refNode) {
		return EdgeListNodes.otherNodes(refNode);
	}

	public static Query edgeListBotheNodes() {
		return EdgeListNodes.bothNodes();
	}

	// Primitives
	//

	public static Query isClass(Class<?>... classList) {
		return IsClass.isClass(classList);
	}

	public static Query dateInRange(Date min, Date max) {
		return IsDate.dateInRange(min, max);
	}

	public static Query doubleInRange(double min, double max) {
		return IsDouble.doubleInRange(min, max);
	}

	public static Query isBoolean() {
		return IsBoolean.isBoolean();
	}

	public static Query isDouble() {
		return IsDouble.doubleInRange(-Double.MAX_VALUE, Double.MAX_VALUE);
	}

	public static Query floatInRange(float min, float max) {
		return IsFloat.floatInRange(min, max);
	}

	public static Query isFloat() {
		return IsFloat.floatInRange(-Float.MAX_VALUE, Float.MAX_VALUE);
	}

	public static Query integerInRange(int min, int max) {
		return IsInteger.integerInRange(min, max);
	}

	public static Query isInteger() {
		return IsInteger.integerInRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static Query longInRange(long min, long max) {
		return IsLong.longInRange(min, max);
	}

	public static Query isLong() {
		return IsLong.longInRange(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

	public static Query isString() {
		return IsString.isString();
	}

	// Strings
	//

	public static StringLength length() {
		return StringLength.length();
	}

	public static ClassQuery stringIsClass(Class<?> parentClass) {
		return ClassQuery.stringIsClass(parentClass);
	}

	public static ClassQuery stringIsClass(String parentClassName) {
		return ClassQuery.stringIsClass(parentClassName);
	}

	public static Query containsSubstring(String str) {
		return ContainsSubstring.containsSubstring(str);
	}

	public static DateString isDateString() {
		return DateString.isDate();
	}

	public static DoubleString isDoubleString() {
		return DoubleString.isDouble();
	}

	public static Query endsWith(String str) {
		return EndsWith.endsWith(str);
	}

	public static EnumerationString isEnum(String... valueList) {
		return EnumerationString.isEnum(valueList);
	}

	public static EnumerationString isEnum(Enum<?>... enumList) {
		return EnumerationString.isEnum(enumList);
	}

	public static FileQuery isFileName() {
		return FileQuery.isFileName();
	}

	public static FloatString isFloatString() {
		return FloatString.isFloat();
	}

	public static InetAddressString isInetAddress() {
		return InetAddressString.isInetAddress();
	}

	public static IntegerString isIntegerString() {
		return IntegerString.isInteger();
	}

	public static LongString isLongString() {
		return LongString.isLong();
	}

	public static PatternString matchesPattern(String pattern) {
		return PatternString.matchesPattern(pattern);
	}

	public static Query startsWith(String str) {
		return StartsWith.startsWith(str);
	}

	public static UserNameQuery isUserName() {
		return UserNameQuery.isUserName();
	}

//	public static VersionString asVersion() {
//		return VersionString.asVersion();
//	}
//
//	public static VersionString isVersionString() {
//		return VersionString.isVersion();
//	}

	public static IsStringList isStringList() {
		return IsStringList.isStringList();
	}

}
