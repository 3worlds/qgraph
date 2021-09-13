package au.edu.anu.rscs.aot.queries.graph.element;

import java.util.Arrays;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;
import fr.cnrs.iees.graph.Specialized;

/**
 * <p>A Query for {@link Specialized} objects which have a class identifier, {@code classId}, 
 * also called <em>label</em>. 
 * Checks that the object label matches one of the labels built in the Query.</p>
 * 
 * <dl>
 * <dt>Type of input to {@code submit()}</dt>
 * <dd>{@code Specialized}</dd>
 * <dt>Type of result</dt>
 * <dd>same as input ({@code result=input})</dd>
 * </dl>
 * 
 * @author Shayne Flint - 26/3/2012
 * @author Jacques Gignoux - 7/9/2016
 *
 */
public class ElementLabel extends QueryAdaptor {
	private String[] labels;

	/**
	 * 
	 * @param labels the list of labels/classIds to compare to 
	 */
	public ElementLabel(String... labels) {
		this.labels = labels;
	}

	/**
	 * Only {@link Specialized} arguments will be checked.
	 */
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
