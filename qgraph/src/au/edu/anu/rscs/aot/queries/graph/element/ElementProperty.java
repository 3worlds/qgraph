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
package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.properties.ReadOnlyPropertyList;

/**
 * Checks that an object has a property and/or a value. The object must implement the
 * <em>ReadonlyPropertyList</em> interface (library <em>omugi</em>).
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
//Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
public class ElementProperty extends Query {

	private String key;
	private Object defaultValue;
	private Object value;
	private Query query;
	private boolean getValue;
	private boolean optional = false;

	public ElementProperty(String key) {
		this.key = key;
		this.defaultValue = null;
		this.value = null;
		this.query = null;
		this.getValue = false;
		this.optional = false;
	}


	public static ElementProperty hasProperty(String key) {
		return new ElementProperty(key);
	}

	public static ElementProperty hasProperty(String key, Object value) {
		return new ElementProperty(key).setValue(value);
	}

	public static ElementProperty hasProperty(String key, Query query) {
		return new ElementProperty(key).setQuery(query);
	}

	public static ElementProperty hasOptionalProperty(String key, Query query) {
		return new ElementProperty(key).setQuery(query).setOptional(true);
	}

	public static ElementProperty getProperty(String key, Object defaultValue) {
		return new ElementProperty(key).setDefaultValue(defaultValue).getValue(true);
	}

	public static ElementProperty getProperty(String key) {
		return getProperty(key, null);
	}

	public ElementProperty setQuery(Query c) {
		this.query = c;
		return this;
	}

	public ElementProperty setValue(Object value) {
		this.value = value;
		return this;
	}

	public ElementProperty getValue(boolean getValue) {
		this.getValue = getValue;
		return this;
	}

	public ElementProperty setDefaultValue(Object defaultValue) {
		this.defaultValue = defaultValue;;
		return this;
	}

	public ElementProperty setOptional(boolean state) {
		this.optional = state;;
		return this;
	}

	public String toString() {
		String result = "[" + this.stateString();
		if (optional)
			result = result + "can ";
		else
			result = result + "must ";
		result = result + "have property '" + key;
		if (value != null)
			result = result + "='" + value;
		result = result + "'";
		if (defaultValue != null)
			result = result + " [" + defaultValue + "]";
		if (query != null)
			result = result + " which satisfies " + query;
		return result + "]";
	}

	public String userString() {
		String result = "[" + this.stateString();
		if (optional)
			result = result + "can ";
		else
			result = result + "must ";
		result = result + "have property '" + key;
		if (value != null)
			result = result + "='" + value;
		result = result + "'";
		if (defaultValue != null)
			result = result + " [" + defaultValue + "]";
		if (query != null)
			result = result + " which satisfies " + query.userString();
		return result + "]";
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		ReadOnlyPropertyList localItem = (ReadOnlyPropertyList)item;

		Object propertyValue;
		if (defaultValue == null)
			propertyValue = localItem.getPropertyValue(key);
		else
			if (localItem.hasProperty(key))
				propertyValue = localItem.getPropertyValue(key);
			else
				propertyValue = defaultValue;

		satisfied = true;

		if (propertyValue == null) {
			if (!optional)
				satisfied = false;
		} else {
			if (query != null)
				if (!query.satisfied(propertyValue))
					satisfied = false;

			if (value != null)
				if (!propertyValue.equals(value))
					satisfied = false;

			if (getValue)
				result = propertyValue;
		}
		return this;
	}

	// TESTING
	//

//	public static void main(String[] args) {
//		TestGraph tg = new TestGraph();
//		AotNode n = tg.getNodeList().getReferenceNode();
//		ElementProperty ep = hasProperty("p1");
//		ep.check(n);
//		float p2 = (Float)SequenceQuery.get(n, getProperty("p2"));
//		System.out.println("Float p2 = " + p2);
//		ElementProperty ep2 = hasProperty("p1", 1234);
//		ep2.check(n);
//		ep2 = hasProperty("p1", 12345);
//		ep2.check(n);
//	}
}
