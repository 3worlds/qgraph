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
package au.edu.anu.qgraph.queries.base.string;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>
 * Check if a class name represents a class that is assignable from from a given
 * parent class.
 * </p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code String}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>
 * <ol>
 * <li>input does not refer to a class known by the application</li>
 * <li>input is not the class passed to the constructor or one of its
 * descendants</li>
 * </ol>
 * </dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#classIsClass(Class)
 *      CoreQueries.classIsClass(...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#stringIsClass(String)
 *      CoreQueries.stringIsClass(...)
 *
 */
public class ClassQuery extends QueryAdaptor {

	private Class<?> parentClass = null;

	/**
	 * Constructor with String representation of the parent class.
	 * <p>
	 * 
	 * Throws {@code IllegalStateException} if the parent class name cannot be
	 * found.
	 * 
	 * @param parentClassName Name of the parent class that in submitted input is
	 *                        tested against.
	 * 
	 */
	public ClassQuery(String parentClassName) {
		try {
			this.parentClass = Class.forName(parentClassName);
		} catch (ClassNotFoundException e) {
			// re-throw for context
			throw new IllegalStateException(
					"Parent class '" + parentClassName + "' in constraint '" + this + "' does not exist", e);
		}
	}

	/**
	 * @param parentClass Parent class that in submitted input is tested against.
	 */
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
				errorMsg = "Expected '" + parentClass.getName() + "' to be assignable from '" + input + "'.";
			return this;
		} catch (Exception e) {
			errorMsg = "Expected a java class but found '" + e.getMessage() + "'.";
			return this;
		}
	}

}
