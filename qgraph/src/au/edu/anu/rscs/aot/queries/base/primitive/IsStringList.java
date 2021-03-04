package au.edu.anu.rscs.aot.queries.base.primitive;

import java.lang.reflect.Type;
import java.util.List;

import au.edu.anu.rscs.aot.queries.QueryAdaptor;
import au.edu.anu.rscs.aot.queries.Queryable;

public class IsStringList extends QueryAdaptor{

	@Override
	public Queryable submit(Object input) {
		initInput(input);
		Type[] par = input.getClass().getTypeParameters();
		if (!(List.class.isAssignableFrom(input.getClass())) 
				&& (par[0].getClass().equals(String.class))) {
			errorMsg = "'"+input.getClass().getSimpleName()+"' is not a StringList";
		};
		return this;
	}

}
