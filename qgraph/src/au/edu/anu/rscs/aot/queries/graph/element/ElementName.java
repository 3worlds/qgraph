package au.edu.anu.rscs.aot.queries.graph.element;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.identity.Identity;

/**
 * <p>A Query for {@link Identity} objects, which have a unique identifier {@code id}, also called 
 * <em>a name</em>.
 * Checks that the object name/id matches one of the names built in the Query.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Identity}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class ElementName extends QueryAdaptor {

	private String[] names;

	/**
	 * 
	 * @param names a list of ids to compare to
	 */
	public ElementName(String... names) {
		this.names = names;
	}

	/**
	 * Only {@link Identity} arguments will be checked.
	 */
	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String name = ((Identity) input).id();
		for (String n : names) {
			if (n.equals(name))
				return this;
		}
		errorMsg = "Name '" + name + "' not found in " + Arrays.deepToString(names);
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.deepToString(names);
	}

}
