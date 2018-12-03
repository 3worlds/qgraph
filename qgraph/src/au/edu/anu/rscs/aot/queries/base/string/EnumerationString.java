package au.edu.anu.rscs.aot.queries.base.string;

import java.util.ArrayList;
import java.util.List;

import au.edu.anu.rscs.aot.queries.Query;

/**
 * 
 * @author Shayne Flint - 26/3/2012
 *
 */
public class EnumerationString extends Query {

	private List<String> valueList;

	public EnumerationString(String... valueList) {		
		this.valueList = new ArrayList<String>(valueList.length);
		for (int i=0; i<valueList.length; i++)
			this.valueList.add(valueList[i]);
	}
	
	public EnumerationString(Enum<?>... enumList) {
		this.valueList = new ArrayList<String>(enumList.length);
		for (Enum<?> e : enumList) 
			valueList.add(e.name());
	}

	public static EnumerationString isEnum(String... valueList) {
		return new EnumerationString(valueList);
	}
	
	public static EnumerationString isEnum(Enum<?>... enumList) {
		return new EnumerationString(enumList);
	}
	
	@Override
	public String userString() {
		return "[EnumerationString " + valueList.toString() + "]";
	}
	
	@Override
	public EnumerationString process(Object item) {
		defaultProcess(item);
		String localItem = (String)item;
		satisfied = valueList.contains(localItem);
		return this;
	}

}
