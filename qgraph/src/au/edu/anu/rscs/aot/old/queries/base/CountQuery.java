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
package au.edu.anu.rscs.aot.old.queries.base;

import au.edu.anu.rscs.aot.old.queries.Query;
import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * 
 * @author Shayne Flint - 27/8/2012
 *
 */
@Deprecated
public class CountQuery extends Query {

	private int min;
	private int max;

	public CountQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}


	public static CountQuery hasMinCount(int min) {
		return new CountQuery(min, Integer.MAX_VALUE);		
	}

	public static CountQuery hasMaxCount(int max) {
		return new CountQuery(Integer.MIN_VALUE, max);		
	}

	public static CountQuery countInRange(int min, int max) {
		return new CountQuery(min, max);		
	}

	public static CountQuery countInRange(IntegerRange range) {
		return new CountQuery(range.getFirst(), range.getLast());		
	}

	public static CountQuery hasCount(int size) {
		return new CountQuery(size, size);		
	}

	@Override
	public CountQuery process(Object item) {
		defaultProcess(item);
		Integer localItem = (Integer)item;
		satisfied = (localItem >= min && localItem <= max);
		return this;
	}

	
	public String toString() {
		return "[Count must be in " + min + ".." + max + "]";
	}

}
