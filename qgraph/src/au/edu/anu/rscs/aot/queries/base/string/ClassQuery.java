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
package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <p>Check if a {@link String} represents the class passed to the constructor.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code String}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd><ol>
 * <li>input does not refer to a class known by the application</li>
 * <li>input is not the class passed to the constructor or one of its descendants</li>
 * </ol></dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#classIsClass(Class) CoreQueries.classIsClass(...)
 * @see au.edu.anu.rscs.aot.queries.CoreQueries#stringIsClass(String) CoreQueries.stringIsClass(...)
 *
 */
// NOT TESTED

public class ClassQuery extends QueryAdaptor {

	private Class<?> parentClass = null;

	public ClassQuery(String parentClassName) {
		try {
			this.parentClass = Class.forName(parentClassName);
		} catch (Exception e) {
			throw new QGraphException(
					"Parent class '" + parentClassName + "' in constraint '" + this + "' does not exist", e);
		}
	}

	public ClassQuery(Class<?> parentClass) {
		this.parentClass = parentClass;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String localItem = (String) input;
		try {
			Class<?> c = Class.forName(localItem);
			if (!parentClass.isAssignableFrom(c))
				errorMsg = "Expected '" + parentClass.getName() + "' to be assignable from '" + input+"'.";
			return this;
		} catch (Exception e) {
			errorMsg = "Expected a java class but found '"+e.getMessage()+"'.";
			return this;
		}
	}

}
