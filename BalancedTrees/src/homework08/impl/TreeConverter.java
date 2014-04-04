package homework08.impl;

import java.util.Random;

import homework08.SpecialtySet;
import homework7.TreeVisualizer;

public class TreeConverter
{
	public static void main(String[] a)
	{
		SpecialtySet<String> set = new SpecialtySet<String>();
	    Random random = new Random();
	    int[] data = new int[] {1, 5, 4, 9, 3, 2, 11, 49};
	    for(int d : data)
	    	set.add(d + "");
	    for(int i = 1; i < 10; i++)
	    {
	    	int val = random.nextInt(i * 10);
	        set.add(val + "");
	        System.out.println("Removing: " + val);
	    }
		TreeVisualizer.load(set.convertNodeTypes());
	}
	

}
