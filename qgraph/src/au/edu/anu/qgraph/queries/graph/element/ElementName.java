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
package au.edu.anu.qgraph.queries.graph.element;

import java.util.Arrays;

import au.edu.anu.qgraph.queries.*;
import fr.cnrs.iees.omugi.identity.Identity;

/**
 * <p>A Query for {@link Identity} objects, which have a unique identifier {@code id}, also called 
 * <em>a name</em>.
 * Checks that the object name/id matches one of the names built in the Query.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Identity}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input identifier ({@link Identity#id()}is not equal to one of the {@code String}s passed to the constructor</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasTheName(String...) CoreQueries.hasTheName(...)
 *
 */
public class ElementName extends QueryAdaptor {

	private String[] names;

	/**
	 * 
	 * @param names a list of ids to compare to
	 */
	public ElementName(String... names) {
		this.names = names;
	}

	/**
	 * Only {@link Identity} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String name = ((Identity) input).id();
		for (String n : names) {
			if (n.equals(name))
				return this;
		}
		errorMsg = "Name '" + name + "' not found in " + Arrays.deepToString(names);
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.deepToString(names);
	}

}
