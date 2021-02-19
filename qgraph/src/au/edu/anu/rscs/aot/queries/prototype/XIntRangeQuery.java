package au.edu.anu.rscs.aot.queries.prototype;

import au.edu.anu.rscs.aot.QGraphException;
import au.edu.anu.rscs.aot.collections.DynamicList;
import fr.ens.biologie.generic.Sizeable;

public class XIntRangeQuery extends XQuery {
	private int min;
	private int max;

	public XIntRangeQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input,Sizeable.class);
		/**
		 * If the query is incorrectly formulated it should crash rather than try and
		 * subsume the error within the query message system - I believe?
		 */
		if (!(input instanceof Sizeable))
			// Should we have two types of exceptions: a QueryException (a.k.a QGraphException AND make QGraphException a bog standard one.
			throw new QGraphException("'" + this.getClass().getSimpleName() + "' is unable to process '"
					+ input.getClass().getSimpleName() + "'. Input of class 'Sizeable'. is expected.");
		Sizeable localItem = (Sizeable) result;
		long size = localItem.size();
		boolean ok = size >= min && size <= max;
		if (!ok)
			errorMsg = "'" + localItem.getClass().getSimpleName() + "' size is " + localItem.size()
					+ " but must be the range " + min + " to " + max;
		return this;
	}

}
