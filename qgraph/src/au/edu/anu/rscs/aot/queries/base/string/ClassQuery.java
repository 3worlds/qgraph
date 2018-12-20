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
import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class ClassQuery extends Query {

	private Class<?> parentClass = null;
	
	public ClassQuery(String parentClassName) {
		try {
			this.parentClass = Class.forName(parentClassName);
		} catch (Exception e) {
			throw new QGraphException("Parent class '" + parentClassName + "' in constraint '" + this + "' does not exist", e);
		}
	}

	public ClassQuery(Class<?> parentClass) {
		this.parentClass = parentClass;
	}
	
	public static ClassQuery stringIsClass(Class<?> parentClass) {
		return new ClassQuery(parentClass);
	}
	
	public static ClassQuery stringIsClass(String parentClassName) {
		return new ClassQuery(parentClassName);
	}
	

	@Override
	public ClassQuery process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Class<?> c = Class.forName(localItem);
			if (parentClass != null)
				satisfied = parentClass.isAssignableFrom(c);
			satisfied = false;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[Object is instance of " + parentClass.getName() + " or subclass]";
	}


}
