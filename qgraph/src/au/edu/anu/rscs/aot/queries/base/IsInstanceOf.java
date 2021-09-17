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
package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * <o>Checks that an Object is an instance of the Class passed as argument of this Query's constructor.</p>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

public class IsInstanceOf extends QueryAdaptor {

	private Class<?> theClass;

	public IsInstanceOf(Class<?> theClass) {
		this.theClass = theClass;
	}

	/**
	 * Convenience static method
	 * @param theClass the class to check.
	 * @return an instance of this Query
	 */
	public static Queryable isInstanceOf(Class<?> theClass) {
		return new IsInstanceOf(theClass);
	}

	public static Queryable isInstanceOf(String className) {
		try {
			return new IsInstanceOf(Class.forName(className));
		} catch (Exception e) {
			throw new QGraphException("Can't create IsInstanceOf constraint", e);
		}
	}
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		if (!theClass.isInstance(input))
			errorMsg = "Expected '"+input+"' to be an instance of '"+theClass+"'.";			
		
		return this;
	}


}
