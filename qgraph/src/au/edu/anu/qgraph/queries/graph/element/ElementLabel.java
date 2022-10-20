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
import fr.cnrs.iees.omugi.graph.Specialized;

/**
 * <p>A Query for {@link Specialized} objects which have a class identifier, {@code classId}, 
 * also called <em>label</em>. 
 * Checks that the object label matches one of the labels built in the Query.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Specialized}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * <dt>Fails if</dt>
 * <dd>input class identifier ({@link Specialized#classId()}is not equal to one of the {@code String}s passed to the constructor</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * @author Jacques Gignoux - 7/9/2016
 * 
 * @see au.edu.anu.qgraph.queries.CoreQueries#hasTheLabel(String...) CoreQueries.hasTheLabel(...)
 *
 */
public class ElementLabel extends QueryAdaptor {
	private String[] labels;

	/**
	 * 
	 * @param labels the list of labels/classIds to compare to 
	 */
	public ElementLabel(String... labels) {
		this.labels = labels;
	}

	/**
	 * Only {@link Specialized} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String label = ((Specialized) input).classId();
		for (String l : labels) {
			if (l.equals(label)) {
				return this;
			}
		}
		errorMsg = "Label '" + label + "' not found in " + Arrays.deepToString(labels);
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.deepToString(labels);
	}

}
