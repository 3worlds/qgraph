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
package au.edu.anu.rscs.aot.queries.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * <p>A query to select items based on multiplicities. It is a powerful way to filter the results of
 * {@link SequenceQuery#get(Object, Queryable...) SequenceQuery.get(...)}.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Collection}{@code <Object>}</dd>
 * <dt>Type of result</dt>
 * <dd>{@link Collection}{@code <Object>}, possibly empty (depending on the filter Query attached
 * with method {@link au.edu.anu.rscs.aot.queries.base.SelectQuery#query(Queryable) query(...)}). <br/>
 * {@link Object} or {@code null}</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>one of the objects of the input collection does not satisfy the query set with {@link #query(Queryable)}
 * <em>and</em> {@code exclusive = true} (as set with {@link #exclusive()})</li>
 * <li>the number of objects that satisfy the query set with {@link #query(Queryable)} is not
 * within the multiplicity range (as set with {@link #multiplicity(Multiplicity)})</li>
 * </ol></dd>
 * </dl>
 * 
 * @author Shayne Flint - 28/3/2012
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectOne() CoreQueries.selectOne()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectOne(Queryable) CoreQueries.selectOne(Queryable)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectZeroOrOne() CoreQueries.selectZeroOrOne()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectZeroOrOne(Queryable) CoreQueries.selectZeroOrOne(Queryable)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectZeroOrMany() CoreQueries.selectZeroOrMany()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectZeroOrMany(Queryable) CoreQueries.selectZeroOrMany(Queryable)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectOneOrMany() CoreQueries.selectOneOrMany()
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#selectOneOrMany(Queryable) CoreQueries.selectOneOrMany(Queryable)
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
