package au.edu.anu.rscs.aot.queries.prototype.queries;


/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public interface Queryable {
	public Queryable query(Object input);
	public String errorMsg();
	public Object result();

}
