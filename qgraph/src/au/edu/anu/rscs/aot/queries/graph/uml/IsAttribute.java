package au.edu.anu.rscs.aot.queries.graph.uml;

import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.OrQuery;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

/**
 * 
 * @author Shayne Flint - 30/4/02012
 *
 */
// NOT TESTED
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
