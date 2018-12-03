package au.edu.anu.rscs.aot.queries.base.string;

import java.io.File;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class FileQuery extends Query {
	
	public static FileQuery isFileName() {
		return new FileQuery();
	}
	
	@Override
	public FileQuery process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = (new File(localItem).exists());
		return this;
	}


}
