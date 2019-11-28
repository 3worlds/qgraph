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
package au.edu.anu.rscs.aot.queries;

import java.util.logging.Logger;

import au.edu.anu.rscs.aot.QGraphException;
import fr.ens.biologie.generic.utils.Logging;

/**
 * <p>The ancestor class for the Query system.</p>
 * <h3>Usage:</h3>
 * <p>All Queries work in the same way: </p>
 * <ul>
 * <li>A subclass of Query, matching the particular condition to fulfill, is constructed with some parameters;</li>
 * <li>Later on, a call to {@code Query.satisfied(Object)} will check the condition
 * and return true if the Query was satistfied;</li>
 * <li>Alternatively, a call to {@code Query.check(Object)} can be made, that will return
 * an Exception if the query is not satisfied.</li>
 * </ul>
 * <p>Most Query subclasses come with static methods implementing the most common use cases for their class.</p>
 * <p>Queries in the base package apply to non-graph objects.</p>
 * <p>The {@code process()} method parameter type is generic ({@link Object}), but for every 
 * particular Query a specific type is required (cf. the javadoc for each specific Query type).</p>
 * <p>Some queries compute a result, accessible through the {@code getResult()} method. The 
 * default return type is {@code Object}, but the actual return type has to be known (cf. the
 * javadoc for each specific Query type). A typical use case is returning a filtered list
 * when the input to {@code process()} is a list of items.</p>
 * 
 * @author Shayne Flint - 15/3/2012
 *
 */
public abstract class Query {

	private static Logger log = Logging.getLogger(Query.class);
	protected Object  result    = null;
	protected boolean satisfied = false;

	/**
	 * 
	 * @return the result computed by the Query
	 */
	public Object getResult() {
		return result;
	}

	/**
	 * 
	 * @return true if the Query was satisfied
	 */
	public boolean satisfied() {
		return satisfied;
	}

	/**
	 * Processes the Query constraint on the input 
	 * @param input the object to check
	 * @return this Query (for agile programming)
	 */
	public abstract Query process(Object input);

	/**
	 * Default processing, always returning satisfied = false and result = input.
	 * @param input the object to check
	 * @return this Query (for agile programming)
	 */
	protected Query defaultProcess(Object input) {
		//		this.input = input;
		result = input;
		satisfied = false;
		return this;
	}

	/**
	 * Calls {@code process(input)} and returns true if satisfied
	 * @param input the object to check
	 * @return true if the Query was satisfied
	 */
	public boolean satisfied(Object input) {
		process(input);
		return satisfied;
	}


	/**
	 * Calls {@code process(input)} and throws an Exception if not satisfied
	 * @param input the object to check
	 */
	public void check(Object item) {
		process(item);
		if (!satisfied()) {
			//			Log.log("Query.check failed - " + toString());
			failed(item);
		}
	}


	// Constraint failure messages
	//

	protected void failed(Object item) {
		String message = "'" + item.toString() + "' does not satisfy '" + toString() + "'";
		//		Log.log("Query: " + message);
		throw new QGraphException(message);
	}

	protected void failed(Object item, Exception e) {
		String message = "'" + item.toString() + "' does not satisfy '" + toString() + "' [" + e + "]";
		//		Log.log("Query: " + message);
		throw new QGraphException(message);
	}

	protected void failed(Object item, String msg) {
		String message = "'" + item.toString() + "' does not satisfy '" + toString() + "' [" + msg + "]";
		//		Log.log("Query: " + message);
		throw new QGraphException(message);
	}


	// Representation
	//

	public static String queryFailedStr = "X!";
	public String stateString() {
		if (satisfied)
			return ""; //"[Passed on " + input + "]";
		else
			return queryFailedStr; //"[Failed on " + input + "]";
	}

	public String toString() {
		return "[" + this.getClass().getName() + ", satisfied=" + satisfied + ", result=[" + result + "]]";
	}

	public String userString() {
		return "[" + stateString() + this.getClass().getName() + "]";
	}

	/**
	 * for debugging only
	 */
	public void log() {
		log.info(toString());
	}


	// Trace
	//

	private void trace(String indent, Query query, Object item) {
		query.process(item);
		if (query.satisfied())
			System.out.println("  " + indent + query);
		else
			System.out.println("X " + indent + query);
		if (query instanceof QueryList) {
			for (Query q : ((QueryList)query).queryList())
				trace("  ", q, item);
		}
	}

	public void trace(Object item) {
		trace("", this, item);
	}

}
