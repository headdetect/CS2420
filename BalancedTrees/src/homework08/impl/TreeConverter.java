package homework08.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import homework08.SpecialtySet;
import homework7.TreeVisualizer;

public class TreeConverter
{
	public static void main(String[] a)
	{
		SpecialtySet<String> set = new SpecialtySet<String>();
	    Random random = new Random();
	    for(int i = 0; i < 5; i++)
	    {
	        set.add(random.nextInt(100) + "");
	    }
	    set.printTree();
	}
}
