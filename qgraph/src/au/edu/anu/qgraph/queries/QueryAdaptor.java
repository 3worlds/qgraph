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
package au.edu.anu.qgraph.queries;

/**
 * <p>An ancestor implementation for queries with default behaviour for all methods of 
 * {@link Queryable} but {@code submit(...)}.</p>
 * 
 * <p>Rules for writing descendants:</p>
 * <ul>
 * <li>call {@code initInput(input)} in {@code submit(...)} to reset field values to default (
 * {@code satisfied=true, errorMsg=null, actionMsg=null}).</li>
 * <li>only test what this query is meant to check. e.g., if {@code input==null} the query
 * should return satisfied unless it is an {@code isNull} query. </li>
 * </ul>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>any class;</dd>
 * <dt>Type of result</dt>
 * <dd>same as input (by default {@code result=input})</dd>
 * </dl>
 * 
 * @author Ian Davies - 23 Feb. 2021
 */
public abstract class QueryAdaptor implements Queryable {
	protected Object result;
	protected String errorMsg;
	protected String actionMsg;

	protected Queryable initInput(Object input/* , Class<?> expected */) {
		result = input;
		// NB: default is satisfied() == true;
		errorMsg = null;
		actionMsg = null;
		return this;
	}

	@Override
	public final boolean satisfied() {
		return errorMsg == null;
	}

	@Override
	public final String errorMsg() {
		return errorMsg;
	}
	
	@Override
	public final String actionMsg() {
		return actionMsg;
	}

	@Override
	public final Object result() {
		return result;
	}

	@Override
	public String toString() {
		if (satisfied())
			return "[" + this.getClass().getSimpleName() + " is TRUE]";
		else
			return "[" + this.getClass().getSimpleName() + " is FALSE [" + errorMsg + "]]";
	}

}
