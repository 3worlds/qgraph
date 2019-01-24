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

import au.edu.anu.rscs.aot.collections.tables.ObjectTable;
import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.GraphElement;

/**
 * A Query for objects which have a label (interface <em>Labelled</em> in library <em>omhtk</em>)
 * -- whatever 'label' means.
 * Checks that the object label matches one of the labels built in the Query.
 *
 * 
 * @author Shayne Flint - 26/3/2012
 * @author Jacques Gignoux - 7/9/2016
 *
 */
public class ElementLabel extends Query {

	private String[] labels;

	public ElementLabel(String... labels) {
		this.labels = labels;
	}
	
	public ElementLabel(String label) {
		labels = new String[1];
		labels[0] = label;
	}
	
	// constructor added by JG 7/9/2016 - for some reason, list properties are read as ObjectTable<?>
	// so we need a constructor to read these when using Archetypes.check() or .complies()
	public ElementLabel(ObjectTable<?> labels) {
		this.labels = new String[labels.size()];
		for (int i=0; i<labels.size(); i++)
			this.labels[i] = ((String)labels.getWithFlatIndex(i));
	}

	public static ElementLabel hasTheLabel(String... labels) {
		return new ElementLabel(labels);
	}

	@Override
	public String toString() {
		return "[" + stateString() + "label must be '" + labels.toString() + "']";
	}
	
	@Override
	public String userString() {
		return toString();
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		GraphElement localItem = (GraphElement)item;
		String label = localItem.classId();

		for (String l : labels) {
			if (l.equals(label)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
