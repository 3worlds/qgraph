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
package au.edu.anu.rscs.aot.old.queries.base.string;

import java.text.DateFormat;
import java.util.Date;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class DateString extends Query {
	
	public static DateString isDate() {
		return new DateString();
	}
	
	@SuppressWarnings("unused")
	@Override
	public boolean satisfied(Object item) {
		String localItem = (String)item;
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			Date typedItem = df.parse(localItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public DateString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			DateFormat df = DateFormat.getDateTimeInstance();
			Date typedItem = df.parse(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be a Date]";
	}

}
