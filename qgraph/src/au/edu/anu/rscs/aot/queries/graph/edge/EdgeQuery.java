package au.edu.anu.rscs.aot.queries.graph.edge;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Edge;
import fr.ens.biologie.generic.Labelled;

/**
  * 
 * @author Shayne Flint - 26/3/2012
 *
 */
// NOT TESTED
public class EdgeQuery extends Query {

	private Query startNodeQuery;
	private Query endNodeQuery;
	
	private String startLabel;
	private String label;
	private String endLabel;
	
	public EdgeQuery(Query startNodeQuery, Query endNodeQuery, String startLabel, String label, String endLabel) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = startLabel;
		this.label = label;
		this.endLabel = endLabel;
	}
	
	public EdgeQuery(Query startNodeQuery, Query endNodeQuery, String label) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = label;
		this.endLabel = null;
	}
	
	public EdgeQuery(Query startNodeQuery, Query endNodeQuery) {
		this.startNodeQuery = startNodeQuery;
		this.endNodeQuery = endNodeQuery;
		this.startLabel = null;
		this.label = null;
		this.endLabel = null;
	}
	
	public static EdgeQuery isEdge(Query startNodeQuery, Query endNodeQuery, String startLabel, String label, String endLabel) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, startLabel, label, endLabel);
	}

	public static EdgeQuery isEdge(Query startNodeQuery, Query endNodeQuery, String label) {
		return new EdgeQuery(startNodeQuery, endNodeQuery, label);
	}

	public static EdgeQuery isEdge(Query startNodeQuery, Query endNodeQuery) {
		return new EdgeQuery(startNodeQuery, endNodeQuery);
	}

	@Override
	public String toString() {
		String result = startNodeQuery.toString();
		if (startLabel != null)
			result = result + "-" + startLabel;
		else
			result = result + "--";
		if (label != null)
			result = result + "-" + label;
		else
			result = result + "--";
		if (endLabel != null)
			result = result + "-" + endLabel;
		else
			result = result + "--";
		result = result + "-" + endNodeQuery;
		return result;
	}
	
	
	// this code must be brittle - it assumes an Edge is also Labelled...
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Edge localItem = (Edge)item;
		Labelled startNode = (Labelled) localItem.startNode();
		Labelled endNode   = (Labelled) localItem.endNode();
		
		startNodeQuery.process(startNode);
		endNodeQuery.process(endNode);
		
		satisfied = true;
		satisfied = satisfied && startNodeQuery.satisfied();
		satisfied = satisfied && endNodeQuery.satisfied();

		if (startLabel != null)
			satisfied = satisfied && startNode.getLabel().equals(startLabel);
		if (label != null)
			satisfied = satisfied && ((Labelled)localItem).getLabel().equals(label);
		if (endLabel != null)
			satisfied = satisfied && endNode.getLabel().equals(endLabel);

		return this;
	}
}
