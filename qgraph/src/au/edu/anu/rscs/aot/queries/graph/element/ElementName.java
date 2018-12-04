package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.queries.Query;
import fr.ens.biologie.generic.Named;

/**
 * A Query for objects which have a name (interface <em>Named</em> in library <em>omhtk</em>).
 * -- whatever 'name' means.
 *  Checks that the object name matches one of the names built in the Query.
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class ElementName extends Query {

	private String[] names;

	public ElementName(String... names) {
		this.names = names;
	}

	public ElementName(String name) {
		names = new String[1];
		names[0] = name;
	}
	
	public static ElementName hasTheName(String... names) {
		return new ElementName(names);
	}

	@Override
	public String toString() {
		return "[" + stateString() + "name must be '" + names.toString() + "']";
	}

	@Override
	public String userString() {
		return toString();
	}


	@Override
	public boolean satisfied(Object item) {
		Named localItem = (Named)item;
		String name = localItem.getName();

		for (String l : names) {
			if (l.equals(name)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Named localItem = (Named)item;
		String name = localItem.getName();

		for (String l : names) {
			if (l.equals(name)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
