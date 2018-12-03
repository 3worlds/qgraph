package au.edu.anu.rscs.aot.queries.base.string;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class UserNameQuery extends Query {

	public static UserNameQuery isUserName() {
		return new UserNameQuery();
	}
	
	@Override
	public UserNameQuery process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		if (localItem.length() < 1)
			satisfied = false;

		if (localItem.contains(" "))
			satisfied = false;

		if (localItem.contains("\t"))
			satisfied = false;

		if (!Character.isLetter(localItem.charAt(0)))
			satisfied = false;
		
		satisfied = true;
		return this;
	}

	public String toString() {
		return "[String must be a UserName]";
	}

}
