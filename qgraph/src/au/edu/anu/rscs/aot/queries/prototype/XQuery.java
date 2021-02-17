package au.edu.anu.rscs.aot.queries.prototype;

public abstract class XQuery {
	protected Object result;
	protected boolean satisfied;
	protected String failMsg;

	public Object getResult() {
		return result;
	}

	public boolean satisfied() {
		return satisfied;
	}
	

	public abstract XQuery process(Object input);
	
	protected XQuery initProcess(Object input) {
		result = input;
		satisfied = true;// assume true unless proved otherwise
		failMsg = "";
		return this;
	}
}
