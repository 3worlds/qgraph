package au.edu.anu.rscs.aot.queries.prototype;

public abstract class XQuery {
	protected Object result;
	protected String errorMsg;

	public Object getResult() {
		return result;
	}

	public boolean satisfied() {
		return errorMsg == null;
	}

	public String failMsg() {
		return errorMsg;
	}

	public abstract XQuery process(Object input);

	protected XQuery initProcess(Object input, Class expected) {
		result = input;
		Class<?> c = expected.getClass();

		errorMsg = null;// i.e satisfied is effectively true
		
		return this;
	}

}
