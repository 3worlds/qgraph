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
package au.edu.anu.qgraph.queries.base.primitive;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>Check if an object is a {@link float}, or if it is within a given range.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Object}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input is not a {@code Float}</li>
 * <li>input value is not the within the range passed in the constructor</li>
 * </ol></dd> 
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#isFloat() CoreQueries.isFloat()
 * @see au.edu.anu.qgraph.queries.CoreQueries#floatInRange(float, float) CoreQueries.floatInRange(...)
 *
 */

public class IsFloat extends QueryAdaptor {

	private float min;
	private float max;

	/**
	 * Constructor with a range
	 * @param min the lower end of the range
	 * @param max  the upper end of the range
	 */
	public IsFloat(float min, float max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!(input instanceof Float)) {
			errorMsg = "Expected '"+Float.class.getName()+"' but found '" + input.getClass().getName() + "'.";
			return this;
		}
		Float localItem = (Float) input;
		if (localItem > max || localItem < min) 
			errorMsg = "Expected Float to be in range ']" + min + ".." + max + "[' but found '" + localItem + "'";
		return this;
	}

}
