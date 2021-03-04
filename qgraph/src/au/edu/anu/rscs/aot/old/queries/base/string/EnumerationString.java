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

import java.util.ArrayList;
import java.util.List;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
@Deprecated
public class EnumerationString extends Query {

	private List<String> valueList;

	public EnumerationString(String... valueList) {		
		this.valueList = new ArrayList<>(valueList.length);
		for (int i=0; i<valueList.length; i++)
			this.valueList.add(valueList[i]);
	}
	
	public EnumerationString(Enum<?>... enumList) {
		this.valueList = new ArrayList<>(enumList.length);
		for (Enum<?> e : enumList) 
			valueList.add(e.name());
	}

	public static EnumerationString isEnum(String... valueList) {
		return new EnumerationString(valueList);
	}
	
	public static EnumerationString isEnum(Enum<?>... enumList) {
		return new EnumerationString(enumList);
	}
	
	@Override
	public String userString() {
		return "[EnumerationString " + valueList.toString() + "]";
	}
	
	@Override
	public EnumerationString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = valueList.contains(localItem);
		return this;
	}

}
