package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * <o>Checks that an Object is an instance of the Class passed as argument of this Query's constructor.</p>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsInstanceOf extends Query {

	private Class<?> theClass;

	public IsInstanceOf(Class<?> theClass) {
		this.theClass = theClass;
	}

	/**
	 * Convenience static method
	 * @param theClass the class to check.
	 * @return an instance of this Query
	 */
	public static Query isInstanceOf(Class<?> theClass) {
		return new IsInstanceOf(theClass);
	}

	public static Query isInstanceOf(String className) {
		try {
			return new IsInstanceOf(Class.forName(className));
		} catch (Exception e) {
			throw new QGraphException("Can't create IsInstanceOf constraint", e);
		}
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		satisfied = theClass.isInstance(item);
		return this;
	}

	public String toString() {
		return "[is instance of " + theClass.getSimpleName() + "]";

	}
}
