package au.edu.anu.rscs.aot.queries.graph.edge;

import java.util.logging.Logger;

import au.edu.anu.rscs.aot.queries.Query;
import fr.cnrs.iees.graph.generic.Edge;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 * NOTE (JG 2018): refactored to use java logging instead of aot logging.
 */
// NOT TESTED
public class EdgeLog extends Query {

	private String prefix;
	
	public EdgeLog(String prefix) {
		this.prefix= prefix;
	}
	
	public static EdgeLog logEdge(String prefix) {
		return new EdgeLog(prefix);
	}
	
	public static EdgeLog logEdge() {
		return new EdgeLog("");
	}

	private Logger log = Logger.getLogger(EdgeLog.class.getName());

	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Edge localItem = (Edge)item;
		log.fine(prefix + localItem.toString());
		satisfied = true;
		return this;
	}

	@Override
	public String toString() {
		return "[EdgeLog]";
	}
	

}
