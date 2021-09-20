package au.edu.anu.rscs.aot.queries.base.primitive;

import java.util.List;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// TODO: check this as it was modified by replacing StringList by List<String>
public class IsStringList extends QueryAdaptor{

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String className = input.getClass().getName();
		if (!(List.class.isAssignableFrom(input.getClass()))){
			errorMsg = "Expected 'List<String>' but found '"+input.getClass().getName()+"'.";
			return this;
		}
		List<?> lst = (List<?>)input;
		if (lst.isEmpty()) {
			errorMsg = "Expected non-empty 'List<String>' but found '"+input.getClass().getName()+"<?>'.";
			return this;
		}
		Object e = lst.get(0);
		if (!(e instanceof String)){
			errorMsg = "Expected 'List<String>' but found 'List<"+e.getClass().getSimpleName()+">'.";
			return this;
		}
		return this;
		
//		Type[] par = input.getClass().getTypeParameters();
//		// its not possible to know the element class in an empty generic - its not in the JVM
//		if (!(List.class.isAssignableFrom(input.getClass())) 
//				&& (par[0].getClass().equals(String.class))) {
//			errorMsg = "'"+input.getClass().getSimpleName()+"' is not a StringList";
//		};
//		return this;
	}

}
