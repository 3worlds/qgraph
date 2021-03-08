package au.edu.anu.rscs.aot.queries;


/**
 * @author Ian Davies
 *
 * @date 23 Feb. 2021
 */
public interface Queryable {
	public Queryable submit(Object input);
	public boolean satisfied();
	public String errorMsg();
	public String actionMsg();
	public Object result();

}
