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
package au.edu.anu.qgraph.queries.base;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import fr.ens.biologie.generic.Sizeable;

/**
 * Query testing if the size of a {@link Sizeable} object is within a specified
 * range.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Sizeable}</dd>
 * <dt>Type of result</dt>
 * <dd>Sizeable ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input size is not within the (integer) range set with the {@link #min(int)} and
 * {@link #max(int)} methods.</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#inRange(int, int) CoreQueries.inRange(int,int)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasMin(int) CoreQueries.hasMin(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasMax(int) CoreQueries.hasMax(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#inRange(au.edu.anu.rscs.aot.util.IntegerRange) CoreQueries.inRange(IntegerRange)
 *
 */
public class SizeQuery extends QueryAdaptor {
	private int min;
	private int max;

	/**
	 * Default constructor with the range [0;+∞[
	 */
	public SizeQuery() {
		this.min = 0;
		this.max = Integer.MAX_VALUE;
	}

	/**
	 * Only {@link Sizeable} arguments will be checked.
	 */
	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
		Sizeable localItem = (Sizeable) result;
		long size = localItem.size();
		boolean ok = (size >= min) && (size <= max);
		if (!ok)
			errorMsg = "Expected size of '" + localItem.getClass().getSimpleName() + "' to be within the range [" + min
					+ ".." + max + "] but found size of '" + localItem.size() + "'.";
		return this;
	}

	/**
	 * Set the range minimum value (default = 0)
	 * 
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery min(int m) {
		min = m;
		return this;
	}

	/**
	 * Set the range maximum value (default = {@code Integer.MAX_VALUE})
	 * 
	 * @param m the value
	 * @return this instance for agile programming
	 */
	public SizeQuery max(int m) {
		max = m;
		return this;
	}

}
