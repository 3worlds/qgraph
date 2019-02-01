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
package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.GraphElement;

/**
 * A Query for objects which have a name (interface <em>Named</em> in library <em>omhtk</em>).
 * -- whatever 'name' means.
 *  Checks that the object name matches one of the names built in the Query.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class ElementName extends Query {

	private String[] names;

	public ElementName(String... names) {
		this.names = names;
	}

	public ElementName(String name) {
		names = new String[1];
		names[0] = name;
	}
	
	public static ElementName hasTheName(String... names) {
		return new ElementName(names);
	}

	@Override
	public String toString() {
		return "[" + stateString() + "name must be '" + names.toString() + "']";
	}

	@Override
	public String userString() {
		return toString();
	}


	@Override
	public boolean satisfied(Object item) {
		GraphElement localItem = (GraphElement)item;
		String name = localItem.id();

		for (String l : names) {
			if (l.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		GraphElement localItem = (GraphElement)item;
		String name = localItem.id();

		for (String l : names) {
			if (l.equals(name)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
