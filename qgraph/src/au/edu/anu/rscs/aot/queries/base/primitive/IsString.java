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
package au.edu.anu.rscs.aot.queries.base.primitive;

import static au.edu.anu.rscs.aot.util.NumberRange.*;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Check if an object is a {@link String}, or if its length is within a given range.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Object}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsString extends QueryAdaptor {

	private int minLength;
	private int maxLength;

	/**
	 * Constructor with length range
	 * @param min the lower end of the range
	 * @param max  the upper end of the range
	 */
	public IsString(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

<<<<<<< HEAD
//	public static Queryable stringOfLength(int minLength, int maxLength) {
//		return new IsString(minLength, maxLength);
//	}
//
//	public static Queryable isString() {
//		return new IsString(0, Integer.MAX_VALUE);
//	}

//	public String toString() {
//		if (minLength == 0 && maxLength == Integer.MAX_VALUE)
//			return "[isString]";
//		else
//			return "[isString with length in " + range(minLength, maxLength) + "]";
//	}
=======
	@Override
	public String toString() {
		if (minLength == 0 && maxLength == Integer.MAX_VALUE)
			return "[isString]";
		else
			return "[isString with length in " + range(minLength, maxLength) + "]";
	}
>>>>>>> branch 'master' of git@gitlab.anu.edu.au:ThreeWorlds/qgraph.git

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof String)) {
			errorMsg = "Expected 'String' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		String localItem = (String) input;
		if (localItem.length() < minLength || localItem.length() > maxLength)
			errorMsg = "Expected String length to be in range ']" + minLength + ".." + maxLength + "[' but found '"
					+ localItem.length() + "'";
		return this;
	}

}
