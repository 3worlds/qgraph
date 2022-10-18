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

/**
 * <p>Check if a {@link String} starts with a given sequence of characters.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code String}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input does not start with the {@code String} passed to the constructor</dd>
 * </dl>
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#startsWith(String) CoreQueries.startsWith(String)
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class StartsWith extends QueryAdaptor {

	private String str;

	/**
	 * 
	 * @param str the initial String inputs must start with
	 */
	public StartsWith(String str) {
		this.str = str;
	}

	/**
	 * Only {@link String} arguments will be checked.
	 */
	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
		String localItem = (String) input;
		boolean ok = localItem.startsWith(str);
		if (!ok)
			errorMsg = "String must start with '" + str + "'.";
		return this;
	}

}
