package au.edu.anu.rscs.aot.queries.graph.uml;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

import au.edu.anu.rscs.aot.queries.base.AndQuery;

public class IsClass extends AndQuery {

	public IsClass() {
		addQuery(
			hasTheLabel("class"),
			hasProperty("name"),
			hasOutEdges(IsAttribute.isAttribute(), Multiplicity.ZERO_MANY));
	}

	public static IsClass isClass() {
		return new IsClass();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsClass]";
    }
    
}
