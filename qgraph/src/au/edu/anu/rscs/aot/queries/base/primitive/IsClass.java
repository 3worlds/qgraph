package au.edu.anu.rscs.aot.queries.base.primitive;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * Checks if an object is of a given class
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class IsClass extends QueryAdaptor {
	private Class<?>[] classList;

	public IsClass(Class<?>... classList) {
		this.classList = classList;
	}
	
	public IsClass(String... classNames) {
		Class<?>[] classList = new Class<?>[classNames.length];
		for (int i=0;i<classNames.length;i++) {
			try {
				classList[i] = Class.forName(classNames[i]);	
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		for (Class<?> c : classList) {
			if (input.getClass().equals(c))
				return this;
		}
		errorMsg = "Item must be one of the types in " + Arrays.deepToString(classList) + ".";
		return this;
	}

}
