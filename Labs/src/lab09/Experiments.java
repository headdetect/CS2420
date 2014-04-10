package lab09;

import static org.junit.Assert.*;

import java.util.Random;

import org.junit.Test;

public class Experiments
{

	@Test
	public void testSimple()
	{
		SimpleHashSet set = new SimpleHashSet(100);
		for (int i = 0; i < 100; i++)
		{
			NumberName n = new NumberName(i);
			set.add(n);
		}
		System.out.println("Number of probes => " + set.getProbeCount());
	}

	@Test
	public void test1()
	{
		
		for (int i = 1; i <= 30; i++)
		{
			SimpleHashSet set = new SimpleHashSet(500);
			
			Random r = new Random();
			
			for (int k = 0; k < 400; k++)
			{
				NumberName n = new NumberName(k);
				set.add(n);
			}
			
			set.resetProbeCount();
			
			for (int samples = 1; samples < 10000 * i; samples++)
			{
				set.contains(Math.abs(r.nextInt()));
			}
			
			System.out.println(10000 * i + "\t" + set.getProbeCount());
		}
	}

}
