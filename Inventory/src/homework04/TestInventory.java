package homework04;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;

import org.junit.Test;

/**
 * This class is for testing {@link Inventory}
 * 
 * @author Brayden Lopez
 * @version January 22, 2014
 */
public class TestInventory
{
	@Test
	public void testParse()
	{
		try
		{
			Parser parser = new Parser("data1.txt");
			ArrayList<AbstractMap.SimpleEntry<String, Object>> data = parser.parse();
			for (int i = 0, len = data.size(); i < len; i++)
			{
				AbstractMap.SimpleEntry<String, Object> line = data.get(i);
				Object value = line.getValue();
				System.out.println("{" + line.getKey() + "," + value + "}");

			}

		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}