/**
 * <p><strong>qgraph</strong> defines a graph querying system to use with <strong>omugi</strong>.</p>
 * 
 * <p>This package defines one ancestor interface, {@link au.edu.anu.qgraph.queries.Queryable Queryable}, and many implementations of it.
 * {@code Queryable} descendants (we call them <em>queries</em>) can be used (1) to check that elements of a graph verify some 
 * condition or (2) to collect parts of the graph according to some conditions. Queries can
 * be chained so that elaborate searches can be performed on a {@link fr.cnrs.iees.graph.Graph Graph}. This library
 * actually defines a language to travel a graph, pick information, get elements based on some
 * conditions, etc.</p>
 * 
 * <p>Of course the list of queries can be freely expanded <em>ad infinitum</em> by
 * writing implementations of {@code Queryable}.</p>
 * 
 * <h3>List of queries currently available</h3>
 * 
 * <table width="100%" border="1" >
 * 
 * <tr><td width="20%"><strong>query</strong></td> 
 * 	   <td><strong>function</strong></td>
 *     <td width="20%"><strong>applies to</strong></td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.AndQuery AndQuery}</td> 
 * 	   <td>check if all its sub-queries are satisfied</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.OrQuery OrQuery}</td> 
 * 	   <td>check if at least one of its sub-queries are satisfied</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.NotQuery NotQuery}</td> 
 * 	   <td>check if none of its sub-queries are satisfied</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.XorQuery XorQuery}</td> 
 * 	   <td>check if exactly one of its sub-queries are satisfied</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.CountQuery CountQuery}</td> 
 * 	   <td>check  that an integer ('count') is within a range</td>
 *     <td>{@link java.lang.Integer Integer}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.SizeQuery SizeQuery}</td> 
 * 	   <td>check  that  the size of an object is within a specified range</td>
 *     <td>{@link fr.cnrs.iees.omhtk.Sizeable Sizeable}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.SelectQuery SelectQuery}</td> 
 * 	   <td>select objects of a collection according to a multiplicity, optionally using a query</td>
 *     <td>{@link java.util.Collection Collection}&lt;
 *     {@link java.lang.Object Object}&gt;</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.SequenceQuery SequenceQuery}</td> 
 * 	   <td>select objects that satisfy a series of queries chained after the other, starting on a single object</td>
 *     <td>{@link  java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.IsQuery IsQuery}</td> 
 * 	   <td>check  that  an object is a query</td>
 *     <td>{@link au.edu.anu.qgraph.queries.Queryable Queryable}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.ForAllQuery ForAllQuery}</td> 
 * 	   <td>apply a query on a collection of objects</td>
 *     <td>{@link java.lang.Iterable Iterable}&lt;
 *      {@link java.lang.Object Object}&gt;</td> <tr/>
 *      
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.IfThenQuery IfThenQuery}</td> 
 * 	   <td>test query. If satisfied, apply a query to the argument, if not, apply another one</td>
 *     <td>{@link  java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsClass IsClass}</td> 
 * 	   <td>check  that a class object is of a given class</td>
 *     <td>{@link java.lang.Class Class}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.IsInstanceOf IsInstanceOf}</td> 
 * 	   <td>check  that an object is of a given class</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.Value Value}</td> 
 * 	   <td>return its argument as a result</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsBoolean IsBoolean}</td> 
 * 	   <td>check if an object is a {@code boolean}</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsDate IsDate}</td> 
 * 	   <td>check if an object is a {@code Date} or if it is within a given date range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsDouble IsDouble}</td> 
 * 	   <td>check if an object is a {@code double} or if it is within a given range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsFloat IsFloat}</td> 
 * 	   <td>check if an object is a {@code float} or if it is within a given range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsInteger IsInteger}</td> 
 * 	   <td>check if an object is an {@code int} or if it is within a given range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsLong IsLong}</td> 
 * 	   <td>check if an object is a {@code long} or if it is within a given range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsString IsString}</td> 
 * 	   <td>check if an object is a {@code String} or if its length is within a given range</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.primitive.IsIntegerRange IsIntegerRange}</td> 
 * 	   <td>check if an object is an {@link IntegerRange}</td>
 *     <td>{@link java.lang.Object Object}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.string.StringLength StringLength}</td> 
 * 	   <td>get the length of a String</td>
 *     <td>{@link java.lang.String String}</td> <tr/>
 *     
 *     
 *     
 *     
 *     
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.base.string.StartsWith StartsWith}</td> 
 * 	   <td>check  that a String starts with a given String</td>
 *     <td>{@link java.lang.String String}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.element.ElementLabel ElementLabel}</td> 
 * 	   <td>check  that a graph element matches one of a serie of class identifiers/labels</td>
 *     <td>{@link fr.cnrs.iees.graph.Specialized Specialized}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.element.ElementName ElementName}</td> 
 * 	   <td>check  that a graph element matches one of a serie of identifiers/names</td>
 *     <td>{@link fr.cnrs.iees.identity.Identity Identity}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.element.ElementProperty ElementProperty}</td> 
 * 	   <td>check  that a graph element has a given property and optionally a value for this property</td>
 *     <td>{@link fr.cnrs.iees.properties.ReadOnlyPropertyList ReadOnlyPropertyList},
 *     {@link fr.cnrs.iees.graph.ReadOnlyDataHolder ReadOnlyDataHolder},</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.node.NodeEdges NodeEdges}</td> 
 * 	   <td>select graph edges linked to a node</td>
 *     <td>{@link fr.cnrs.iees.graph.Node Node}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.node.NodeListEdges NodeListEdges}</td> 
 * 	   <td>select IN, OUT or all edges of a list of Nodes</td>
 *     <td>{@link java.lang.Iterable Iterable}&lt;
 *     {@link fr.cnrs.iees.graph.Node Node}&gt;</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.node.TreeQuery TreeQuery}</td> 
 * 	   <td>select parts of a tree starting at some node</td>
 *     <td>{@link fr.cnrs.iees.graph.TreeNode TreeNode}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.edge.EdgeNodes EdgeNodes}</td> 
 * 	   <td>select start, end, other-end or both-ends nodes of an edge</td>
 *     <td>{@link fr.cnrs.iees.graph.Edge Edge}</td> <tr/>
 *     
 * <tr><td>{@link au.edu.anu.qgraph.queries.graph.edge.EdgeListNodes EdgeListNodes}</td> 
 * 	   <td>select start, end, other-end or both-ends nodes of a list of edges</td>
 *     <td>{@link java.lang.Iterable Iterable}&lt;
 *     {@link fr.cnrs.iees.graph.Edge Edge}&gt;</td> <tr/>
 *     
 * </table>
 * 
 * <p>Static methods in the {@link au.edu.anu.qgraph.queries.CoreQueries CoreQueries} class implement
 * the most common use cases of queries for a graph.</p>
 * 
 * @author Jacques Gignoux - 13 sept. 2021
 *
 */
package au.edu.anu.qgraph;

import au.edu.anu.omhtk.util.IntegerRange;
