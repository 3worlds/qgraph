package au.edu.anu.rscs.aot.queries;

import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.NotQuery;
import au.edu.anu.rscs.aot.queries.base.OrQuery;
import au.edu.anu.rscs.aot.queries.base.SelectQuery;
import au.edu.anu.rscs.aot.queries.base.SizeQuery;
import au.edu.anu.rscs.aot.queries.base.XorQuery;
import au.edu.anu.rscs.aot.queries.base.primitive.IsClass;
import au.edu.anu.rscs.aot.queries.base.string.StartsWith;
import au.edu.anu.rscs.aot.queries.graph.EdgeNodeSelection;
import au.edu.anu.rscs.aot.queries.graph.edge.EdgeListNodes;
import au.edu.anu.rscs.aot.queries.graph.edge.EdgeNodes;
import au.edu.anu.rscs.aot.queries.graph.element.ElementLabel;
import au.edu.anu.rscs.aot.queries.graph.element.ElementName;
import au.edu.anu.rscs.aot.queries.graph.element.ElementProperty;
import au.edu.anu.rscs.aot.queries.graph.node.NodeEdges;
import au.edu.anu.rscs.aot.queries.graph.node.NodeListEdges;
import au.edu.anu.rscs.aot.queries.graph.node.TreeQuery;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;
import fr.cnrs.iees.graph.Direction;
import fr.cnrs.iees.graph.Node;

/**
*
* This class is a convenience class of static methods that brings together queries of all types
*
* @author Shayne Flint - 26/3/2012<br/>
* 			refactored by Ian Davies - feb. 2021
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

	// Strings TODO
	public static Queryable startsWith(String s) {
		return new StartsWith(s);
	}

	// Size
	public static Queryable inRange(int min, int max) {
		return new SizeQuery().min(min).max(max);
	}

	public static Queryable hasMin(int min) {
		return new SizeQuery().min(min);
	}

	public static Queryable hasMax(int max) {
		return new SizeQuery().max(max);
	}

	public static Queryable inRange(IntegerRange range) {
		return new SizeQuery().min(range.getFirst()).max(range.getLast());
	}

	// ------------------   Elements
	//
	public static Queryable hasTheName(String... names) {
		return new ElementName(names);
	}

	public static Queryable hasTheLabel(String... labels) {
		return new ElementLabel(labels);
	}

	/**
	 * Checks that a property exists in the input
	 * @param k the name of the property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k) {
		return new ElementProperty().key(k);
	}

	/**
	 * Checks that a property exists and has the specified value in the input
	 * @param k the name of the property
	 * @param v the expected value
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k, Object v) {
		return new ElementProperty().key(k).value(v);
	}

	/**
	 * Checks that a property exists and satisfied the specified query
	 * @param k the name of the property
	 * @param q the query to check for this property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasProperty(String k, Queryable q) {
		return new ElementProperty().key(k).query(q);
	}

	/**
	 * Checks that an <em>optional</em> property exists and satisfied the specified query
	 * @param k the name of the property
	 * @param q the query to check for this property
	 * @return the resulting ElementProperty query
	 */
	public static Queryable hasOptionalProperty(String k, Queryable q) {
		return new ElementProperty().key(k).query(q).optional();
	}

	/**
	 * Get the value of the specified property, and use the specified default value if
	 * property not set
	 * @param k the name of the property
	 * @param dv the value to set if the property was empty
	 * @return the resulting ElementProperty query
	 */
	public static ElementProperty getProperty(String k, Object dv) {
		return new ElementProperty().key(k).defaultValue(dv).getValue();
	}
	
	/**
	 * Get the value of the specified property
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

	public static Queryable edgeListStartNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.START);
	}

	public static Queryable edgeListEndNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.END);
	}

	public static Queryable edgeListBothNodes() {
		return new EdgeListNodes().edgeNodeSelection(EdgeNodeSelection.BOTH);
	}

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

	// ------------------------- primitives
	public static Queryable isClass(Class<?>... classList) {
		return new IsClass(classList);
	}
}
