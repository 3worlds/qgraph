package au.edu.anu.rscs.aot.queries.prototype.base;

import au.edu.anu.rscs.aot.queries.prototype.queries.XQuery;
import fr.ens.biologie.generic.Sizeable;

public class XSizeQuery extends XQuery {
	private int min;
	private int max;

	public XSizeQuery(int min, int max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input, Sizeable.class);
		Sizeable localItem = (Sizeable) result;
		long size = localItem.size();
		boolean ok = size >= min && size <= max;
		if (!ok)
			errorMsg = "Size of '" + localItem.getClass().getSimpleName() + "' is '" + localItem.size() + "' but "
					+ this;
		return this;
	}

	@Override
	public String toString() {
		return "[Size must be within " + min + ".." + max + " inclusive.]";
	}

	
}
