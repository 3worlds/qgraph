package au.edu.anu.rscs.aot.queries.graph.uml;


import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

import au.edu.anu.rscs.aot.queries.base.AndQuery;


public class IsEnumeration extends AndQuery {

	public IsEnumeration() {
		addQuery(hasTheLabel("enumeration"), hasProperty("literals", isStringList()));
	}

	public static IsEnumeration isEnumeration() {
		return new IsEnumeration();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsEnumeration]";
    }

}
