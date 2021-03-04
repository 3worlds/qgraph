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
import fr.ens.biologie.generic.Sizeable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class SizeQuery extends Query {

	private int min;
	private int max;

	public SizeQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}


	public static SizeQuery hasMin(int min) {
		return new SizeQuery(min, Integer.MAX_VALUE);		
	}

	public static SizeQuery hasMax(int max) {
		return new SizeQuery(Integer.MIN_VALUE, max);		
	}

	public static SizeQuery inRange(int min, int max) {
		return new SizeQuery(min, max);		
	}

	public static SizeQuery inRange(IntegerRange range) {
		return new SizeQuery(range.getFirst(), range.getLast());		
	}

	public static SizeQuery hasSize(int size) {
		return new SizeQuery(size, size);		
	}

	public static SizeQuery hasOne() {
		return new SizeQuery(1, 1);		
	}

	public static SizeQuery hasTwo() {
		return new SizeQuery(2, 2);		
	}

	public static SizeQuery hasZeroOrOne() {
		return new SizeQuery(0, 1);		
	}

	public static SizeQuery hasZero() {
		return new SizeQuery(0, 0);		
	}

	public static SizeQuery hasOneOrMany() {
		return new SizeQuery(1, Integer.MAX_VALUE);		
	}

	public static SizeQuery hasZeroOrMany() {
		return new SizeQuery(0, Integer.MAX_VALUE);		
	}


	@Override
	public SizeQuery process(Object item) {
		defaultProcess(item);
		Sizeable localItem = (Sizeable)item;
		long count = localItem.size();
		satisfied  = count >= min && count <= max;
		return this;
	}

	
	public String toString() {
		return "[Size must be in " + min + ".." + max + "]";
	}

}
