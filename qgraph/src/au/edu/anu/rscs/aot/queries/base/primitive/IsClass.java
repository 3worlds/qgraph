package au.edu.anu.rscs.aot.queries.base.primitive;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsClass extends Query {

	private Class<?>[] classList;
	
	public IsClass(Class<?>... classList) {
		this.classList= classList;
	}
	
	public static IsClass isClass(Class<?>... classList) {
		return new IsClass(classList);
	}
	
	public static IsClass isClass(String... classNames) {
		Class<?>[] classList = new Class<?>[classNames.length];
		for (int i=0; i<classNames.length; i++) {
			try {
				classList[i] = Class.forName(classNames[i]);
			} catch (ClassNotFoundException e) {
				throw new QGraphException(e);
			}
		}
		return new IsClass(classList);
	}

	@Override
	public String toString() {
		String result = "[Item must be one of the types in (";
		if (classList.length == 1)
			result = result + classList[0].getName();
		else {
			result = result + classList[0].getName();
			for (int i=1; i<classList.length; i++)
				result = result + ", " + classList[i].getName();
		}
		return result + ")]";
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		for (Class<?> c : classList) {
			if (item.getClass().equals(c)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
