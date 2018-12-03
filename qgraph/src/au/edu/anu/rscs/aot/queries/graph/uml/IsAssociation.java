package au.edu.anu.rscs.aot.queries.graph.uml;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.SequenceQuery;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;


public class IsAssociation extends AndQuery {

	public IsAssociation() {
		
		Query edgeQuery = new SequenceQuery(hasProperty("phrase"), hasProperty("multiplicity",
				isEnum(Multiplicity.values()) ));
				
		addQuery(
			hasTheLabel("association"),
			hasOutEdges(IsClass.isClass(), Multiplicity.ONE).withLabel("passive").withEdgeQuery(edgeQuery),
			hasOutEdges(IsClass.isClass(), Multiplicity.ONE).withLabel("active").withEdgeQuery(edgeQuery),
			hasOutEdges(IsClass.isClass(), Multiplicity.ZERO_ONE).withLabel("associativeClass"));
	}

	public static IsAssociation isAssociation() {
		return new IsAssociation();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsAssociation]";
    }

}
