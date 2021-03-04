package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.ReadOnlyDataHolder;
import fr.cnrs.iees.properties.ReadOnlyPropertyList;

public class ElementProperty extends QueryAdaptor {
	private String key;
	private Object defaultValue;
	private Object value;
	private Queryable query;
	private boolean getValue;
	private boolean optional;

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
		Object propertyValue;
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

	public ElementProperty key(String k) {
		key = k;
		return this;
	}

	public ElementProperty defaultValue(Object v) {
		defaultValue = v;
		return this;
	}

	public ElementProperty query(Queryable q) {
		query = q;
		return this;
	}

	public ElementProperty value(Object v) {
		value = v;
		return this;
	}

	public ElementProperty getValue() {
		getValue = true;
		return this;
	}

	public ElementProperty optional() {
		optional = true;
		return this;
	}

}
