package au.edu.anu.rscs.aot.queries.graph.uml;

import au.edu.anu.rscs.aot.queries.Query;
import au.edu.anu.rscs.aot.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.base.SequenceQuery;

import static au.edu.anu.rscs.aot.queries.CoreQueries.*;


public class IsUMLAssociation extends AndQuery {

	public IsUMLAssociation() {
		
		Query edgeQuery = new SequenceQuery(hasProperty("phrase"), hasProperty("multiplicity",
				isEnum(Multiplicity.values()) ));
				
		addQuery(
			hasTheLabel("association"),
			hasOutEdges(IsUMLClass.isClass(), Multiplicity.ONE).withLabel("passive").withEdgeQuery(edgeQuery),
			hasOutEdges(IsUMLClass.isClass(), Multiplicity.ONE).withLabel("active").withEdgeQuery(edgeQuery),
			hasOutEdges(IsUMLClass.isClass(), Multiplicity.ZERO_ONE).withLabel("associativeClass"));
	}

	public static IsUMLAssociation isAssociation() {
		return new IsUMLAssociation();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsAssociation]";
    }

}
