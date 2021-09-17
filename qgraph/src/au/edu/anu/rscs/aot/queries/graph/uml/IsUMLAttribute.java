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
package au.edu.anu.rscs.aot.queries.graph.uml;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;
import static au.edu.anu.rscs.aot.queries.graph.node.HasEdges.*;

import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.OrQuery;

/**
 * 
 * @author Shayne Flint - 30/4/02012
 *
 */
// NOT TESTED

public class IsUMLAttribute extends AndQuery {

	public IsUMLAttribute() {
		addQuery(hasTheLabel("attribute"), hasProperty("name"));
		addQuery(new OrQuery(hasProperty("type"), hasOutEdges(IsEnumeration.isEnumeration(), Multiplicity.ONE).withLabel("enumeration")));
	}

	public static IsUMLAttribute isAttribute() {
		return new IsUMLAttribute();
	}

//    @Override
//    public String userString() {
//    	return "[" + stateString() + "IsAttribute]";
//    }

}
