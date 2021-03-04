package au.edu.anu.rscs.aot.queries.graph.element;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Specialized;

public class ElementLabel extends QueryAdaptor {
	private String[] labels;

	public ElementLabel(String... labels) {
		this.labels = labels;
	}

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		String label = ((Specialized) input).classId();
		for (String l : labels) {
			if (l.equals(label)) {
				return this;
			}
		}
		errorMsg = "Label '" + label + "' not found in " + Arrays.deepToString(labels);
		return this;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + Arrays.deepToString(labels);
	}

}
