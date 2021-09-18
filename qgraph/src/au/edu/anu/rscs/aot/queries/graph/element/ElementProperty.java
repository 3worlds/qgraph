package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.ReadOnlyDataHolder;
import fr.cnrs.iees.properties.ReadOnlyPropertyList;

/**
 * <p>A {@link Query} to check that an object has a property and (optionally) a value.</p>
 * <dl>
 * <dt>Type of input to {@code process()}</dt>
 * <dd>{@link ReadOnlyPropertyList} or {@link ReadOnlyDataHolder} (remember that
 * {@link SimplePropertyList} and {@link DataHolder} are descendants of these, and as such
 * also constitute valid input)</dd>
 * <dt>Type of result</dt>
 * <dd>the property value, if applicable - otherwise no result</dd>
 * </dl>
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
