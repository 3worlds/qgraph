package au.edu.anu.rscs.aot.queries.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * A query to select items based on multiplicities.
 * 
 * @author Shayne Flint - 28/3/2012
 *
 */
// Tested OK with version 0.0.1 on 5/12/2018 (using Shayne's test suite)
public class SelectQuery extends QueryAdaptor {
	private Queryable theQuery;
	private boolean exclusive;// all items must match query
	private IntegerRange multiplicity;
	private boolean returnMany;

	/**
	 * Constructor with a default multiplicity of {@code 0..*}
	 */
	public SelectQuery() {
		multiplicity = new IntegerRange(0, Integer.MAX_VALUE);
		returnMany = true;
		exclusive = false;
	}

	/**
	 * Only {@link Collection} arguments will be checked.
	 */
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
				errorMsg = "Expected only items that match '" 
					+ theQuery.getClass().getSimpleName() + "' but found "
					+ (originalSize - count) + " unexpected items.";
				return this;
			}

		if (!multiplicity.inRange(count)) {
			result = null;
			errorMsg = "Expected " + multiplicity + " item(s) matching " 
				+ theQuery + " but found " + count + ".";
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

	@Override
	public String toString() {
		String resultStr = "[Select";
		if (exclusive)
			resultStr = resultStr + " only";
		resultStr = resultStr + " " + multiplicity + " items";
		if (returnMany)
			resultStr = resultStr + " (return as list)";
		else
			resultStr = resultStr + " (return as item)";
		resultStr = resultStr + " which match " + theQuery + ", result=" + result 
			+ ", satisfied=" + (errorMsg == null) + "]";
		return resultStr;
	}

	// ------------- Fluid interface ------
	/**
	 * Add a query to check on all {@code input} objects 
	 * @param q the query
	 * @return this instance for agile programming
	 */
	public SelectQuery query(Queryable q) {
		theQuery = q;
		return this;
	}

	/**
	 * Set this query to fail if returning more than one object
	 * @return this instance for agile programming
	 */
	public SelectQuery returnOne() {
		returnMany = false;
		return this;
	}

	/**
	 * Set this query to fail if returning one or zero objects
	 * @return this instance for agile programming
	 */
	public SelectQuery returnMany() {
		returnMany = true;
		return this;
	}

	/**
	 * Set the lower end of the multiplicity range
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SelectQuery min(int m) {
		multiplicity.setFirst(m);
		return this;
	}

	/**
	 * Set the upper end of the multiplicity range
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SelectQuery max(int m) {
		multiplicity.setLast(m);
		return this;
	}

	/**
	 * Set the mutiplicity range
	 * @param mult one of the four possible values of the {@link Multiplicity} enum.
	 * @return this instance for agile programming
	 */
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

	/**
	 * Set the query to check all items
	 * @return this instance for agile programming
	 */
	public SelectQuery exclusive() {
		exclusive = true;
		return this;
	}

	/**
	 * Set the query to stop cecking after the first failure
	 * @return this instance for agile programming
	 */
	public SelectQuery nonExclusive() {
		exclusive = false;
		return this;
	}

}
