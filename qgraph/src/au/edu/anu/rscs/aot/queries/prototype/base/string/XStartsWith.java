package au.edu.anu.rscs.aot.queries.prototype.base.string;

import au.edu.anu.rscs.aot.queries.prototype.queries.XQuery;

public class XStartsWith extends XQuery {

	private String str;

	public XStartsWith(String str) {
		this.str = str;
	}

	@Override
	public XQuery process(Object input) {
		initProcess(input, String.class);
		String localItem = (String) input;
		boolean ok = localItem.startsWith(str);
		if (!ok)
			errorMsg = "String must start with '" + str + "'.";
		return this;
	}


	@Override
	public String toString() {
		return "[String must start with '" + str + "']";
	}

}
