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
import au.edu.anu.qgraph.queries.Queryable;

/**
 * <p>Checks that an Object is an instance of the Class passed as argument of 
 * this Query's constructor.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input is not an instance of the class passed to the constructor</dd>
 * </dl>
 * @author Shayne Flint - 26/3/2012
 *
 * @see au.edu.anu.qgraph.queries.CoreQueries#isInstanceOf(Class) CoreQueries.isInstanceOf(Class)
 * @see au.edu.anu.qgraph.queries.CoreQueries#isInstanceOf(String) CoreQueries.isInstanceOf(String
 */

public class IsInstanceOf extends QueryAdaptor {

	private Class<?> theClass;

	/**
	 * 
	 * @param theClass the class to compare objects to
	 */
	public IsInstanceOf(Class<?> theClass) {
		this.theClass = theClass;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!theClass.isInstance(input))
			errorMsg = "Expected '"+input+"' to be an instance of '"+theClass+"'.";					
		return this;
	}


}
