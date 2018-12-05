package au.edu.anu.rscs.aot.queries.graph.element;

import org.junit.Test;

import au.edu.anu.rscs.aot.queries.graph.element.ElementProperty;
import fr.cnrs.iees.graph.generic.DataNode;
import fr.cnrs.iees.graph.generic.GraphElementFactory;
import fr.cnrs.iees.graph.generic.impl.DefaultGraphFactory;
import fr.cnrs.iees.graph.properties.SimplePropertyList;
import fr.cnrs.iees.graph.properties.impl.SimplePropertyListImpl;
import junit.framework.TestCase;

/**
 * 
 * @author Yao Wang - 11/9/2012 (refactored by JG 2018)
 *
 */
public class ElementPropertyTest extends TestCase 
{
	@Test
	public void testHasProperty()
	{
		GraphElementFactory gf = new DefaultGraphFactory();
		SimplePropertyList props = new SimplePropertyListImpl("p1");
		props.setProperty("p1", 1234);
		DataNode n = gf.makeNode(props);
		try
		{
			ElementProperty ep = ElementProperty.hasProperty("p1");
			ep.check(n);
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		
		ElementProperty ep2 =null;
		try
		{
			ep2= ElementProperty.hasProperty("p1", 1234);
			ep2.check(n);
		}
		catch (Exception e) 
		{
			fail("shold not throw an exception ");
		}
		try
		{
			ep2 = ElementProperty.hasProperty("p1", 12345);
			ep2.check(n);
			fail("shold have thrown an exception ");
		}
		catch (Exception e) 
		{
			System.out.println("  error : " + e);
		}
	}
}
