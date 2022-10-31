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
package au.edu.anu.qgraph.queries.graph.element;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;
import fr.cnrs.iees.omugi.graph.ReadOnlyDataHolder;
import fr.cnrs.iees.omugi.properties.ReadOnlyPropertyList;

/**
 * <p>A {@link Queryable} to check that an object has a property and (optionally) a value.</p>
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>{@link fr.cnrs.iees.omugi.properties.ReadOnlyPropertyList ReadOnlyPropertyList} or 
 * {@link fr.cnrs.iees.omugi.graph.ReadOnlyDataHolder ReadOnlyDataHolder} (remember that
 * {@link fr.cnrs.iees.omugi.properties.SimplePropertyList SimplePropertyList} and 
 * {@link fr.cnrs.iees.omugi.graph.DataHolder DataHolder} are descendants of these, and as such
 * also constitute valid input)</dd>
 * <dt>Type of result</dt>
 * <dd>the property value, if {@link ElementProperty#getValue() getValue()} has been called. In this
 * case, the returned value is equal to the argument passed to {@link ElementProperty#defaultValue(Object) defaultValue(...)}
 * if the property did not exist in input.
 *  Otherwise no result</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input does not have a property named as the argument passed to 
 * {@link ElementProperty#key(String) key(...)} <em>and</em> {@link ElementProperty#optional() optional()}
 * has not been called (i.e., the property is required but not found)</li>
 * <li>input property named as the argument passed to {@code key(...)} does not satisfy the query
 * argument passed to {@link ElementProperty#query(Queryable) query(...)}</li>
 * <li>input property named as the argument passed to {@code key(...)} value does not match
 * the valued passed as argument to {@link ElementProperty#value(Object) value(...)}</li>
 * </ol></dd> 
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasProperty(String) 					CoreQueries.hasProperty(String)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasProperty(String, Object) 			CoreQueries.hasProperty(String, Object)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasProperty(String, Queryable) 			CoreQueries.hasProperty(String, Queryable)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasOptionalProperty(String, Queryable) 	CoreQueries.hasOptionalProperty(String, Queryable)
 * @see au.edu.anu.qgraph.queries.CoreQueries#getProperty(String, Object) 			CoreQueries.getProperty(String, Object)
 * @see au.edu.anu.qgraph.queries.CoreQueries#getProperty(String) 					CoreQueries.getProperty(String)
 * 
 * @author Shayne Flint - 26/3/2012<br/>
 * 	refactored by Jacques Gignoux - 15/4/2019 <br/>
 * an Iand Davies - Feb. 2021
 * 
 *
 */
// Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
// Tested OK with version 0.1.1 on 21/5/2019 (NB the test suite is not complete, only checks hasProperty)
public class ElementProperty extends QueryAdaptor {
	private String key;
	private Object defaultValue;
	private Object value;
	private Queryable query;
	private boolean getValue;
	private boolean optional;

	/**
	 * Only {@link ReadOnlyDataHolder} and {@link ReadOnlyPropertyList} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		ReadOnlyPropertyList localItem = null;
		// fix by JG 15/4/2019 - this should enable one to treat Nodes, Edges, TreeNodes
		// as well as property lists
		if (input instanceof ReadOnlyPropertyList)
			localItem = (ReadOnlyPropertyList) input;
		else if (input instanceof ReadOnlyDataHolder)
			localItem = ((ReadOnlyDataHolder) input).properties();
		Object propertyValue=null;
		if (defaultValue == null)
			propertyValue = localItem.getPropertyValue(key);
		else if (localItem.hasProperty(key))
			propertyValue = localItem.getPropertyValue(key);
		else
			propertyValue = defaultValue;

		boolean ok = true;
		if (propertyValue == null) {
			if (!optional)
				ok = false;
		} else {
			if (query != null) {
				query.submit(propertyValue);
				if (!query.satisfied())
					ok = false;
			}
			if (value != null)
				if (!propertyValue.equals(value))
					ok = false;

			if (getValue)
				result = propertyValue;
		}
		if (!ok) {
			StringBuilder sb = new StringBuilder();
			if (optional)
				sb.append("Can ");
			else
				sb.append("Must ");
			sb.append("have property '").append(key);
			if (value != null)
				sb.append("='").append(value).append("'");

			if (defaultValue != null)
				sb.append(" [").append(defaultValue).append("]");

			if (query != null) {
				if (query.satisfied())
					sb.append(" which satisfies [").append(query);
				else
					sb.append(" which satisfies [").append(query.errorMsg());
			}
			sb.append("]");
			errorMsg = sb.toString();

		}
		return this;
	}

	// Fluid interface

	/**
	 * Set the property name to compare to.
	 * @param k the property name
	 * @return this instance for agile programming
	 */
	public ElementProperty key(String k) {
		key = k;
		return this;
	}

	/**
	 * Set the default value: if the property is absent in the property list, it will be set to
	 * this value
	 * @param v the property value
	 * @return this instance for agile programming
	 */
	public ElementProperty defaultValue(Object v) {
		defaultValue = v;
		return this;
	}

	/**
	 * Set the query to apply to the property value.
	 * @param q the query
	 * @return this instance for agile programming
	 */
	public ElementProperty query(Queryable q) {
		query = q;
		return this;
	}

	/**
	 * Set the property value
	 * @param v the property value
	 * @return  this instance for agile programming
	 */
	public ElementProperty value(Object v) {
		value = v;
		return this;
	}

	/**
	 * If the property value should be returned as a result
	 * @return this instance for agile programming
	 */
	public ElementProperty getValue() {
		getValue = true;
		return this;
	}

	/**
	 * If the property is optional
	 * @return this instance for agile programming
	 */
	public ElementProperty optional() {
		optional = true;
		return this;
	}

}
