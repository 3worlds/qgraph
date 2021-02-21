package au.edu.anu.rscs.aot.queries.prototype.qfactory;

import au.edu.anu.rscs.aot.queries.prototype.base.XSequenceQuery;
import au.edu.anu.rscs.aot.queries.prototype.queries.XQuery;

public class Queries {
	private Queries() {
	};

	// Instead of get(...) return the sq and then interograte:
	// if errorMsg()!=null use the result;
	public static XSequenceQuery get(Object input, XQuery... queries) {
		XSequenceQuery q = new XSequenceQuery(queries);
		q.process(input);
		return q;
	}


}
