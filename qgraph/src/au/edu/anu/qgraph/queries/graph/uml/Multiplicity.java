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
package au.edu.anu.qgraph.queries.graph.uml;

import au.edu.anu.rscs.aot.util.IntegerRange;

/**
 * The four main multiplicities used in UML.
 * Plus 0..0.
 * 
 * @author Shayne Flint - 29/3/2012
 *
 */
public enum Multiplicity {

	ZERO, ONE, ONE_MANY, ZERO_ONE, ZERO_MANY;

	/**
	 * Read a String argument into a multiplicity enum value. Throws an Exception if the String does
	 * not represent a multiplicity.
	 * @param str the String to parse
	 * @return the multiplicity
	 */
	public static Multiplicity valueFrom(String str) {
		// Multiplicity result;
		if (str.equals("0..0"))
			return ZERO;
		else if (str.equals("1..1"))
			return ONE;
		else if (str.equals("1..*"))
			return ONE_MANY;
		else if (str.equals("0..1"))
			return ZERO_ONE;
		else if (str.equals("0..*"))
			return ZERO_MANY;
		else
			throw new IllegalArgumentException("Cannot convert '" + str + "' to a multiplicity.");
	}

	/**
	 * Build a multiplicity enum value from two integers matching the two ends of the multiplicity.
	 * @param first the lower end of the multiplicity (0 or 1)
	 * @param last the upper end of the multiplicity (1 or {@code Integer.MAX_VALUE})
	 * @return the multiplicity
	 */
	public static Multiplicity valueFrom(int first, int last) {
		if (first == 0 && last == 0)
			return ZERO;
		if (first == 1 && last == 1)
			return ONE;
		if (first == 1 && last == Integer.MAX_VALUE)
			return ONE_MANY;
		if (first == 0 && last == 1)
			return ZERO_ONE;
		if (first == 0 && last == Integer.MAX_VALUE)
			return ZERO_MANY;
		throw new IllegalArgumentException("Multiplicity: " + first + ".." + last + " is not a valid multiplicity");
	}

	/**
	 * Convert a mutiplicity String into an {@link IntegerRange} instance.
	 * @param str
	 * @return the multiplicity
	 */
	public static IntegerRange stringToIntegerRange(String str) {
		try {
			String[] items = str.split("..");
			int from = Integer.parseInt(items[0]);
			int to = Integer.MAX_VALUE;
			if (!items[1].equals("*"))
				to = Integer.parseInt(items[1]);
			IntegerRange result = new IntegerRange(from, to);
			return result;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Multiplicity string wrongly formatted: " + str,e);
		}
	}

	/**
	 * Build a multiplicity enum value from an {@link IntegerRange}.
	 * @param range the range
	 * @return the multiplicity
	 */
	public static Multiplicity valueFrom(IntegerRange range) {
		return valueFrom(range.getFirst(), range.getLast());
	}

	/**
	 * Convert a mutiplicity into an {@link IntegerRange} instance.
	 * @return  the range matching this multiplicity
	 */
	public IntegerRange asIntegerRange() {
		switch (this) {
		case ZERO:
			return new IntegerRange(0, 0);
		case ONE:
			return new IntegerRange(1, 1);
		case ONE_MANY:
			return new IntegerRange(1, Integer.MAX_VALUE);
		case ZERO_ONE:
			return new IntegerRange(0, 1);
//		case ZERO_MANY:
//			return new IntegerRange(0, Integer.MAX_VALUE);
		default:
			//case ZERO_MANY
			return new IntegerRange(0, Integer.MAX_VALUE);
		}
	}
}
