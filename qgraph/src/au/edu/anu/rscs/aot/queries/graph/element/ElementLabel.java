package au.edu.anu.rscs.aot.queries.graph.element;

import au.edu.anu.rscs.aot.collections.tables.ObjectTable;
import au.edu.anu.rscs.aot.queries.Query;
import fr.ens.biologie.generic.Labelled;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 * @author Jacques Gignoux - 7/9/2016
 *
 */
public class ElementLabel extends Query {

	private String[] labels;

	public ElementLabel(String... labels) {
		this.labels = labels;
	}
	
	public ElementLabel(String label) {
		labels = new String[1];
		labels[0] = label;
	}
	
	// constructor added by JG 7/9/2016 - for some reason, list properties are read as ObjectTable<?>
	// so we need a constructor to read these when using Archetypes.check() or .complies()
	public ElementLabel(ObjectTable<?> labels) {
		this.labels = new String[labels.size()];
		for (int i=0; i<labels.size(); i++)
			this.labels[i] = ((String)labels.getWithFlatIndex(i));
	}

	public static ElementLabel hasTheLabel(String... labels) {
		return new ElementLabel(labels);
	}

	@Override
	public String toString() {
		return "[" + stateString() + "label must be '" + labels.toString() + "']";
	}
	
	@Override
	public String userString() {
		return toString();
	}
	
	@Override
	public Query process(Object item) {
		defaultProcess(item);
		Labelled localItem = (Labelled)item;
		String label = localItem.getLabel();

		for (String l : labels) {
			if (l.equals(label)) {
				satisfied = true;
				return this;
			}
		}
		satisfied = false;
		return this;
	}

}
