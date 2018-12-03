package au.edu.anu.rscs.aot.collections;

import java.util.List;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * A list filtered by a Query.
 * 
 * @author Shayne Flint - 23/4/2012
 *
 */
public class FilteredList<T> extends DynamicList<T>  {

	public FilteredList(List<T> list, Query query) {
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
