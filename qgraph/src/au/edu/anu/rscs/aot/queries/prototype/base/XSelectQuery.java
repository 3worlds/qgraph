package au.edu.anu.rscs.aot.queries.prototype.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.queries.prototype.queries.Queryable;
import au.edu.anu.rscs.aot.queries.prototype.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.util.IntegerRange;

public class XSelectQuery extends QueryAdaptor {
	private Queryable theQuery;
	private boolean exclusive;
	private IntegerRange multiplicity;
	private boolean returnMany;

	public XSelectQuery() {
		multiplicity = new IntegerRange(0, Integer.MAX_VALUE);
		returnMany = true;
		exclusive = false;
	}

	@Override
	public Queryable query(Object input) {
		initProcess(input, Collection.class);
		Collection<Object> list = (Collection<Object>) input;
		int originalSize = list.size();
		Collection<Object> localItem;
		if (theQuery == null)
			localItem = list;
		else {
			localItem = new ArrayList<Object>();
			for (Object item : list) {
				theQuery.query(item);
				if (theQuery.errorMsg() == null)
					localItem.add(item);
			}
		}
		int count = localItem.size();

		if (theQuery != null && exclusive)
			if (count != originalSize) {
				errorMsg = "Expected only items that match '" + theQuery.getClass().getSimpleName() + "' but found "
						+ (originalSize - count) + " unexpected items.";
				return this;
			}

		if (!multiplicity.inRange(count)) {
			result = null;
			errorMsg = "Expected " + multiplicity + " item(s) matching " + theQuery + " but found " + count + ".";
			return this;
		}

		if (returnMany) {
			result = localItem;
			return this;
		} else {
			Iterator<Object> localItemIterator = localItem.iterator();
			if (localItemIterator.hasNext()) {
				result = localItemIterator.next();
				return this;
			} else
				result = null;
		}

		// not satisfied by what is the message??
		errorMsg = "not satisfied but what is the message??";
		return this;
	}

	public String toString() {
		String resultStr = "[Select";

		if (exclusive)
			resultStr = resultStr + " only";

		resultStr = resultStr + " " + multiplicity + " items";

		if (returnMany)
			resultStr = resultStr + " (return as list)";
		else
			resultStr = resultStr + " (return as item)";

		resultStr = resultStr + " which match " + theQuery + ", result=" + result + ", satisfied=" + (errorMsg == null)
				+ "]";
		return resultStr;
	}

	// ------------- Fluid interface ------
	public XSelectQuery query(Queryable q) {
		theQuery = q;
		return this;
	}

	public XSelectQuery returnOne() {
		returnMany = false;
		return this;
	}

	public XSelectQuery returnMany() {
		returnMany = true;
		return this;
	}

	public XSelectQuery min(int m) {
		multiplicity.setFirst(m);
		return this;
	}

	public XSelectQuery max(int m) {
		multiplicity.setLast(m);
		return this;
	}

	public XSelectQuery multiplicity(Multiplicity mult) {
		switch (mult) {
		case ZERO_ONE:
			min(0);
			max(1);
			break;
		case ZERO_MANY:
			min(0);
			max(Integer.MAX_VALUE);
			break;
		case ONE:
			min(1);
			max(1);
			break;
		case ONE_MANY:
			min(1);
			max(Integer.MAX_VALUE);
			break;
		default:
			break;
		}
		return this;
	}

	public XSelectQuery exclusive() {
		exclusive = true;
		return this;
	}

	public XSelectQuery nonExclusive() {
		exclusive = false;
		return this;
	}

}
