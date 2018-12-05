package au.edu.anu.rscs.aot.queries.graph.uml;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

import au.edu.anu.rscs.aot.queries.base.AndQuery;

public class IsUMLClass extends AndQuery {

	public IsUMLClass() {
		addQuery(
			hasTheLabel("class"),
			hasProperty("name"),
			hasOutEdges(IsUMLAttribute.isAttribute(), Multiplicity.ZERO_MANY));
	}

	public static IsUMLClass isClass() {
		return new IsUMLClass();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsClass]";
    }
    
}
