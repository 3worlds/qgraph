package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class ClassQuery extends Query {

	private Class<?> parentClass = null;
	
	public ClassQuery(String parentClassName) {
		try {
			this.parentClass = Class.forName(parentClassName);
		} catch (Exception e) {
			throw new QGraphException("Parent class '" + parentClassName + "' in constraint '" + this + "' does not exist", e);
		}
	}

	public ClassQuery(Class<?> parentClass) {
		this.parentClass = parentClass;
	}
	
	public static ClassQuery stringIsClass(Class<?> parentClass) {
		return new ClassQuery(parentClass);
	}
	
	public static ClassQuery stringIsClass(String parentClassName) {
		return new ClassQuery(parentClassName);
	}
	

	@Override
	public ClassQuery process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			Class<?> c = Class.forName(localItem);
			if (parentClass != null)
				satisfied = parentClass.isAssignableFrom(c);
			satisfied = false;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[Object is instance of " + parentClass.getName() + " or subclass]";
	}


}
