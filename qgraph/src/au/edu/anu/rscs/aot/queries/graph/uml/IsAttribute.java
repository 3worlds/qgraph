package au.edu.anu.rscs.aot.queries.graph.uml;

import au.edu.anu.rscs.aot.queries.base.AndQuery;

public class IsAttribute extends AndQuery {

	public IsAttribute() {
		addQuery(hasTheLabel("attribute"), hasProperty("name"));
		addQuery(new OrQuery(hasProperty("type"), hasOutEdges(IsEnumeration.isEnumeration(), Multiplicity.ONE).withLabel("enumeration")));
	}

	public static IsAttribute isAttribute() {
		return new IsAttribute();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsAttribute]";
    }

}
