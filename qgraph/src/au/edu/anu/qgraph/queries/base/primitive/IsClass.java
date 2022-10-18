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

import java.util.Arrays;

import au.edu.anu.qgraph.queries.QueryAdaptor;
import au.edu.anu.qgraph.queries.Queryable;

/**
 * Checks if an object is of a given class within a list of classes.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Class}&lt;?&gt;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input is not one of the classes passed to the constructor</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 * @see au.edu.anu.qgraph.queries.CoreQueries#isClass(Class...) CoreQueries.isClass(Class...)
 * @see au.edu.anu.qgraph.queries.CoreQueries#isClass(String...) CoreQueries.isClass(String...)
 */
// NOT TESTED
public class IsClass extends QueryAdaptor {
	private Class<?>[] classList;

	/**
	 * 
	 * @param classList the classes to check
	 */
	public IsClass(Class<?>... classList) {
		this.classList = classList;
	}

	/**
	 * 
	 * @param classNames the names of the classes to check
	 */
	public IsClass(String... classNames) {
		classList = new Class<?>[classNames.length];
		for (int i = 0; i < classNames.length; i++) {
			try {
				classList[i] = Class.forName(classNames[i]);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Only {@link Class}&lt;?&gt; arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		for (Class<?> c : classList) {
			if (input.getClass().equals(c))
				return this;
		}
		errorMsg = "Expected '"+input+"' to be one of the classes " + Arrays.deepToString(classList) + " but found '"
				+ input.getClass().getName() + "'.";
		return this;
	}

}
