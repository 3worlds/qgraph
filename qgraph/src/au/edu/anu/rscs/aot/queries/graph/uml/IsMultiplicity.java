package au.edu.anu.rscs.aot.queries.graph.uml;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class IsMultiplicity extends Query {

	public IsMultiplicity() {
	}

	public static Query isMultiplicity() {
		return new IsMultiplicity();
	}

	public String toString() {
		return "[Must be a valid Multiplicity]";
	}

	@Override
	public IsMultiplicity process(Object item) {
		defaultProcess(item);
		if (item instanceof Multiplicity) {
			satisfied = true;
		} else
			satisfied = false;
		return this;
	}

}