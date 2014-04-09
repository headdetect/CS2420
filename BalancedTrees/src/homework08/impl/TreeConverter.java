package homework08.impl;

import homework08.SpecialtySet;

import java.util.Random;

public class TreeConverter
{
	public static void main(String[] a)
	{
		SpecialtySet<Integer> set = new SpecialtySet<Integer>();
	    //AVLTree set = new AVLTree();
		Random random = new Random();
	    int[] data = new int[] {1, 5, 48, 46, 40, 6, 0, 4, 9, 3, 2, 11, 49};
	    for(int d : data)
	    	set.add(d);
	    	//set.insert(d);
	    for(int i = 1; i < 0; i++)
	    {
	    	int val = random.nextInt(i * 10);
	        set.add(val);
	    	//set.insert(val);
	        System.out.println("Removing: " + val);
	    }
	    //set.printSet();
		//TreeVisualizer.load(set.convertNodeTypes());
	}
	

}
