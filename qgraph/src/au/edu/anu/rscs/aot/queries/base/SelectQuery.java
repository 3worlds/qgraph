package au.edu.anu.rscs.aot.queries.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;

public class SelectQuery extends QueryAdaptor {
	private Queryable theQuery;
	private boolean exclusive;// all items must match query
	private IntegerRange multiplicity;
	private boolean returnMany;

	public SelectQuery() {
		multiplicity = new IntegerRange(0, Integer.MAX_VALUE);
		returnMany = true;
		exclusive = false;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		@SuppressWarnings("unchecked")
		Collection<Object> list = (Collection<Object>) input;
		int originalSize = list.size();
		Collection<Object> localItem;
		if (theQuery == null)
			localItem = list;
		else {
			localItem = new ArrayList<Object>();
			for (Object item : list) {
				theQuery.submit(item);
				if (theQuery.satisfied())
					localItem.add(item);
			}
		}
		int count = localItem.size();

		if (!theQuery.satisfied() && exclusive)
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

		// original returns not satisfied but what is the message??
		errorMsg = " debugging: not satisfied but what is the message? Should this be satisfied == true?";
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
	public SelectQuery query(Queryable q) {
		theQuery = q;
		return this;
	}

	public SelectQuery returnOne() {
		returnMany = false;
		return this;
	}

	public SelectQuery returnMany() {
		returnMany = true;
		return this;
	}

	public SelectQuery min(int m) {
		multiplicity.setFirst(m);
		return this;
	}

	public SelectQuery max(int m) {
		multiplicity.setLast(m);
		return this;
	}

	public SelectQuery multiplicity(Multiplicity mult) {
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

	public SelectQuery exclusive() {
		exclusive = true;
		return this;
	}

	public SelectQuery nonExclusive() {
		exclusive = false;
		return this;
	}

}
