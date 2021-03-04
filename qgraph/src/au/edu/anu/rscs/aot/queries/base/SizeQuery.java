package au.edu.anu.rscs.aot.queries.base;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import fr.ens.biologie.generic.Sizeable;

public class SizeQuery extends QueryAdaptor {
	private int min;
	private int max;

	public SizeQuery() {
		this.min = 0;
		this.max = Integer.MAX_VALUE;
	}
	

	@Override
	public QueryAdaptor submit(Object input) {
		initInput(input);
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

	// --- fluid interface
	public SizeQuery min(int m) {
		min = m;
		return this;
	}
	public SizeQuery max(int m) {
		max = m;
		return this;
	}
	
}
