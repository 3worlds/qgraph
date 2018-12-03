
//Copyright (C) 2008, Shayne Flint

//Any user wishing to make a use of this software must
//contact Shayne Flint at shayne.flint@anu.edu.au to 
//arrange an appropriate license.


package au.edu.anu.rscs.aot.queries.graph.uml;


import static au.edu.anu.rscs.aot.queries.CoreQueries.*;

import au.edu.anu.rscs.aot.queries.base.AndQuery;

public class IsClassModel extends AndQuery {


	public IsClassModel() {		
		addQuery(
			hasTheLabel("classModel"),
			hasProperty("name"),
			hasOutEdges(IsClass.isClass(), Multiplicity.ONE_MANY),
			hasOutEdges(IsAssociation.isAssociation(), Multiplicity.ZERO_MANY));
	}

	public static IsClassModel isClassModel() {
		return new IsClassModel();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsClassModel]";
    }

}
