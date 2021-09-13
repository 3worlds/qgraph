package au.edu.anu.rscs.aot.queries.base.primitive;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

/**
 * Checks if an object is of a given class within a list of classes.
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@link Class&lt;?&gt;}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class IsClass extends QueryAdaptor {
	private Class<?>[] classList;

	/**
	 * 
	 * @param classList the classes to check
	 */
	public IsClass(Class<?>... classList) {
		this.classList = classList;
	}
	
	/**
	 * 
	 * @param classNames the names of the classes to check
	 */
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

	/**
	 * Only {@link Class&lt;?&gt;} arguments will be checked.
	 */
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
