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
package au.edu.anu.rscs.aot.collections;

import java.util.Collection;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * A list filtered by a Query.
 * 
 * @author Shayne Flint - 23/4/2012
 *
 */
public class FilteredList<T> extends DynamicList<T>  {

	public FilteredList(Collection<T> list, Query query) {
		for (T item : list)
			if (query.satisfied(item))
				add(item);
	}


	// TESTING
	//

//	public static void main(String[] args) {
//
//		final Logger log = LoggerFactory.getLogger(FilteredList.class);
//		
//		class TestList1 extends DynamicList<String> {
//			public TestList1() {
//				super("i1", "i2", "i3", "i4");
//				log.debug("<<new test list>>");
//			}
//		}
//
//		// test constraints
//		//
//
//		class TestList2 extends DynamicList<String> {
//			public TestList2() {
//				super("i1", "i2", "j3", "j4", "j5", "i6", "j7", "i8");
//				log.debug("<<new test list 2>>");
//			}
//		}
//
//		class TestQuery extends Query {
//
//			@Override
//			public TestQuery process(Object item) {
//				defaultProcess(item);
//				String localItem = (String)item;
//				satisfied = localItem.startsWith("i");
//				return this;
//			}
//
//		}
//
//		DynamicList<String> list = new TestList2();
//		log.debug("list = {}", list);
//		FilteredList<String> filteredList = new FilteredList<String>(list, new TestQuery());
//		log.debug("filtered list = {}", filteredList);
//
//	}



}
