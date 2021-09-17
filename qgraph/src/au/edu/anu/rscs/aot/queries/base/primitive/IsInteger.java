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

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class IsInteger extends QueryAdaptor {

	private int min;
	private int max;

	public IsInteger(int min, int max) {
		this.min = min;
		this.max = max;
	}

	public static Queryable integerInRange(int min, int max) {
		return new IsInteger(min, max);
	}

	public static Queryable isInteger() {
		return new IsInteger(Integer.MIN_VALUE, Integer.MAX_VALUE);
	}

//	public String toString() {
//		return "[Must be Integer in " + min + ".." + max + "]";
//	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Integer)) {
			errorMsg = "Expected 'Integer' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Integer localItem = (Integer) input;
		if (localItem > max || localItem < min)
			errorMsg = "Expected Integer to be in range ']" + min + ".." + max + "[' but found '" + localItem + "'";
		return this;
	}

}
