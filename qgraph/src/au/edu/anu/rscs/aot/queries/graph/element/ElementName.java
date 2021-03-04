package au.edu.anu.rscs.aot.queries.graph.element;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.identity.Identity;

public class ElementName extends QueryAdaptor {
	private String[] names;

	public ElementName(String... names) {
		this.names = names;
	}

//	public XElementName(String name) {
//		names = new String[1];
//		names[0] = name;
//	}

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
