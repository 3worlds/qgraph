package au.edu.anu.rscs.aot.queries.base.string;

import java.net.InetAddress;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class InetAddressString extends Query {

	public static InetAddressString isInetAddress() {
		return new InetAddressString();
	}

	@SuppressWarnings("unused")
	@Override
	public boolean satisfied(Object item) {
		String localItem = (String)item;
		try {
			InetAddress typedItem = InetAddress.getByName(localItem);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@SuppressWarnings("unused")
	@Override
	public InetAddressString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		try {
			InetAddress typedItem = InetAddress.getByName(localItem);
			satisfied = true;
		} catch (Exception e) {
			satisfied = false;
		}
		return this;
	}

	public String toString() {
		return "[String must be an InetAddress]";
	}

}
