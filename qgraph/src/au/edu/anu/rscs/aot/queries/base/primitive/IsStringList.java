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

import java.util.List;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */

/**
 * @author Ian Davies
 *
 * @date 21 Sept 2021
 */
public class IsStringList extends QueryAdaptor {

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		// NB: It's not possible to know the element class in an empty generic by
		// reflection - its not in the JVM.
		if (!(List.class.isAssignableFrom(input.getClass()))) {
			errorMsg = "Expected 'List<String>' but found '" + input.getClass().getName() + "'.";
			return this;
		}

		List<?> lst = (List<?>) input;
		if (lst.isEmpty()) {
			errorMsg = "Expected non-empty 'List<String>' but found '" + input.getClass().getName() + "<?>'.";
			return this;
		}
		Object e = lst.get(0);
		if (!(e instanceof String)) {
			errorMsg = "Expected 'List<String>' but found 'List<" + e.getClass().getSimpleName() + ">'.";
			return this;
		}
		return this;

//		
//		Type[] par = input.getClass().getTypeParameters();
//		if (!(List.class.isAssignableFrom(input.getClass())) 
//				&& (par[0].getClass().equals(String.class))) {
//			errorMsg = "'"+input.getClass().getSimpleName()+"' is not a StringList";
//		};
//		return this;
	}

}
