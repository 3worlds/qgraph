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

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * Checks that an integer ('count') is within a range.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>Integer</dd>
 * <dt>Type of result</dt>
 * <dd>Integer ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 27/8/2012
 *
 */

public class CountQuery extends QueryAdaptor {

	private int min;
	private int max;

	/**
	 * Constructor with min and max (caution: no check that min<max). 
	 * @param min
	 * @param max
	 */
	public CountQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}

	/**
	 * Only Integer argument will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Integer localItem = (Integer)input;
		if (!((localItem >= min) && (localItem <= max)))
			errorMsg ="[Expected Integer to be in the range " + min + ".." + max + " but found '"+localItem+"']";
		return this;
	}



}
