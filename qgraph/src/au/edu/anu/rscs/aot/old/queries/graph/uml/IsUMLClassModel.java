
//Copyright (C) 2008, Shayne Flint

//Any user wishing to make a use of this software must
//contact Shayne Flint at shayne.flint@anu.edu.au to 
//arrange an appropriate license.


package au.edu.anu.rscs.aot.old.queries.graph.uml;


import static au.edu.anu.rscs.aot.old.queries.CoreQueries.*;

import au.edu.anu.rscs.aot.old.queries.base.AndQuery;
import au.edu.anu.rscs.aot.queries.graph.uml.Multiplicity;

@Deprecated
public class IsUMLClassModel extends AndQuery {


	public IsUMLClassModel() {		
		addQuery(
			hasTheLabel("classModel"),
			hasProperty("name"),
			hasOutEdges(IsUMLClass.isClass(), Multiplicity.ONE_MANY),
			hasOutEdges(IsUMLAssociation.isAssociation(), Multiplicity.ZERO_MANY));
	}

	public static IsUMLClassModel isClassModel() {
		return new IsUMLClassModel();
	}

    @Override
    public String userString() {
    	return "[" + stateString() + "IsClassModel]";
    }

}