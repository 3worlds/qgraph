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

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * Checks if an object is of a given class
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class IsClass extends Query {

	private Class<?>[] classList;
	
	public IsClass(Class<?>... classList) {
		this.classList= classList;
	}
	
	public static IsClass isClass(Class<?>... classList) {
		return new IsClass(classList);
	}
	
	public static IsClass isClass(String... classNames) {
		Class<?>[] classList = new Class<?>[classNames.length];
		for (int i=0; i<classNames.length; i++) {
			try {
				classList[i] = Class.forName(classNames[i]);
			} catch (ClassNotFoundException e) {
				throw new QGraphException(e);
			}
		}
		return new IsClass(classList);
	}

	@Override
	public String toString() {
		String result = "[Item must be one of the types in (";
		if (classList.length == 1)
			result = result + classList[0].getName();
		else {
			result = result + classList[0].getName();
			for (int i=1; i<classList.length; i++)
				result = result + ", " + classList[i].getName();
		}
		return result + ")]";
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		for (Class<?> c : classList) {
			if (item.getClass().equals(c)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
