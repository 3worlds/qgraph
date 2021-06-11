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
package au.edu.anu.rscs.aot.old.queries.base.primitive;

import static au.edu.anu.rscs.aot.util.NumberRange.*;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class IsString extends Query {

	private int minLength;
	private int maxLength;

	public IsString(int minLength, int maxLength) {
		this.minLength = minLength;
		this.maxLength = maxLength;
	}

	public static Query stringOfLength(int minLength, int maxLength) {
		return new IsString(minLength, maxLength);
	}

	public static Query isString() {
		return new IsString(0, Integer.MAX_VALUE);
	}

	public String toString() {
		if (minLength == 0 && maxLength == Integer.MAX_VALUE)
			return "[isString]";
		else
			return "[isString with length in " + range(minLength, maxLength) + "]";
	}


	@Override
	public Query process(Object item) {
		defaultProcess(item);
		if (item instanceof String) {
			String localItem = (String)item;
			satisfied = (localItem.length() >= minLength && localItem.length() <= maxLength);
		} else
			satisfied = false;
		return this;
	}

}