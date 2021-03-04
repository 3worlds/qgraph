/**
 * @author shayne.flint@anu.edu.au
 *
 * 
 * 
 */
package au.edu.anu.rscs.aot.old.queries.base.primitive;

import java.lang.reflect.Type;
import java.util.List;

import au.edu.anu.rscs.aot.old.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// TODO: check this as it was modified by replacing StringList by List<String>
@Deprecated
public class IsStringList extends Query {

	public IsStringList() {
	}

	public static IsStringList isStringList() {
		return new IsStringList();
	}

	public String toString() {
		return "[isStringList]";
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
//		satisfied = (item instanceof StringList);
		Type[] par = item.getClass().getTypeParameters();
		satisfied = (List.class.isAssignableFrom(item.getClass())) 
				&& (par[0].getClass().equals(String.class));
		return this;
	}

}
